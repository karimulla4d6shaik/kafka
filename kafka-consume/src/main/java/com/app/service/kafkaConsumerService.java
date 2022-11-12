package com.app.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.app.dto.PostDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class kafkaConsumerService {
	
	@KafkaListener(topics = "demo", groupId = "post-group-id")
	public void fetchPostFromKafkaTopic(PostDto postDto) {
		System.out.println(postDto);
	}
	
	@KafkaListener(topics = "update", groupId = "update")
	public void update(PostDto postDto) {
		System.out.println(postDto);
	}
	
	@KafkaListener(topics = "add", groupId = "add")
	public void add(PostDto postDto) {
		System.out.println(postDto);
	}
	
	
	@KafkaListener(topics = "all", groupId = "all")
	public void consumePosts(String postDtos) {		
		try {
			PostDto[] readValue = new ObjectMapper().readValue(postDtos, PostDto[].class);
			for(PostDto postDto : readValue) {
				System.out.println(postDto);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
