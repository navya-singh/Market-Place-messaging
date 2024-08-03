/**
 * Project 4- The Sharing Economy (shoe store messages)
 *
 * The Buyer class allows for buyer accounts to be created and to perform certain functions, including viewing sellers and getting messages stats.
 *
 * @author Fabian Prado, Wesley Richards, Courtney Kossick, Navya Singh, Arnav Kasat, lab section 36
 *
 * @version November 13, 2023
 *
 *
 */
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class Buyer {
    private String userName;
    private String email;
    private String userPassword;
    private static ArrayList<String> buyerNames = new ArrayList<>();

    public Buyer(String userName, String email, String userPassword) {
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
        buyerNames.add(userName);
    }

    public static ArrayList<String> getBuyerNames() {
        return buyerNames;
    }

    public static void customerMessagesReceived(String username, boolean decreasingOrder) { // This method returns a
                                                                                            // list of stores by number
                                                                                            // of messages received

        try {

            String fileName = username + "ReceivedMessages.txt"; // Using the username of the user, I look for its .txt
                                                                 // file of messages received history
            File f = new File(fileName);
            BufferedReader bfr = new BufferedReader(new FileReader(f));

            String line = bfr.readLine();
            Map<String, Integer> sellerFrequency = new HashMap<>(); // I used a hashamp to group the frequency of the
                                                                    // seller sending messages

            while (line != null) {

                if (line.startsWith("From: ")) { // Only lines which start with from: will have the name of the seller
                                                 // who messaged the customer
                    String seller = line.substring(6);
                    sellerFrequency.put(seller, sellerFrequency.getOrDefault(seller, 0) + 1); // the .getOrDefault
                                                                                              // checks if the sellers
                                                                                              // name already is in the
                                                                                              // Hashamp, if so it adds
                                                                                              // to its frequency, if
                                                                                              // not it adds it to the
                                                                                              // hashmap
                }
                line = bfr.readLine();
            }

            ArrayList<String[]> customerReceivedArrayList = new ArrayList<>(); // Initializing a list to get the
                                                                               // information into an array

            for (Map.Entry<String, Integer> entry : sellerFrequency.entrySet()) { // Traversing through the hashmap
                String seller = entry.getKey();
                int messageCount = entry.getValue();
                String[] sellerData = { seller, String.valueOf(messageCount) };
                customerReceivedArrayList.add(sellerData); // Adding an array to the list
            }

            // Convert the list to a 2D string array
            String[][] customerReceivedArray = new String[customerReceivedArrayList.size()][2];
            customerReceivedArrayList.toArray(customerReceivedArray);

            // Sort the array based on the second value (message frequency)
            // The code below uses the "Comparator" class to sort the array, since the array
            // is a String[][]
            // The @Override is used to ensure that the compare() method implemented below
            // WILL override the compare() of the Comparator class

            Arrays.sort(customerReceivedArray, new Comparator<String[]>() {
                // Since the Array given is String [][], the comparator class will take in each
                // row as a String[]
                @Override
                public int compare(String[] row1, String[] row2) {
                    // The second value of each row is the messageFrequency, so it takes it and
                    // parses it

                    int messageFrequency1 = Integer.parseInt(row1[1]);
                    int messageFrequency2 = Integer.parseInt(row2[1]);

                    // Compare based on the message frequency

                    if (decreasingOrder) {
                        // If the decreasingOrder is true, then elements with larger messageFrequency
                        // will come first
                        return Integer.compare(messageFrequency2, messageFrequency1);
                    }

                    else {
                        // If the decreasingOrder is false, then elements with smallest messageFrequency
                        // will come first
                        return Integer.compare(messageFrequency1, messageFrequency2);
                    }}
            });

            bfr.close();
            System.out.println("\nMessages received from each seller:\n");
            for (int i = 0; i < customerReceivedArray.length; i++) {
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        System.out.println("From: " + customerReceivedArray[i][j]);

                    } else if (j == 1) {
                        System.out.println("Total messages received: " + customerReceivedArray[i][j] + "\n");
                    }
                    
                }
            }
            System.out.println("\n");

        } catch (IOException e) {
            System.out.println("The username you entered may not have a message history");
        }
    }

    public static void customerMessagesSent(String username, boolean decreasingOrder) { // This method returns a list of
                                                                                        // stores by the number of
                                                                                        // messages that particular
                                                                                        // customer has sent.

        try {

            String fileName = username + "SentMessages.txt"; // Using the username of the user, I look for its .txt file
                                                             // of messages sent history
            File f = new File(fileName);
            BufferedReader bfr = new BufferedReader(new FileReader(f));

            String line = bfr.readLine();
            Map<String, Integer> customerFrequency = new HashMap<>(); // I used a hashmap to group the frequency of the
                                                                      // customer sending messages

            while (line != null) {

                if (line.startsWith("To: ")) { // Only lines which start with To: will have the name of the seller which
                                               // the customer messaged
                    String seller = line.substring(4);
                    customerFrequency.put(seller, customerFrequency.getOrDefault(seller, 0) + 1); // the .getOrDefault
                                                                                                  // checks if the
                                                                                                  // sellers name
                                                                                                  // already is in the
                                                                                                  // Hashamp, if so it
                                                                                                  // adds to its
                                                                                                  // frequency, if not
                                                                                                  // it adds it to the
                                                                                                  // hashmap
                }
                line = bfr.readLine();
            }

            ArrayList<String[]> customerSentArrayList = new ArrayList<>(); // Initializing a list to get the information
                                                                           // into an array

            for (Map.Entry<String, Integer> entry : customerFrequency.entrySet()) { // Traversing through the hashmap
                String seller = entry.getKey();
                int messageCount = entry.getValue();
                String[] sellerData = { seller, String.valueOf(messageCount) };
                customerSentArrayList.add(sellerData); // Adding an array to the list
            }

            // Convert the list to a 2D string array
            String[][] customerSentArray = new String[customerSentArrayList.size()][2];
            customerSentArrayList.toArray(customerSentArray);

            // Sort the array based on the second value (message frequency)
            // The code below uses the "Comparator" class to sort the array, since the array
            // is a String[][]
            // The @Override is used to ensure that the compare() method implemented below
            // WILL override the compare() of the Comparator class

            Arrays.sort(customerSentArray, new Comparator<String[]>() {
                // Since the Array given is String [][], the comparator class will take in each
                // row as a String[]
                @Override
                public int compare(String[] row1, String[] row2) {
                    // The second value of each row is the messageFrequency, so it takes it and
                    // parses it

                    int messageFrequency1 = Integer.parseInt(row1[1]);
                    int messageFrequency2 = Integer.parseInt(row2[1]);

                    // Compare based on the message frequency

                    if (decreasingOrder) {
                        // If the decreasingOrder is true, then elements with larger messageFrequency
                        // will come first
                        return Integer.compare(messageFrequency2, messageFrequency1);
                    } else {
                        // If the decreasingOrder is false, then elements with smallest messageFrequency
                        // will come first
                        return Integer.compare(messageFrequency1, messageFrequency2);
                    }
                }
            });

            bfr.close();
            System.out.println("\nMessages sent to each buyer:\n");
            for (int i = 0; i < customerSentArray.length; i++) {
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        System.out.println("To: " + customerSentArray[i][j]);

                    } else if (j == 1) {
                        System.out.println("Total messages sent: " + customerSentArray[i][j] + "\n");
                    }
                }
            }
            System.out.println("\n");

        } catch (IOException e) {
            System.out.println("The username you entered may not have a message history");
        }
    }

    public static void displaySellerList() {
        File f = new File("sellerList.txt");
        File s = new File("hiddenSellers.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            if (s.exists()) {
                while (line != null) {
                    boolean isHidden = false;
                    BufferedReader bw = new BufferedReader(new FileReader(s));
                    String hiddenUser = bw.readLine();
                    while (hiddenUser != null) {
                        if (!((line).contains((hiddenUser.split("]")[1]).toString()))) {
                            hiddenUser = bw.readLine();
                        } else {
                            line = br.readLine();
                            isHidden = true;
                            break;
                        }
                    } 
                    if (!isHidden) {
                        System.out.println((line.split(":"))[0]);
                        line = br.readLine();
                    }
                    bw.close();
                }
            } else {
                while (line != null) {
                    System.out.println((line.split(":"))[0]);
                    line = br.readLine();
                }
            } 
            br.close();  
        } catch (IOException e) {
            System.out.println("The username you entered may not have a message history");
        }
    }

    public static void displayBlockedSellers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("blockedSellers.txt"));
            String line = br.readLine();
            while (line != null) {
                System.out.println(line.split("]")[1]);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("You have not blocked any sellers.");
        }
    }

    public static void displayHiddenSellers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("hiddenSellers.txt"));
            String line = br.readLine();
            while (line != null) {
                System.out.println(line.split("]")[1]);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("You have not hidden any sellers.");
        }
    }

}
