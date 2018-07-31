import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataAccessCsv {



    public List<BankAccount> readCsv() throws IOException {

        List<BankAccount> bankAccountList = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        //File file = new File("document.csv");

        FileInputStream fis = new FileInputStream("document.csv");

        InputStream is = fis;

        CSVParser parser = CSVParser.parse(is, StandardCharsets.US_ASCII,
                CSVFormat.EXCEL.withHeader());


        for (CSVRecord csvRecord : parser) {

            BankAccount bankAccount = new BankAccount();
            bankAccount.setFromId(csvRecord.get("Sender account"));
            bankAccount.setToId(csvRecord.get("Receiver Account"));
            bankAccount.setDate(LocalDateTime.parse(csvRecord.get("Date"), dtf)
                    .atZone(ZoneId.of("Asia/Istanbul")));
            bankAccount.setBalance(Float.parseFloat(csvRecord.get("Amount")));
            bankAccount.setFormattedDate(csvRecord.get("Date"));
            //bankAccount.setDate();
            bankAccountList.add(bankAccount);
        }

        return bankAccountList;

    }
}
