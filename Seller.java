/**
 * Project 4- The Sharing Economy (shoe store messages)
 *
 * The Seller class allows for seller accounts to be created and to perform certain functions, including viewing buyers and getting messages stats.
 *
 * @author Fabian Prado, Wesley Richards, Courtney Kossick, Navya Singh, Arnav Kasat, lab section 36
 *
 * @version November 13, 2023
 *
 *
 */
import java.util.*;
import java.io.*;

public class Seller {
    private String sellerName;
    private String sellerEmail;
    private String sellerPassword;
    private ArrayList<Store> stores = new ArrayList<>();
    public Seller (String sellerName, String sellerEmail, String sellerPassword) {
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.sellerPassword = sellerPassword;
    }
   
    public void addStore(String storeOwner, String storeName) {
        if (storeOwner.equals(sellerName)) {
            if (Store.getStoreNames().contains(storeName)) {
                throw new IllegalArgumentException("Store name already exists!");
            }
            else {
                Store store = new Store(storeOwner, storeName);
                stores.add(store);
            }
        }
        else {
            throw new IllegalArgumentException("Owner name does not match account!");
        }
    }

    public ArrayList<Store> getStores() {
        //returns a specific seller's stores
        return stores;
    }

    public static void sellerStatistic(String username, boolean decreasingOrder) { //Must used this method with getMostCommonWord() method

        try {
        
            String fileName = username + "ReceivedMessages.txt"; //Using the username of the user, I look for its .txt file of messages received history
            File f = new File(fileName);
            BufferedReader bfr = new BufferedReader(new FileReader(f));

            String line = bfr.readLine();
            Map<String, Integer> sellerFrequency = new HashMap<>(); //I used a hashamp to group the frequency of the buyer sending messages
            
            while (line != null) {

                if (line.startsWith("From: ")) { //Only lines which start with from: will have the name of the buyer who messaged the seller
                    String seller = line.substring(6);
                    sellerFrequency.put(seller, sellerFrequency.getOrDefault(seller, 0) + 1); //the .getOrDefault checks if the sellers name already is in the Hashmap, if so it adds to its frequency, if not it adds it to the hashmap
                }
                line = bfr.readLine();
            }

            ArrayList<String[]> buyerReceivedArrayList = new ArrayList<>(); //Initializing a list to get the information into an array

            
            for (Map.Entry<String, Integer> entry : sellerFrequency.entrySet()) { //Traversing through the hashmap
                String buyer = entry.getKey();
                int messageCount = entry.getValue();
                String[] buyerData = {buyer, String.valueOf(messageCount)}; 
                buyerReceivedArrayList.add(buyerData); //Adding an array to the list
            }
            
            // Convert the list to a 2D string array
            String[][] buyerReceivedArray = new String[buyerReceivedArrayList.size()][2];
            buyerReceivedArrayList.toArray(buyerReceivedArray);

            // Sort the array based on the second value (message frequency)
            // The code below uses the "Comparator" class to sort the array, since the array is a String[][]
            // The @Override is used to ensure that the compare() method implemented below WILL override the compare() of the Comparator class
            

            Arrays.sort(buyerReceivedArray, new Comparator<String[]>() {  
                // Since the Array given is String [][], the comparator class will take in each row as a String[] 
                @Override
                public int compare(String[] row1, String[] row2) { 
                    // The second value of each row is the messageFrequency, so it takes it and parses it 

                    int messageFrequency1 = Integer.parseInt(row1[1]); 
                    int messageFrequency2 = Integer.parseInt(row2[1]);

                    // Compare based on the message frequency

                    if (decreasingOrder) {
                        // If the decreasingOrder is true, then elements with larger messageFrequency will come first 
                        return Integer.compare(messageFrequency2, messageFrequency1);
                    }

                    else {
                        // If the decreasingOrder is false, then elements with smallest messageFrequency will come first 
                        return Integer.compare(messageFrequency1, messageFrequency2);
                    }
                }
            });
            
            bfr.close();
            for (int i = 0; i < buyerReceivedArray.length; i++) {
                for (int j = 0; j < 2; j ++) {
                    System.out.println(buyerReceivedArray[i][j]);
                }
            }
            System.out.println("\n");

        } catch (IOException e) {
            System.out.println("The username you entered may not have a message history");
        }
    }

    public static void getMostCommonWord(String username) { //Must used this method with sellerStatistic() method
        try {
        
            String fileName = username + "ReceivedMessages.txt"; //Using the username of the user, I look for its .txt file of messages received history
            File f = new File(fileName);
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            

            String line = bfr.readLine();
            StringBuilder placeHolder = new StringBuilder();
            
            while ((line = bfr.readLine()) != null) {

                if (line.startsWith("From: ")) {
                    // The message is the line after the "From:" line
                    line = bfr.readLine();

                    if (line != null && !line.trim().isEmpty()) {
                        line += " "; //So words ending and begging lines dont get put together
                        placeHolder.append(line);
                    }
                }
            }

            String[] messages = placeHolder.toString().toLowerCase().split("[.,;:?!()\\s/]+");

            Map<String, Integer> wordFrequency = new HashMap<>(); //I used a hashamp to group the frequency of the word 

            for (String message: messages) {
                wordFrequency.put(message, wordFrequency.getOrDefault(message, 1) + 1);
            }

            String mostCommonWord = "";
            int frequency = 0;

            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                if (frequency < entry.getValue()) {
                    mostCommonWord = entry.getKey();
                    frequency = entry.getValue();
                }
            }

            String[][] mostCommonWordFrequency = new String[1][2];
            mostCommonWordFrequency[0][0] = mostCommonWord;
            mostCommonWordFrequency[0][1] = Integer.toString(frequency);
        
            bfr.close();
            System.out.println("Most common word: " + mostCommonWordFrequency[0][0]);
            System.out.println("Frequency: " + mostCommonWordFrequency[0][1]);
            System.out.println();

            // The first Value of the array is the Most common word and the second part its its frequency (as a String to send both values at the same time)
        } catch (IOException e) {
            System.out.println("The username you entered may not have a message history");
        }
    }
    
    public static void displayBuyerList() {
    File f = new File("buyerList.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            Scanner scanner = new Scanner(f);
            String line = br.readLine();
            while (scanner.hasNextLine()) {
                System.out.println((line.split(":"))[0]);
                line = br.readLine();
                scanner.nextLine();
            } br.close();
            scanner.close();
        } catch (IOException e) {
            System.out.println("The username you entered may not have a message history.");
        }
    }

    public static void displayBlockedBuyers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("blockedBuyers.txt"));
            String line = br.readLine();
            while (line != null) {
                System.out.println(line.split("]")[1]);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("You have not blocked any buyers.");
        }
    }

    public static void displayHiddenBuyers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("hiddenBuyers.txt"));
            String line = br.readLine();
            while (line != null) {
                System.out.println(line.split("]")[1]);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("You have not hidden any buyers.");
        }
    }
}
