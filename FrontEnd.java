/**
 * Project 4- The Sharing Economy (shoe store messages)
 *
 * The Front end class creates an interface for a user, either a seller or buyer, to either create or log into an account and interact with buyers and sellers.
 *
 * @author Fabian Prado, Wesley Richards, Courtney Kossick, Navya Singh, Arnav Kasat, lab section 36
 *
 * @version November 13, 2023
 *
 *
 */
import java.io.*;
import java.util.*;

public class FrontEnd {
    static Scanner keyboard = new Scanner(System.in);
    static Seller seller;
    static Buyer customer;
    static boolean keepRunning = true;
    static String response;
    static String username;
    static String email;
    static String password;
    static File buyerList = new File("buyerList.txt");
    static File sellerList = new File("sellerList.txt");

    //Saves blocked and hidden users for easy reference access.
    static ArrayList<String> blockedBuyers = new ArrayList<>();
    static ArrayList<String> hiddenBuyers = new ArrayList<>();
    static ArrayList<String> blockedSellers = new ArrayList<>();
    static ArrayList<String> hiddenSellers = new ArrayList<>();
    //Main method to run code with
    public static void main(String[] args) throws IOException {
        String response;
        System.out.println(
                "Welcome to Shoe Central Messaging, the #1 digital platform for connecting shoe buyers with shoe stores!"
                        +
                        "\nDo you have an account? Type Y for yes and N for no. Type E to exit.");
        response = keyboard.nextLine();
        while (keepRunning == true) {
            if (response.equalsIgnoreCase("y")) {
                System.out.println("Please enter your username.");
                String user = keyboard.nextLine();
                System.out.println("Are you a buyer or a seller? Type B for buyer and S for seller.");
                String type = keyboard.nextLine();
                if (checkUsernameExists(user, type)) {
                    attemptToAccessAccount(user, type);
                    giveOptions(user, type);
                    keepRunning = false;
                } else {
                    System.out
                            .println("This account does not exist. Type T to try again, or C to create a new account.");
                    String userAnswer = keyboard.nextLine();
                    while (userAnswer != null) {
                        if (userAnswer.equalsIgnoreCase("c")) {
                            response = "n";
                            userAnswer = null;
                        } else if (userAnswer.equalsIgnoreCase("t")) {
                            response = "y";
                            userAnswer = null;
                        } else {
                            System.out.println("You must input either T or C. Try again.");
                            userAnswer = keyboard.nextLine();
                        }
                    }
                }
            } else if (response.equalsIgnoreCase("n")) {
                createNewAccount();
                System.out.println("Success! Account created. Proceeding to login...\n");
                response = "y";
            } else if (response.toLowerCase().equals("e")) {
                keepRunning = false;
            } else {
                System.out.println("That is not a valid response! Please try again.");
                response = keyboard.nextLine();
                keepRunning = true;
            }
        }
        System.out.println("Have a nice day.");
    }

    //Verify username exists, and then have them enter password and verify it matches.
    public static boolean attemptToAccessAccount(String user, String type) throws IOException {
        String response = type;
        String potentialUser = user;
        String attemptAtPassword = "empty";
        String actualPassword = "empty";
        while (attemptAtPassword != (null)) {
            System.out.println("Please enter your password.");
            attemptAtPassword = keyboard.nextLine();
            while (checkUsernameExists(potentialUser, response)) {
                if (type.equalsIgnoreCase("b")) {
                    BufferedReader br = new BufferedReader(new FileReader("buyerList.txt"));
                    while ((actualPassword = (br.readLine())) != (null)) {
                        String[] removeStores = actualPassword.split("\\{");
                        String[] userInfo = removeStores[0].split(":");
                        if ((userInfo[2]).equals(attemptAtPassword) && (userInfo[0].equals(user))) {
                            br.close();
                            return true;
                        } else if (userInfo[0].equals(user)){
                            System.out.println("That password was incorrect. Try again.");
                            attemptAtPassword = keyboard.nextLine(); 
                        }
                    } br.close();
                } else if (type.equalsIgnoreCase("s")) {
                    BufferedReader brs = new BufferedReader(new FileReader("sellerList.txt"));
                    while ((actualPassword = (brs.readLine())) != (null)) {
                        String[] removeStores = actualPassword.split("\\{");
                        String[] userInfo = removeStores[0].split(":");
                        if ((userInfo[2]).equals(attemptAtPassword) && (userInfo[0].equals(user))) {
                            brs.close();
                            return true;
                        } else if (userInfo[0].equals(user)){
                            System.out.println("That password was incorrect. Try again.");
                            attemptAtPassword = keyboard.nextLine(); 
                        }
                    } brs.close();
                }
            }
            System.out.println("Unable to access account. Please try again.\n");
            System.out.println("Please enter your username.");
            potentialUser = keyboard.nextLine();
            System.out.println("Are you a buyer or a seller? Type B for buyer and S for seller.");
            response = keyboard.nextLine();
        }
        return false;
    }


    // Access to the current user's name outside of class.
    public static String getSenderName() {
        return username;
    }

    //Allows for creation of a new account
    public static void createNewAccount() throws IOException {
        boolean goodEmail = false;
        boolean goodPass = false;
        System.out.println("Are you a buyer or a seller? Type B for buyer and S for seller.");
        response = keyboard.nextLine();
        while (!(response == null)) {
            if (response.toLowerCase().equals("b")) {
                System.out.println("Enter a username. Username cannot contain a semicolon.");
                username = keyboard.nextLine();
                if (username.contains(":")) {
                    System.out.println("Invalid username. Username cannot contain semicolon.\n");
                } else if (!checkUsernameExists(username, response)) {
                    while (goodEmail == false) {
                        System.out.println("Enter your email. Email cannot contain semicolon.");
                        email = keyboard.nextLine();
                        if (email.contains(":")) {
                            System.out.println("Invalid email. Email cannot contain semicolon.\n");
                        } else {
                            goodEmail = true;
                            while (goodPass == false) {
                                System.out.println("Create a password. Password cannot contain a semicolon.");
                                password = keyboard.nextLine();
                                if (password.contains(":")) {
                                    System.out.println("Invalid password. Password cannot contain semicolon.\n");
                                } else {
                                    goodPass = true;
                                }
                            }
                        } 
                    }
                    customer = new Buyer(username, email, password);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(buyerList, true));
                    bw.append(username + ":" + email + ":" + password + "\n");
                    bw.flush();
                    bw.close();
                    break;
                } else {
                    System.out.println("Sorry, that username has already been taken. Please try again.");
                } response = null;
            } else if (response.toLowerCase().equals("s")) {
                System.out.println("Enter a username. Username cannot contain a semicolon.");
                username = keyboard.nextLine();
                if (username.contains(":")) {
                    System.out.println("Invalid username. Username cannot contain semicolon.\n");
                } else if (!(checkUsernameExists(username, response))) {
                    while (goodEmail == false) {
                        System.out.println("Enter your email. Email cannot contain semicolon.");
                        email = keyboard.nextLine();
                        if (email.contains(":")) {
                            System.out.println("Invalid email. Email cannot contain semicolon.\n");
                        } else {
                            goodEmail = true;
                            while (goodPass == false) {
                                System.out.println("Create a password. Password cannot contain a semicolon.");
                                password = keyboard.nextLine();
                                if (password.contains(":")) {
                                    System.out.println("Invalid password. Password cannot contain semicolon.\n");
                                } else {
                                    goodPass = true;
                                    System.out.println("Would you like to add a store as well? Type y for yes and n for no.");
                                    if (keyboard.nextLine().equalsIgnoreCase("Y")) {
                                        seller = new Seller(username, email, password);
                                        System.out.println("Please enter your stores name.");
                                        String store = keyboard.nextLine();
                                        BufferedWriter bw = new BufferedWriter(new FileWriter(sellerList, true));
                                        bw.append(username + ":" + email + ":" + password + "{" + store + "\n");
                                        bw.close();
                                        break;
                                    } else if (keyboard.nextLine().equalsIgnoreCase("N")) {
                                        System.out.println("No store will be added at this time, but you can add one upon logging in.");
                                        seller = new Seller(username, email, password);
                                        BufferedWriter bw = new BufferedWriter(new FileWriter(sellerList, true));
                                        bw.append(username + ":" + email + ":" + password + "\n");
                                        bw.close();
                                        break;
                                    } else {
                                        System.out.println("Invalid input. No store will be added at this time, but you can add one upon logging in.");
                                        seller = new Seller(username, email, password);
                                        BufferedWriter bw = new BufferedWriter(new FileWriter(sellerList, true));
                                        bw.append(username + ":" + email + ":" + password + "\n");
                                        bw.close();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Sorry, that username has already been taken. Please try again.");
                }
                response = null;
            } else {
                System.out.println("That is not a valid response. Type B for buyer and S for seller.");
                response = keyboard.nextLine();
            }
        }
    }

    //Checks if entered username is already taken
    public static boolean checkUsernameExists(String username, String type) {
        String testUser = username;
        String testType = type;
        if (testType.equalsIgnoreCase("b")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader("buyerList.txt"));
                while (!(testUser = (br.readLine())).equals(null)) {
                    String[] userInfo = testUser.split(":");
                    if ((userInfo[0]).equals(username)) {
                        FrontEnd.username = testUser;
                        br.close();
                        return true;
                    }
                }
                br.close();
                return false;
            } catch (Exception e) {
                return false;
            }
        } else if (testType.equalsIgnoreCase("s")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader("sellerList.txt"));
                while (!(testUser = (br.readLine())).equals(null)) {
                    String[] userInfo = testUser.split(":");
                    if ((userInfo[0]).equals(username)) {
                        FrontEnd.username = testUser;
                        br.close();
                        return true;
                    }
                }
                br.close();
                return false;
            } catch (Exception e) {
                return false;
            }
        } else {
            System.out.println("You must enter B or S.");
            return false;
        }
    }

    // Print the options menu
    public static void giveOptions(String username, String type) throws IOException {
        String value = "0";
        while (!value.equals("10")) {
            System.out.println(
                    "Enter 1 to send a new message.\nEnter 2 to edit a sent message.\nEnter 3 to delete a sent message."
                            +
                            "\nEnter 4 to write to CVS file or Import .txt to existing message.\nEnter 5 to view message statistics.\nEnter 6 to change user visibility settings."
                            +
                            "\nEnter 7 to change user account settings.\nEnter 8 to delete account.\nEnter 9 to add a store.\nEnter 10 to exit the program.");
            value = keyboard.nextLine();
            switch (value) {
                case "1":
                    Messages.writeMessage(username, type);
                    break;
                case "2":
                    Messages.editMessage(username, type);
                    break;
                case "3":
                    Messages.deleteMessage(username, type);
                    break;
                case "4":
                    System.out.println("Would you like to export a converation (e) or import a new file into a conversation (i)?");
                    String choice = keyboard.nextLine();
                    if (choice.equals("e")) {
                        System.out.println("Please enter in the other user's name:");
                        String otherUser = keyboard.nextLine();
                        if (type.equals("b")) {
                            if (checkUsernameExists(otherUser, "s")) {
                                Files.readImportFile(username, otherUser);
                                Files.writeToCSVFile(username, otherUser, Files.readImportFile(username, otherUser));
                            }
                            else {
                                System.out.println("User does not exist!");
                            }
                        }
                        else if (type.equals("s")) {
                            if (checkUsernameExists(otherUser, "b")) {
                                Files.readImportFile(username, otherUser);
                                Files.writeToCSVFile(username, otherUser, Files.readImportFile(username, otherUser));
                            }
                            else {
                                System.out.println("User does not exist!");
                            }
                        }
                    }
                    else if (choice.equals("i")) {
                        System.out.println("Please enter in the other user's name:");
                        String otherUser = keyboard.nextLine();
                        if (type.equals("b")) {
                            if (checkUsernameExists(otherUser, "s")) {
                                Files.readImportFile(username, otherUser);
                                System.out.println("Please enter in the file you would like to import in as a message:");
                                String importFile = keyboard.nextLine();
                                if (new File(importFile).exists()) {
                                    Files.importToFile(importFile, Files.readImportFile(username, otherUser));
                                } else {
                                    System.out.println("That file does not exist!");
                                }
                            }
                            else {
                                System.out.println("User does not exist!");
                            }
                        }
                        else if (type.equals("s")) {
                            if (checkUsernameExists(otherUser, "b")) {
                                Files.readImportFile(username, otherUser);
                                System.out.println("Please enter in the file you would like to import in as a message:");
                                String importFile = keyboard.nextLine();
                                if (new File(importFile).exists()) {
                                    Files.importToFile(importFile, Files.readImportFile(username, otherUser));
                                } else {
                                    System.out.println("That file does not exist!");
                                }
                                Files.importToFile(importFile, Files.readImportFile(username, otherUser));
                            }
                            else {
                                System.out.println("User does not exist!");
                            }
                        }
                    }
                    break;
                case "5":
                    String sortingChoice = "";
                    if (type.equalsIgnoreCase("s")) {
                        System.out.println("How would you like to sort your statistics? Enter up to sort in increasing order, and down to sort in decreasing order.");
                        sortingChoice = keyboard.nextLine();
                        if (sortingChoice.equals("down")) {
                            Seller.sellerStatistic(username, true);
                            Seller.getMostCommonWord(username);
                        } else if (sortingChoice.equals("up")) {
                            Seller.sellerStatistic(username, false);
                            Seller.getMostCommonWord(username);
                        }
                    } else if (type.equalsIgnoreCase("b")) {
                        System.out.println("How would you like to sort your statistics? Enter up to sort in increasing order, and down to sort in decreasing order.");
                        sortingChoice = keyboard.nextLine();
                        if (sortingChoice.equals("down")) {
                            Buyer.customerMessagesReceived(username, true);
                            Buyer.customerMessagesSent(username, true);
                        } else if (sortingChoice.equals("up")) {
                            Buyer.customerMessagesReceived(username, false);
                            Buyer.customerMessagesSent(username, false);
                        }
                    } else {
                        System.out.println("No statistics to report.");
                    }
                    break;
                    
                case "6":
                    userViewSettings(username, type);
                    break;
                case "7":
                    userAccountSettings(username, type);
                    break;
                case "8":
                    userAccountDeletion(username, type);
                    value = "10";
                case "9":
                    if (type.equalsIgnoreCase("s")) {
                        if (attemptToAccessAccount(username, type)) {
                            System.out.println("Please enter a store to add. Store name may not contain colons.");
                            String newStore = keyboard.nextLine();
                            if (Store.getAllStores().contains(newStore) || newStore.contains(":")) {
                                System.out.println("Unable to create store. Invalid store name.");
                                break;
                            } else {
                                Store.addStore(newStore);
                            }

                            break; 
                        } else {
                            System.out.println("Unable to access. Could not verify account details correctly.");
                            break;
                        }
                    } else {
                        System.out.println("Unable to access. Buyers cannot add stores.");
                        break;
                    }
                case "10":
                    System.out.println("Thank you for using Message Sender.");
                    break;
                default:
                    System.out.println("That is not a valid choice. Please enter an integer between 1 and 7.");
            }
        }
    }

    //Shows user options to block and hide other users
    public static void userViewSettings(String username, String type) {
        System.out.println("What action would you like to take?");
        System.out.println("Enter 1 to block a user.\nEnter 2 to unblock a user.\nEnter 3 to hide a user.\n"
        + "Enter 4 to unhide a user.\nEnter 5 to view blocked users.\nEnter 6 to view hidden users.\nEnter 7 to exit user visibility settings.");
        String selection = keyboard.nextLine();
        switch (selection) {
            case "1":
                if (type.equalsIgnoreCase("b")) {
                    System.out.printf("Available users to block: \n");
                        Buyer.displaySellerList();
                        System.out.println("Enter the username of the user you would like to block: ");
                        String userToBlock = keyboard.nextLine();
                        blockSeller(userToBlock);
                } else if (type.equalsIgnoreCase("s")) {
                    System.out.printf("Available users to block: \n");
                        Seller.displayBuyerList();
                        System.out.println("Enter the username of the user you would like to block: ");
                        String userToBlock = keyboard.nextLine();
                        blockBuyer(userToBlock);
                }
            break;
            case "2":
                if (type.equalsIgnoreCase("b")) {
                    System.out.printf("Available users to unblock: \n");
                    Buyer.displayBlockedSellers();
                    System.out.println("Enter the username of the user you would like to unblock: ");
                    String userToUnblock = keyboard.nextLine();
                    unblockSeller(userToUnblock);
                } else if (type.equalsIgnoreCase("s")) {
                    System.out.printf("Available users to unblock: \n");
                    Seller.displayBlockedBuyers();
                    System.out.println("Enter the username of the user you would like to unblock: ");
                    String userToUnblock = keyboard.nextLine();
                    unblockBuyer(userToUnblock);
                }
            break;
            case "3":
                if (type.equalsIgnoreCase("b")) {
                    System.out.printf("Available users to hide: \n");
                    Buyer.displaySellerList();
                    System.out.println("Enter the username of the user you would like to hide: ");
                    String userToHide = keyboard.nextLine();
                    hideSeller(userToHide);
                } else if (type.equalsIgnoreCase("s")) {
                    System.out.printf("Available users to hide: \n");
                    Seller.displayBuyerList();
                    System.out.println("Enter the username of the user you would like to hide: ");
                    String userToHide = keyboard.nextLine();
                    hideBuyer(userToHide);
                }
            break;
            case "4":
                if (type.equalsIgnoreCase("b")) {
                    System.out.printf("Available users to unhide: \n");
                        Buyer.displayHiddenSellers();
                        System.out.println("Enter the username of the user you would like to unhide: ");
                        String userToUnhide = keyboard.nextLine();
                        unhideSeller(userToUnhide);
                } else if (type.equalsIgnoreCase("s")) {
                    System.out.printf("Available users to unhide: \n");
                        Seller.displayHiddenBuyers();
                        System.out.println("Enter the username of the user you would like to unhide: ");
                        String userToUnhide = keyboard.nextLine();
                        unhideBuyer(userToUnhide);
                    }
            break;
            case "5":
                if (type.equalsIgnoreCase("b")) {
                    getBlockedSellers();
                } else if (type.equalsIgnoreCase("s")) {
                    getBlockedBuyers();
                }
            break;
            case "6":
                if (type.equalsIgnoreCase("b")) {
                    getHiddenSellers();
                } else if (type.equalsIgnoreCase("s")) {
                    getHiddenBuyers();
                }
            break;
            case "7":
                System.out.println("Returning to option menu.");
                break;
            default:
                System.out.println("Invalid selection, response must be an integer between 1 and 7. Returning to option menu now.");
                break;
        }
    }

    public static void blockBuyer(String buyer) {
        System.out.println(buyer);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("blockedBuyers.txt", true));
            bw.write(username + "]" + buyer + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unblockBuyer(String buyer) {
        blockedBuyers.remove(buyer);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("blockedBuyers.txt", false));
            BufferedReader bfr = new BufferedReader(new FileReader("blockedBuyers.txt"));
            while (true) {
                String line = bfr.readLine();
                if (line == null) {
                    break;
                }

                if (line.equals(username + ":" + buyer)) {
                    continue;
                } else {
                    bw.write(line);
                }
            }
            bw.close();
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void hideBuyer(String buyer) {
        hiddenBuyers.add(buyer);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("hiddenBuyers.txt", true));
            bw.write(username + "]" + buyer + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unhideBuyer(String buyer) {
        hiddenBuyers.remove(buyer);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("hiddenBuyers.txt", false));
            BufferedReader bfr = new BufferedReader(new FileReader("hiddenBuyers.txt"));
            while (true) {
                String line = bfr.readLine();
                if (line == null) {
                    break;
                }
                if (line.equals(username + ":" + buyer)) {
                    continue;
                } else {
                    bw.write(line);
                }
            }
            bw.close();
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void blockSeller(String seller) {
        blockedSellers.add(seller);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("blockedSellers.txt", true));
            bw.write(username + "]" + seller + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unblockSeller(String seller) {
        blockedSellers.remove(seller);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("blockedSellers.txt", false));
            BufferedReader bfr = new BufferedReader(new FileReader("blockedSellers.txt"));
            while (true) {
                String line = bfr.readLine();
                if (line == null) {
                    break;
                }

                if (line.equals(username + ":" + seller)) {
                    continue;
                } else {
                    bw.write(line);
                }
            }
            bw.close();
            bfr.close();
        } catch (IOException e) {
            System.out.println("The username you entered may not be able to block this user.");
        }
    }

    public static void hideSeller(String seller) {
        hiddenSellers.add(seller);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("hiddenSellers.txt", true));
            bw.write(username + "]" + seller + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unhideSeller(String seller) {
        hiddenSellers.remove(seller);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("hiddenSellers.txt", false));
            BufferedReader bfr = new BufferedReader(new FileReader("hiddenSellers.txt"));
            while (true) {
                String line = bfr.readLine();
                if (line == null) {
                    break;
                }

                if (line.equals(username + ":" + seller)) {
                    continue;
                } else {
                    bw.write(line);
                }
            }
            bw.close();
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getBlockedBuyers() {
        Seller.displayBlockedBuyers();
    }

    public static void getBlockedSellers() {
        Buyer.displayBlockedSellers();
    }

    public static void getHiddenBuyers() {
        Seller.displayHiddenBuyers();
    }

    public static void getHiddenSellers() {
        Buyer.displayHiddenSellers();
    }

    //Shows user options for changing their account information
    public static void userAccountSettings(String username, String type) throws IOException {
        String value = "";
        while (!value.equals("4")) {
            System.out.println("Enter 1 to change your username.\nEnter 2 to change your email.\n" +
                    "Enter 3 to change your password.\nEnter 4 to exit.");
            value = keyboard.nextLine();
            switch (value) {
                case "1":
                    if (type.equals("b")) {
                        Scanner sc = new Scanner(new File("buyerList.txt"));
                        StringBuffer buffer = new StringBuffer();
                        while (sc.hasNextLine()) {
                            buffer.append(sc.nextLine()+System.lineSeparator());
                        }
                        String fileContents = buffer.toString();
                        sc.close();
                        String oldLine = FrontEnd.username;
                        String[] userInfo = oldLine.split(":");
                        String otherInfo = ":" + userInfo[1] + ":" + userInfo[2];
                        System.out.println("Please enter in your new username");
                        String newName = keyboard.nextLine();
                        while (true) {
                            if (!checkUsernameExists(newName, type)) {
                                fileContents = fileContents.replaceAll(oldLine, newName + otherInfo);
                                FileWriter writer = new FileWriter("buyerList.txt");
                                System.out.println("");
                                System.out.println(fileContents);
                                writer.append(fileContents);
                                writer.flush();
                                FrontEnd.username = newName + otherInfo;
                                writer.close();
                                break;
                            } else {
                                System.out.println("Sorry, that username is already taken! Please enter a new username.");
                                newName = keyboard.nextLine();
                            }
                        }
                    }
                    else if (type.equals("s")) {
                        Scanner sc = new Scanner(new File("sellerList.txt"));
                        StringBuffer buffer = new StringBuffer();
                        while (sc.hasNextLine()) {
                            buffer.append(sc.nextLine()+System.lineSeparator());
                        }
                        String fileContents = buffer.toString();
                        sc.close();
                        String oldLine = FrontEnd.username;
                        String[] userInfo = oldLine.split(":");
                        String otherInfo = ":" + userInfo[1] + ":" + userInfo[2];
                        System.out.println("Please enter in your new username");
                        String newName = keyboard.nextLine();
                        while (true) {
                            if (!checkUsernameExists(newName, type)) {
                                fileContents = fileContents.replaceAll(oldLine, newName + otherInfo);
                                FileWriter writer = new FileWriter("sellerList.txt");
                                System.out.println("");
                                System.out.println(fileContents);
                                writer.append(fileContents);
                                writer.flush();
                                FrontEnd.username = newName + otherInfo;
                                writer.close();
                                break;
                            } else {
                                System.out.println("Sorry, that username is already taken! Please enter a new username.");
                                newName = keyboard.nextLine();
                            }
                        }
                    }
                    System.out.println("Username successfully changed.");
                    break;
                case "2":
                    if (type.equals("b")) {
                        Scanner sc = new Scanner(new File("buyerList.txt"));
                        StringBuffer buffer = new StringBuffer();
                        while (sc.hasNextLine()) {
                            buffer.append(sc.nextLine()+System.lineSeparator());
                        }
                        String fileContents = buffer.toString();
                        sc.close();
                        String oldLine = FrontEnd.username;
                        String[] userInfo = oldLine.split(":");
                        String userName = userInfo[0];
                        String otherInfo = ":" + userInfo[2];
                        System.out.println("Please enter in your new email");
                        String newEmail = keyboard.nextLine();
                        fileContents = fileContents.replaceAll(oldLine, userName + ":" + newEmail + otherInfo);
                        FileWriter writer = new FileWriter("buyerList.txt");
                        System.out.println("");
                        System.out.println(fileContents);
                        writer.append(fileContents);
                        writer.flush();
                        writer.close();
                        FrontEnd.email = newEmail;
                    }
                    if (type.equals("s")) {
                        Scanner sc = new Scanner(new File("sellerList.txt"));
                        StringBuffer buffer = new StringBuffer();
                        while (sc.hasNextLine()) {
                            buffer.append(sc.nextLine()+System.lineSeparator());
                        }
                        String fileContents = buffer.toString();
                        sc.close();
                        String oldLine = FrontEnd.username;
                        String[] userInfo = oldLine.split(":");
                        String userName = userInfo[0];
                        String otherInfo = ":" + userInfo[2];
                        System.out.println("Please enter in your new email");
                        String newEmail = keyboard.nextLine();
                        fileContents = fileContents.replaceAll(oldLine, userName + ":" + newEmail + otherInfo);
                        FileWriter writer = new FileWriter("sellerList.txt");
                        System.out.println("");
                        System.out.println(fileContents);
                        writer.append(fileContents);
                        writer.flush();
                        writer.close();
                        FrontEnd.email = newEmail;
                    }
                    break;
                case "3":
                    if (type.equals("b")) {
                        Scanner sc = new Scanner(new File("buyerList.txt"));
                        //instantiating the StringBuffer class
                        StringBuffer buffer = new StringBuffer();
                        //Reading lines of the file and appending them to StringBuffer
                        while (sc.hasNextLine()) {
                            buffer.append(sc.nextLine()+System.lineSeparator());
                        }
                        String fileContents = buffer.toString();
                        sc.close();
                        String oldLine = FrontEnd.password;
                        String[] userInfo = oldLine.split(":");
                        String info = userInfo[0] + ":"  + userInfo[1] + ":";
                        System.out.println("Please enter in your new password");
                        String newPass = keyboard.nextLine();
                        fileContents = fileContents.replaceAll(oldLine, info + newPass);
                        FileWriter writer = new FileWriter("buyerList.txt");
                        System.out.println("");
                        System.out.println(fileContents);
                        writer.append(fileContents);
                        writer.flush();
                        writer.close();
                        FrontEnd.password = newPass;
                    }
                    if (type.equals("s")) {
                        Scanner sc = new Scanner(new File("sellerList.txt"));
                        StringBuffer buffer = new StringBuffer();
                        while (sc.hasNextLine()) {
                            buffer.append(sc.nextLine()+System.lineSeparator());
                        }
                        String fileContents = buffer.toString();
                        sc.close();
                        String oldLine = FrontEnd.username;
                        String[] stores = oldLine.split("\\{");
                        String[] userInfo = stores[0].split(":");
                        String info = userInfo[0] + ":"  + userInfo[1] + ":";
                        System.out.println("Please enter in your new password");
                        String newPass = keyboard.nextLine();
                        fileContents = fileContents.replaceAll(oldLine, info + newPass + "\\{" + stores[1]);
                        FileWriter writer = new FileWriter("sellerList.txt");
                        System.out.println("");
                        System.out.println(fileContents);
                        writer.append(fileContents);
                        writer.flush();
                        writer.close();
                        FrontEnd.password = newPass;
                    }
                    break;
                case "4":
                    System.out.println("Returning to option menu.");
                    break;
                default:
                    System.out.println("That is an invalid choice. Please enter an integer between 1 and 5.");
            }
        }
    }

    //Allows for account to be deleted
    public static void userAccountDeletion(String username, String type) throws IOException {
        Scanner userFile;
        FileWriter writer;
        String userFileString;
        StringBuffer buffer = new StringBuffer();
        while (type != null) {
            if (type.equalsIgnoreCase("b")) {
                userFile = new Scanner(new File("buyerList.txt"));
                writer = new FileWriter(new File("buyerList.txt"));
            } else {
                userFile = new Scanner(new File("sellerList.txt"));
                writer = new FileWriter(new File("sellerList.txt"));
            }
            buffer = new StringBuffer();
            while (userFile.hasNextLine()) {
                if (!userFile.nextLine().contains(username)) {
                    buffer.append(userFile.nextLine() + System.lineSeparator());
                }
            }
            System.out.println("Are you sure you want to do this? Enter y for yes and n for no.");
            String confirm = keyboard.nextLine();

            if (confirm.equalsIgnoreCase("y")) {
                userFileString = buffer.toString();
                writer.write(userFileString);
                writer.flush();
                writer.close();
                System.out.println("Account successfully deleted.");
                type = null;
            } else if (confirm.equalsIgnoreCase("n")) {
                System.out.println("No account deletion has occured. Returning to options menu");
            } else {
                System.out.println("Invalid input. Operation could not be completed.");
            }
        }
    }
}
