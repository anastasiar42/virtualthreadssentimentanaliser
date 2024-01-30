# SentimentAnalyser

@author Anastasiia Ryzhkova
@version Java 21

# Description
The application provides a user-friendly interface for analysing sentiment of Twitter/X poster 
using lexicon analysis and SaS (Score and Sum) approach for defining sentiment.

# To Run
Application is run from console at .jar file directory:
java --enable-preview -cp .:./sentiment.jar ie.atu.sw.Runner
Then navigate through console options (1 and 2) to configure desired lexicon and then analyse the text with the lexicon provided in step 1. Option 3 can be used to exit the application. 

# Features

```
● Lexicon configuration
  ○ Lexicon path shall follow valid format for filesystem (e.g. /Users/anastasiia/Documents/ATU/lexicons/vader.txt)
  ○ Lexicon processing methods implemented in LexiconHandler.java class
  ○ Words and scores are saved to concurrent HashMap
  ○ processLexiconFromFile method reads lexicon data from a file and processes it using virtual threads
  ○ processLexicon method processes lexicon data provided as a string using virtual threads
  ○ Both methods utilise the Executors.newVirtualThreadPerTaskExecutor() to create a virtual thread pool for parallel execution
● Analyse sentiment of Twitter/X poster from text file
  ○ File Path shall follow valid format for filesystem (e.g. /Users/anastasiia/Documents/ATU/100-twitter-users/AceMas21.txt)
  ○ Sentiment analysis functionality is implemented  in SentimentCalculator class
  ○ Constructor SentimentCalculator(ConcurrentMap<String, Double> lexicon) assigns the provided lexicon to the instance variable
  ○ analyseSentiment method analyses sentiment for a specified file using the provided lexicon and utilises virtual threads
  ○ reportSentiment method generates a sentiment report based on the calculated score
● Process overview
  ○ Sentiment analysis is done sequentially
  ○ First lexicon shall be configured
  ○ Then text sentiment may be analysed as per provided lexicon
  ○ Definition of positive and negative sentiment was based on that the negative words have negative score so if overall score is negative is indicates the negative sentiment

```
