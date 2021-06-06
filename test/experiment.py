# def classify(sentence):
#     input_data = pd.DataFrame([bow(sentence, words)], dtype=np.float32, index=['input'])
#     get_model()
#     # Prediction or To Get the Posibility or Probability from the Model
#     results = model.predict([input_data])[0]
#     # interpreter.set_tensor(input_details, ([input_data])[0])
#     # interpreter.invoke()
#     # results = interpreter.get_tensor(output_details)[0]
#     # print(bow(sentence, words))
#     # Exclude those results which are Below Threshold
#     results = [[i,r] for i,r in enumerate(results) if r>ERROR_THRESHOLD]
#     # Sorting is Done because heigher Confidence Answer comes first.
#     results.sort(key=lambda x: x[1], reverse=True)
#     return_list = []
#     for r in results:
#         return_list.append((classes[r[0]], r[1])) #Tuppl -> Intent and Probability
#     return return_list


# def clean_up_sentence(sentence):
#     # It Tokenize or Break it into the constituents parts of Sentense.
#     sentence_words = nltk.word_tokenize(sentence)
# #     print(sentence_words)
#     # Stemming means to find the root of the word.
#     sentence_words = [stemmer.stem(word.lower()) for word in sentence_words]
#     return sentence_words

# def bow(sentence, words, show_details=False):
#     sentence_words = clean_up_sentence(sentence)
# #     print(sentence_words)
#     bag = [0]*len(words)
#     for s in sentence_words:
#         for i,w in enumerate(words):
#             if w == s:
#                 bag[i] = 1
#                 if show_details:
#                     print ("found in bag: %s" % w)
#     return(np.array(bag))

# terlalu banyak di rumah membuat saya jarang bersosialisasi dengan orang, dan