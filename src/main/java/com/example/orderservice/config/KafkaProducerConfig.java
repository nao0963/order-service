package com.example.orderservice.config;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderDetailDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Bean
    public KafkaTemplate<String, String> getkafkaTemplate() {
        return new KafkaTemplate<>(getProducerFactory());
    }

    private ProducerFactory<String, String> getProducerFactory() {
        return new DefaultKafkaProducerFactory<>(configProps());
    }

    private Map<String,Object> configProps(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.29:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

}
