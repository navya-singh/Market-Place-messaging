/**
 * Project 4- The Sharing Economy (shoe store messages)
 *
 * The Messages class is responsible for creating the messages of both Seller and Buyer, and implementing them into a text file so other 
 * classes can use them. This class also allows the user to either delete a message or edit them.
 *
 * @author Fabian Prado, Wesley Richards, Courtney Kossick, Navya Singh, Arnav Kasat, lab section 36
 *
 * @version November 13, 2023
 *
 *
 */
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Messages {
    String recordMessage;
    static String intendedUser;
    String type;
    static Scanner keyboard = new Scanner(System.in);
    File f = new File((intendedUser + "ReceivedMessages.txt"));
    static ArrayList<String> listOfStores= new ArrayList<>();

    public Messages(String input, String intendedUser, String type) {
        this.recordMessage = input;
        Messages.intendedUser = intendedUser;
        this.type = type;
    }

    public static String timeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String timestamp = dateFormat.format(currentDate);
        return timestamp;
    }

    public static void writeMessage(String currentUser, String type) throws IOException {
        String intendedUser;
        String message;
        String currUser = currentUser;
        boolean blocked = false;

        if (type.equalsIgnoreCase("s")) {
            if (new File("buyerList.txt").exists()) {
                Scanner fileScanner = new Scanner(new File("buyerList.txt"));
                type = "b";
                System.out.println("Buyer List:");
                while (fileScanner.hasNextLine()) {
                    String[] actualUser = fileScanner.nextLine().split(":");
                    System.out.println(actualUser[0]);
                }

                System.out.println("\nWhat is the username of the person you would like to message?");
                intendedUser = keyboard.nextLine();
                File blockedSellers = new File("blockedSellers.txt");
                if (blockedSellers.exists()) {
                    BufferedReader blockedScanner = new BufferedReader(new FileReader(blockedSellers));
                    String specificLine = blockedScanner.readLine();
                    while (specificLine != null) {
                        if (specificLine.contains(currentUser) && specificLine.contains(intendedUser)) {
                            System.out.println("This user has blocked you, so you cannot message them.");
                            blocked = true;
                            break;
                        } specificLine = blockedScanner.readLine();
                    }
                    if (blocked == false) {
                        try {
                            if (FrontEnd.checkUsernameExists(intendedUser, type)) {
                                System.out.println("What is the message you would like to send?");
                                message = keyboard.nextLine();
                                addMessageToSenderFile(message, currUser, intendedUser);
                                addMessageToReceiverFile(message, currUser, intendedUser);
                            } else {
                                System.out.println("Sorry, that is not a valid user -- Unable to send message.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    blockedScanner.close();
                } else {
                    try {
                        if (FrontEnd.checkUsernameExists(intendedUser, type)) {
                            System.out.println("What is the message you would like to send?");
                            message = keyboard.nextLine();
                            addMessageToSenderFile(message, currUser, intendedUser);
                            addMessageToReceiverFile(message, currUser, intendedUser);
                        } else {
                            System.out.println("Sorry, that is not a valid user -- Unable to send message.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } 
            } else {
                System.out.println("Sorry, there are no buyers available to message.\n");
            }
        } else if (type.equalsIgnoreCase("b")) {
            if (new File("sellerList.txt").exists()) {
                Scanner fileScanner = new Scanner(new File("sellerList.txt"));
                type = "s";
                System.out.println("Seller List:");
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] actualUser = line.split(":");
                    if (!(line.contains(actualUser[0]) && line.contains(currentUser))) {
                        System.out.println(actualUser[0]);
                    }
                }
                Scanner storeScanner = new Scanner(new File("sellerList.txt"));
                System.out.println("Store List:");
                while (storeScanner.hasNextLine()) {
                    String line = storeScanner.nextLine();
                    if (line.contains("{")){
                        String[] storeSeparate = line.split("\\{");
                        String[] actualUser = storeSeparate[1].split(",");
                        for (int index = 0; index < actualUser.length; ++index) {
                            listOfStores.add(actualUser[0]);
                            System.out.println(actualUser[0]);
                        }
                    } 
                }

                System.out.println("\nWhat is the name of the person or store that you would like to message?");
                intendedUser = keyboard.nextLine();
                if (listOfStores.contains(intendedUser)) {
                    Scanner findSeller = new Scanner(new File("sellerList.txt"));
                    String line = findSeller.nextLine();
                    if (line.contains(intendedUser)) {
                        String[] foundUser = line.split(":");
                        intendedUser = (foundUser[0]);
                        System.out.println("Sending message to owner of this store, " + foundUser[0]);
                    }
                }
                File blockedBuyers = new File("blockedBuyers.txt");
                if (blockedBuyers.exists()) {
                    BufferedReader blockedScanner = new BufferedReader(new FileReader(blockedBuyers));
                    String specificLine = blockedScanner.readLine();
                    while (specificLine != null) {
                        if (specificLine.contains(currentUser) && specificLine.contains(intendedUser)) {
                            System.out.println("This user has blocked you, so you cannot message them at this time.");
                            blocked = true;
                            break;
                        } specificLine = blockedScanner.readLine();
                    }
                    if (blocked == false) {
                        try {
                            if (FrontEnd.checkUsernameExists(intendedUser, type)) {
                                System.out.println("What is the message you would like to send?");
                                message = keyboard.nextLine();
                                addMessageToSenderFile(message, currUser, intendedUser);
                                addMessageToReceiverFile(message, currUser, intendedUser);
                            } else {
                                System.out.println("Sorry, that is not a valid user -- Unable to send message.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    blockedScanner.close();
                } else {
                    try {
                        if (FrontEnd.checkUsernameExists(intendedUser, type)) {
                            System.out.println("What is the message you would like to send?");
                            message = keyboard.nextLine();
                            addMessageToSenderFile(message, currUser, intendedUser);
                            addMessageToReceiverFile(message, currUser, intendedUser);
                        } else {
                            System.out.println("Sorry, that is not a valid user -- Unable to send message.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
                System.out.println("Sorry, there are no sellers available to message.\n");
                return;
            }   
    }

    public static void addMessageToSenderFile(String messageToAdd, String currentUser, String intendedUser) {
        File f = new File(((currentUser) + "SentMessages.txt"));
        if (f.exists()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                bw.append("To: " + (intendedUser) + "\nSent Message: " + messageToAdd + "\n" + timeStamp() + "\n");
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new File((currentUser) + "SentMessages.txt");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                bw.append("To: " + (intendedUser) + "\nSent Message: " + messageToAdd + "\n" + timeStamp() + "\n");
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addMessageToReceiverFile(String messageToAdd, String currentUser, String intendedUser) {
        File f = new File((intendedUser + "ReceivedMessages.txt"));
        if (f.exists()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                bw.append("From: " + (currentUser) + "\nNew Message: " + messageToAdd + "\n" + timeStamp() + "\n");
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new File(intendedUser + "ReceievedMessages.txt");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
                bw.append("From: " + (currentUser) + "\nNew Message: " + messageToAdd + "\n" + timeStamp() + "\n");
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void editMessage(String username, String type) throws IOException {
        File s = new File((username) + "SentMessages.txt");
        String messageReceiver;
        String newText = "";
        if (s.exists()) {
            System.out.println("Who was the message sent to that you would like to edit?");
            messageReceiver = keyboard.nextLine();
            File r = new File((messageReceiver) + "ReceivedMessages.txt");
            if (r.exists()) {
                Scanner receiverFile = new Scanner(r);
                Scanner senderFile = new Scanner(s);
                StringBuffer buffer = new StringBuffer();
                StringBuffer buffer2 = new StringBuffer();
                System.out.println("What is the message you would like to replace?");
                String messageToReplace = keyboard.nextLine();
                while (receiverFile.hasNextLine()) {
                    buffer.append(receiverFile.nextLine() + System.lineSeparator());
                }
                while (senderFile.hasNextLine()) {
                    buffer2.append(senderFile.nextLine() + System.lineSeparator());
                }

                String contentsOfRFile = buffer.toString();
                String contentsOfSFile = buffer2.toString();
                if (contentsOfRFile.contains(messageToReplace)) {
                    System.out.println("Message found!\n");
                    System.out.println("Enter revised message: ");
                    newText = keyboard.nextLine();
                } else {
                    System.out.println("That message to that user was not found.\n");
                }
                contentsOfRFile = contentsOfRFile.replace(messageToReplace, newText);
                contentsOfSFile = contentsOfSFile.replace(messageToReplace, newText);
                FileWriter writer = new FileWriter(r);
                FileWriter writer2 = new FileWriter(s);
                writer.write(contentsOfRFile);
                writer2.write(contentsOfSFile);
                writer.flush();
                writer.close();
                writer2.flush();
                writer2.close();
                receiverFile.close();
                senderFile.close();
            } else {
                System.out.println("User not found.");
            }
        } else {
            System.out.println("You have not sent any messages.\n");
        }
    }

    public static void deleteMessage(String username, String type) throws IOException {
        File s = new File((username) + "SentMessages.txt");
        String messageReceiver;
        if (s.exists()) {
            System.out.println("Who was the message sent to that you would like to delete?");
            messageReceiver = keyboard.nextLine();
            File r = new File((messageReceiver) + "ReceivedMessages.txt");
            if (r.exists()) {
                Scanner senderFile = new Scanner(s);
                StringBuffer buffer = new StringBuffer();
                System.out.println("What is the message you would like to delete?");
                String messageToDelete = keyboard.nextLine();
                while (senderFile.hasNextLine()) {
                    buffer.append(senderFile.nextLine() + System.lineSeparator());
                }
                String contentsOfSFile = buffer.toString();
                if (contentsOfSFile.contains(messageToDelete)) {
                    System.out.println("Message found!\n");
                } else {
                    System.out.println("That message to that user was not found.");
                }
                contentsOfSFile = contentsOfSFile.replace(messageToDelete, "This message has been deleted.");
                FileWriter writer = new FileWriter(s);
                writer.write(contentsOfSFile);
                writer.flush();
                writer.close();
                senderFile.close();
            } else {
                System.out.println("User not found.");
            }
        } else {
            System.out.println("You have not sent any messages.");
        }
    }
}
