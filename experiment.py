#import library
import string

import tensorflow as tf
import numpy as np
import tflearn
import random
import pandas as pd

#Used to for Contextualisation and Other NLP Tasks.
import nltk
# from nltk.stem.lancaster import LancasterStemmer
# stemmer = LancasterStemmer()
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
from nltk.tokenize import sent_tokenize, word_tokenize
factory = StemmerFactory()
stemmer = factory.create_stemmer()

#Other
import json
import pickle
import warnings
from tensorflow.keras.models import Sequential, load_model
from tensorflow.keras.layers import Dense, Activation, Dropout
from tensorflow.keras.optimizers import SGD
warnings.filterwarnings("ignore")
print(tf.__version__)

print("Processing the Intents.....")
with open('intents.json') as json_data:
    intents = json.load(json_data)

print("Loading Pickle.....")
data = pickle.load( open( "training_data", "rb" ) ) #pickle

words = data['words']
classes = data['classes']
train_x = data['train_x']
train_y = data['train_y']
print(data)

# load our saved model
interpreter = tf.lite.Interpreter('model.tflite')
interpreter.allocate_tensors()

input_details = interpreter.get_input_details()[0]['index']
output_details = interpreter.get_output_details()[0]['index']

def clean_up_sentence(sentence):
    # It Tokenize or Break it into the constituents parts of Sentense.
    sentence = sentence.translate(str.maketrans('', '', string.punctuation)).lower()
    sentence_words = word_tokenize(sentence)
    # sentence_words = nltk.word_tokenize(sentence)
    print(sentence_words)
    # Stemming means to find the root of the word.
    sentence_words = [stemmer.stem(word.lower()) for word in sentence_words]
    return sentence_words

# Return the Array of Bag of Words: True or False and 0 or 1 for each word of bag that exists in the Sentence
def bow(sentence, words, show_details=False):
    sentence_words = clean_up_sentence(sentence)
    print(sentence_words)
    bag = [0]*len(words)
    for s in sentence_words:
        for i,w in enumerate(words):
            if w == s:
                bag[i] = 1
                if show_details:
                    print ("found in bag: %s" % w)
    return(np.array(bag))

ERROR_THRESHOLD = 0.25
print("ERROR_THRESHOLD = 0.25")

def classify(sentence):
# generate probabilities from the model
    input_data = pd.DataFrame([bow(sentence, words)], dtype=np.float32, index=['input'])
#     # Prediction or To Get the Posibility or Probability from the Model
#     results = new_model.predict([input_data])[0]
    interpreter.set_tensor(input_details, ([input_data])[0])
    interpreter.invoke()
    results = interpreter.get_tensor(output_details)[0]
    print(bow(sentence, words))
    # Exclude those results which are Below Threshold
    results = [[i,r] for i,r in enumerate(results) if r>ERROR_THRESHOLD]
    # Sorting is Done because heigher Confidence Answer comes first.
    results.sort(key=lambda x: x[1], reverse=True)
    return_list = []
    for r in results:
        return_list.append((classes[r[0]], r[1])) #Tuppl -> Intent and Probability
    return return_list

def response(sentence, show_details=False):
    results = classify(sentence)
    # That Means if Classification is Done then Find the Matching Tag.
    if results:
        # Long Loop to get the Result.
        while results:
            for i in intents['intents']:
                # Tag Finding
                if i['tag'] == results[0][0]:
                    # Random Response from High Order Probabilities
                    return print(random.choice(i['responses']))

            results.pop(0)

if __name__ == '__main__':
    from time import sleep
    print("Hai, hari yang indah")
    sleep(0.5)
    print("Perkenalkan, saya Zassy. Chatbot pribadi anda")
    sleep(0.5)
    print("Ada yang bisa saya bantu?")
    while True:
        input_data = input("You : ")
        if input_data.lower() == "quit":
            break
        answer = response(input_data)
        answer

