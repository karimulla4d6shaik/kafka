package com.app.cfg;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KafkaConfig {

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
	
	@Bean
	public ProducerFactory<String, Object> producerConfig() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);	
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		DefaultKafkaProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(configs);
		return producerFactory;
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory){
		return new KafkaTemplate<>(producerFactory);
	}
}
