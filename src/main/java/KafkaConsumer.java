import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumer {

    public static void main(String[] args){


        Properties properties = new Properties();

        //kafka bootstrap server
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");

        properties.setProperty("key.deserializer",StringDeserializer.class.getName());

        properties.setProperty("value.deserializer",StringDeserializer.class.getName());

        properties.setProperty("group.id","test");

        properties.setProperty("enable.auto.commit","true");

        properties.setProperty("auto.commit.interval.ms","1000");

        properties.setProperty("auto.offset.reset","none");

        org.apache.kafka.clients.consumer.KafkaConsumer<String,String> kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(properties);

        kafkaConsumer.subscribe(Arrays.asList("bank-balance-exactly-once"));


        while (true){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
            for(ConsumerRecord<String,String > consumerRecord : consumerRecords){

                System.out.println("Partition : "+ consumerRecord.partition() +
                " Offset : " + consumerRecord.offset() + " Key Account Id : " + consumerRecord.key() +
                        " Value : " + consumerRecord.value()
                );
            }

        }




    }
}
