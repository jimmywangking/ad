package com.baron.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.baron.ad.dto.MySqlRowData;
import com.baron.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/***
 @package com.baron.ad.sender
 @author Baron
 @create 2020-09-06-6:38 PM
 */
@SuppressWarnings("ALL")
@Component("KafkaSender")
@Slf4j
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData){
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

//    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
//    public void processMysqlRowData(ConsumerRecord<?, ?> record) {
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            Object msg = kafkaMessage.get();
//            MySqlRowData rowData = JSON.parseObject(msg.toString(), MySqlRowData.class);
//            log.info("Kafka process MySqlRowData: {}", JSON.toJSONString(rowData));
//        }
//
//    }

}
