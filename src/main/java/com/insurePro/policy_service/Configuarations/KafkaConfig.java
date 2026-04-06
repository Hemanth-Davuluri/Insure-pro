package com.insurePro.policy_service.Configuarations;

import com.insurePro.policy_service.Entity.PolicyEntity;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static com.insurePro.policy_service.Constant.AppConstant.DOCUMENT;

@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic topic(){

        return TopicBuilder
                .name(DOCUMENT)
                .build();
    }
}
