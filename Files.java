/**
 * Project 4- The Sharing Economy (shoe store messages)
 *
 * The Files class allows the user to export conversations of their choice to a csv file and import message details into to an existing conversation.
 *
 * @author Fabian Prado, Wesley Richards, Courtney Kossick, Navya Singh, Arnav Kasat, lab section 36
 *
 * @version November 13, 2023
 *
 *
 */
import java.io.*;
import java.util.ArrayList;

public class Files {

    public static ArrayList<String> readImportFile(String myUserName, String otherName) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        File f = new File(myUserName + "ReceivedMessages.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        try {
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
                //System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(list);
        ArrayList<String> newList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("From: " + otherName)) {
                newList.add(list.get(i));
                newList.add(list.get(i + 1));
                newList.add(list.get(i + 2));
            }
        }
        //System.out.println(newList);
        f = new File(myUserName + "SentMessages.txt");
        fr = new FileReader(f);
        bfr = new BufferedReader(fr);
        try {
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
                //System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("To: " + otherName)) {
                newList.add(list.get(i));
                newList.add(list.get(i + 1));
                newList.add(list.get(i + 2));
            }
        }
        //System.out.println(newList);
        return newList;
    }

    //Users can export details for one or more of their conversations using a csv file.
    //All message details should be preserved: Participants, Message sender, timestamp, and contents.
    public static void writeToCSVFile(String userName, String otherName, ArrayList<String> details) throws FileNotFoundException {
        String fileName = userName + "ConversationWith" + otherName + ".csv";
        File cvsFile = new File(fileName);

        FileOutputStream fos = new FileOutputStream(cvsFile, false);
        PrintWriter pw = new PrintWriter(fos);

        for (int i = 0; i < details.size(); i++) {
            pw.println(details.get(i));
        }
        pw.close();
        System.out.println("File Created!");
    }

    //Users can import a text file (.txt) to an existing conversation.
    //The file text will be sent as a message to the recipient.
    public static void importToFile(String fileName, ArrayList<String> details) throws FileNotFoundException {
        File f = new File(fileName);

        FileOutputStream fos = new FileOutputStream(f, true);
        PrintWriter pw = new PrintWriter(fos);

        for (int i = 0; i < details.size(); i++) {
            pw.println(details.get(i));
        }
        pw.close();
        System.out.println("File Imported To!");
    }
}
