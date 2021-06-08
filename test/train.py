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
with open('../intents.json') as json_data:
    intents = json.load(json_data)

# nltk.download('punkt')
words = []
classes = []
documents = []
ignore_words = ['?']
print("Looping through the Intents to Convert them to words, classes, documents and ignore_words.......")
for intent in intents['intents']:
    for pattern in intent['patterns']:
        # tokenize each word in the sentence
        pattern = pattern.translate(str.maketrans('', '', string.punctuation)).lower()
        w = word_tokenize(pattern)
        # add to our words list
        words.extend(w)
        # add to documents in our corpus
        documents.append((w, intent['tag']))
        # add to our classes list
        if intent['tag'] not in classes:
            classes.append(intent['tag'])

print("Stemming, Lowering and Removing Duplicates.......")
words = [stemmer.stem(w.lower()) for w in words if w not in ignore_words]
words = sorted(list(set(words)))

# remove duplicates
classes = sorted(list(set(classes)))

print (len(documents), "documents")
print (len(classes), "classes", classes)
print (len(words), "unique stemmed words", words)

print("Creating the Data for our Model.....")
training = []
output = []
print("Creating an List (Empty) for Output.....")
output_empty = [0] * len(classes)

print("Creating Traning Set, Bag of Words for our Model....")
for doc in documents:
    # initialize our bag of words
    bag = []
    # list of tokenized words for the pattern
    pattern_words = doc[0]
    # stem each word
    pattern_words = [stemmer.stem(word.lower()) for word in pattern_words]
    # create our bag of words array
    for w in words:
        bag.append(1) if w in pattern_words else bag.append(0)

    # output is a '0' for each tag and '1' for current tag
    output_row = list(output_empty)
    output_row[classes.index(doc[1])] = 1

    training.append([bag, output_row])

print("Shuffling Randomly and Converting into Numpy Array for Faster Processing......")
random.shuffle(training)
training = np.array(training)

print("Creating Train and Test Lists.....")
train_x = list(training[:,0])
train_y = list(training[:,1])
print("Building Neural Network for Out Chatbot to be Contextual....")
print("Resetting graph data....")

# %tensorflow_version 1.x
tf.compat.v1.reset_default_graph() #reset graph

# model versi keras
model = tf.keras.models.Sequential([
    #input layer
    tf.keras.layers.Dense(units=128, input_shape=(len(train_x[0]),), activation='relu'),
    tf.keras.layers.Dropout(0.5),
    tf.keras.layers.Dense(units=64, activation='relu'), #First hidden layer
    tf.keras.layers.Dropout(0.5),
    tf.keras.layers.Dense(units=32, activation='relu'), #second hidden layer
    tf.keras.layers.Dropout(0.5),
    tf.keras.layers.Dense(units=len(train_y[0]), activation='softmax') #output layer
])
sgd = SGD(lr=0.01, decay=1e-6, momentum=0.9, nesterov=True)
model.compile(loss='categorical_crossentropy', optimizer=sgd, metrics=['accuracy'])
hist = model.fit(tf.constant(train_x), tf.constant(train_y),
                 epochs=500, batch_size=32, verbose=1,
                steps_per_epoch=20)
model.save('../model.h5', hist)
model.save('model.tflearn', hist)
print("model created")

import pathlib

tf.saved_model.save(model, '/tmp/saved_model/1/')  # will be saved in dick C

# Convert the model.
converter = tf.lite.TFLiteConverter.from_saved_model('/tmp/saved_model/1/')  # path to the SavedModel directory
converter.optimizations = [tf.lite.Optimize.DEFAULT]
tflite_model = converter.convert()

#Save the model.
tflite_model_file = pathlib.Path('/tmp/model.tflite')
tflite_model_file.write_bytes(tflite_model)

print("Pickle is also Saved..........")
pickle.dump( {'words':words, 'classes':classes, 'train_x':train_x, 'train_y':train_y}, open( "../training_data", "wb" ) )

# json.dumps( {'words':words, 'classes':classes, 'train_x':train_x, 'train_y':train_y},
#                       separators=(',', ': '), ensure_ascii=False )
#
# with open('training_data.json', 'w', encoding='utf8') as outfile:
#     outfile.write(json.dumps( {'words':words, 'classes':classes, 'train_x':train_x, 'train_y':train_y},
#                       separators=(',', ': '), ensure_ascii=False ))

print("Loading Pickle.....")
data = pickle.load( open( "../training_data", "rb" ) ) #pickle

words = data['words']
classes = data['classes']
train_x = data['train_x']
train_y = data['train_y']
print(data)

# load our saved model
interpreter = tf.lite.Interpreter(model_content=tflite_model)
interpreter.allocate_tensors()

input_details = interpreter.get_input_details()[0]['index']
output_details = interpreter.get_output_details()[0]['index']

def clean_up_sentence(sentence):
    # It Tokenize or Break it into the constituents parts of Sentense.
    sentence = sentence.translate(str.maketrans('', '', string.punctuation)).lower()
    sentence_words = word_tokenize(sentence)
    # sentence_words = nltk.word_tokenize(sentence)
#     print(sentence_words)
    # Stemming means to find the root of the word.
    sentence_words = [stemmer.stem(word.lower()) for word in sentence_words]
    return sentence_words

# Return the Array of Bag of Words: True or False and 0 or 1 for each word of bag that exists in the Sentence
def bow(sentence, words, show_details=False):
    sentence_words = clean_up_sentence(sentence)
#     print(sentence_words)
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
    # print(bow(sentence, words))
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
    return results.pop(0)

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

