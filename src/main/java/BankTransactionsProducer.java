import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class BankTransactionsProducer {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();

        // kafka bootstrap server
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // producer acks
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all"); // strongest producing guarantee
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        // leverage idempotent producer from Kafka 0.11 !
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // ensure we don't push duplicates

        Producer<String, String> producer = new KafkaProducer<>(properties);

        DataAccessCsv dataAccessCsv = new DataAccessCsv();

        List<BankAccount> bankAccountList = dataAccessCsv.readCsv();


        int i = 0;

        int iterate = bankAccountList.size();

        while (true) {
            System.out.println("Producing batch: " + i);
            try {

                for (BankAccount bankAccount : bankAccountList){
                    producer.send(newTransaction(bankAccount));
                    Thread.sleep(100);
                }

                i += 1;
            } catch (InterruptedException e) {
                break;
            }
        }
        producer.close();
    }

    public static ProducerRecord<String, String> newTransaction(BankAccount bankAccount) {
        // creates an empty json {}
        ObjectNode transaction = JsonNodeFactory.instance.objectNode();

        // we write the data to the json document
        transaction.put("fromId", bankAccount.getFromId());
        transaction.put("toId", bankAccount.getToId());
        transaction.put("amount", bankAccount.getBalance());
        transaction.put("time", bankAccount.getFormattedDate());
        return new ProducerRecord<>("bank-transactions", bankAccount.getFromId(), transaction.toString());
    }
}
