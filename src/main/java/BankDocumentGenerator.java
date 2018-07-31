import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class BankDocumentGenerator {



    public static void main(String[] args) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("document.csv", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println("Receiver Account,Sender account,Amount,Date");


        Random rand = new Random();

        int random = 1000;
        while(random>0){

        int counter = 0;
            int randomNum = rand.nextInt((10000 - 100) + 1) + 100;
            int randomNum2 = rand.nextInt((10000 - 100) + 1) + 100;
        while (counter<50){

            int randomNum3 = rand.nextInt((100 - 10) + 1) + 10;
            writer.println(randomNum+","+randomNum2+","+randomNum3+","+"03-03-2018 11:29:00");
            counter++;


        }
        random--;
        }



        writer.close();


    }




}
