# Market-Place-messaging

This project contains a main method in FrontEnd.java, and should run properly so long as all classes have been compiled. A buyers and a sellers file have been included from us to allow for testing of all functions without having to make a bunch of new accounts, but the program can also work as though it is starting from scratch. That is to say, if the program user decides to create an account, they will be able to do so and all features will be fully operable.

Courtney is submitting the Vocareum workspace.
Arnav is submitting the report on Brightspace.

The Buyer class allows for buyer accounts to perform certain functions. Within this class, there is a method to create an array list of buyer names. There are also methods for a buyer to view all sellers (that are not hidden), all the sellers that they have blocked, and all the sellers that they have hidden. Additionally, this class contains a statistics function unique to the buyer; where they can check the interactions they have had with stores by being able to see how many messages they have sent to every store, as well as how many messages they have received. Buyers are able to sort their statistics by frequency, either in an incrementing or decrementing fashion. We tested all types of inputs for creating an account to make sure that this successfully created a proper buyer account. This class is used in FrontEnd.java.

The Seller class allows for seller accounts to perform specific functions. In the seller class, there is a method to allow the seller to add a store to their account, and generate an array list of all their stores. There are also methods for a seller to view all buyers (that are not hidden), all the buyers that they have blocked, and all the buyers that they have hidden. Once again, this class implements a statistics function unique to the seller; where they can check how many times a seller has messaged them, as well as the most common word in their overall messages. Sellers are also able to sort their statistics by frequency, again in an incrementing or decrementing fashion. We tested all types of inputs for creating an account to make sure that this successfully created a proper seller account. This class is used in FrontEnd.java.

The Messages class which is responsible for creating the messages of both Seller and Buyer, and implementing them into a text file so other classes can use them. Specifically, it adds a message to both a Received File of the user who receives the message, and a Sent File of the user who sends the message. The format of the message in the files is three lines which contain: the user who sent or received the message, the message, and a timestamp. This class also allows the user to either delete a message or edit them, changing the status of the message from both Received and Sent files. We tested all types of inputs for messages. This class is referenced in FrontEnd.java, and uses methods from it as well.

The Store class which is the building class for the stores and their respective owners. This class is used by both the buyer and seller. Using this class’ constructor, the seller is able to create a Store, assigning their name into it and adding it to the list of Stores. In the case of the Sellers, they can use the class to interact with the sellers and their stores in various ways: they can select a store and get the owner's name to message, view the list of stores, and search for a seller to message. 

The Files class, which contains four methods, will prompt the user for an input file and then call the readInputFile method which utilizes BufferedReader and Writer to add each line of the input file to an ArrayList and save the message details. After reading the input file, the method getMessageDetails will prompt the user again for if they are writing to a CSV file, if the user is, then the writeToCSVFile method will use the message details and prompted user information for the sender and receiver of message to write a new CSV file of all message information. If the user chose to not write to a CSV file, then they are prompted in importing message details from the input file to an existing conversation as a new message using the importToFile method. If the user chose no to both options or had any invalid input, then getMessageDetails will prompt the user to try again or exit that section of the overall program.;

The FrontEnd class which is where the main method of the program will run. This class combines all methods and classes, actually putting together the whole program. It creates an interface for a user, either a seller or buyer, to either create or log into an account and interact with buyers and sellers.

*the text files in this repository simulates what the messages between users may look like, as well as seller lists and buyer lists.

Project Notes for Clarification:
~Blocking will only prevent the blocked user from messaging the user doing the blocking. The user doing the blocking will still be able to message the user they block. If they would like to not see a user as an option to message, then they may hide the user. They will still be able to message the user if they know the users name, but they will not see the user displayed on the list of Users to Message.
~A user should not use the same letter back to back when creating their passcode.
