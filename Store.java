/**
 * Project 4- The Sharing Economy (shoe store messages)
 *
 * The Store class allows for users to create stores. It also allows buyers to select a store and get the owner's name to message.
 *
 * @author Fabian Prado, Wesley Richards, Courtney Kossick, Navya Singh, Arnav Kasat, lab section 36
 *
 * @version November 13, 2023
 *
 *
 */
import java.io.*;
import java.util.*;

public class Store {
    private String storeOwner;
    private String storeName;
    private static ArrayList<String> storeList = new ArrayList<>();
    private static ArrayList<ArrayList<String>> storeListTotal = new ArrayList<ArrayList<String>>();

    public Store(String storeOwner, String storeName) {
        this.storeOwner = storeOwner;
        this.storeName = storeName;
        storeList.add(storeOwner);
        storeList.add(storeName);
        storeListTotal.add(storeList);
    }
    public String toString() {
        return "Owner: " + storeOwner + "\nStore: " + storeName + "\n";
    }
    public static ArrayList<ArrayList<String>> getTotalStoreList() {
        return storeListTotal;
    }
    public static String getOwner(String sellerName) {
        //customers can search for a seller to message
        String seller = "";
        for (int i = 0; i < storeListTotal.size(); i ++) {
            if (storeListTotal.get(i).get(0).equals(sellerName)) {
                seller = storeListTotal.get(i).get(0);
            }
        }
        if (!seller.equals("")) {
            return "Send a message to " + seller + "!";
        }
        else {
            return "Seller does not exist!";
        }
    }
    public static ArrayList<String> getStoreNames() {
        //customers can view stores
        ArrayList<String> storeNames = new ArrayList<>();
        for (int i = 0; i < storeListTotal.size(); i ++) {
            storeNames.add(storeListTotal.get(i).get(1));
        }
        return storeNames;
    }
    public static String getStoresOwner(String store) {
        //customer can select store and get owner's name to message
        String storesOwner = "";
        for (int i = 0; i < storeListTotal.size(); i ++) {
            if (storeListTotal.get(i).get(1).equals(store)) {
                storesOwner = storeListTotal.get(i).get(0);
            }
        }
        if (!storesOwner.equals("")) {
            return "The owner of " + store + " is: " + storesOwner;
        }
        else {
            return "Store does not exist!";
        }
    }
    public static String addStore(String storeName) throws IOException {
        Scanner sc = new Scanner(new File("sellerList.txt"));
        //instantiating the StringBuffer class
        StringBuffer buffer = new StringBuffer();
        //Reading lines of the file and appending them to StringBuffer
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()+System.lineSeparator());
        }
        String fileContents = buffer.toString();
        String oldLine = FrontEnd.username;
        //closing the Scanner object
        sc.close();
        storeList.add(storeName);
        String newLine;
        if (oldLine.contains("{")) {
            newLine = oldLine + ", " + storeName;
        } else {
            newLine = oldLine + "{" + storeName;
        }
        fileContents = fileContents.replaceAll(oldLine, newLine);
        FileWriter writer = new FileWriter("sellerList.txt");
        System.out.println();
        System.out.println(fileContents);
        writer.append(fileContents);
        writer.flush();
        writer.close();
        return "Store added!";
    }
    public static ArrayList<String> getAllStores() throws IOException {
        System.out.println("Seller's Store List");
        String list = "";
        String line;
        ArrayList<String> allStores = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("sellerList.txt"));
        while ((line = br.readLine()) != null) {
            String[] info = line.split("\\{");
            if (info.length < 2) {
                System.out.println("Seller has no stores.");
                break;
            }
            
            for (int i = 1; i < info.length; i++) {
                list = list  + info[i] + ", ";
                allStores.add(info[i]);
            }
            String storeList = info[0] + ": " + list.substring(0, list.length());
            System.out.println(storeList + "\n");
            list = "";
        }
        br.close();
        System.out.println(allStores);
        return allStores;
    }
    public static String getStoreOwner(String storeName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("sellerList.txt"));
        String line;
        String [] lineInfo;
        while ((line = br.readLine()) != null) {
            lineInfo = line.split(":");
            if (line.contains(storeName)) {
                br.close();
                return lineInfo[0];
            }
        }
        br.close();
        return "Store doesn't exist!";
    }
}
