package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.dto.PostDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaService {
	@Autowired
	private RestTemplate template;
	@Value("${json.fake.api}")
	private String url;
	
	@Value("${kafka.topic.name}")
	private String topicName;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
		
	
	public PostDto updatePost(PostDto postDto) {
		HttpEntity<PostDto> httpEntity = new HttpEntity<PostDto>(postDto);
		ResponseEntity<PostDto> responseEntity = template.exchange(url+postDto.getId(), HttpMethod.PUT, httpEntity, PostDto.class);
		kafkaTemplate.send("update", responseEntity.getBody());
		return responseEntity.getBody();
	}
	
	public PostDto addPost(PostDto postDto) {
		ResponseEntity<PostDto> responseEntity = template.postForEntity(url, postDto, PostDto.class);		
		kafkaTemplate.send("add", responseEntity.getBody());
		return postDto;
	}

	public PostDto getPost(int id) {
		ResponseEntity<PostDto> responseEntity = template.getForEntity(url+id, PostDto.class);
		PostDto postDto = responseEntity.getBody();
		kafkaTemplate.send(topicName, postDto);
		return postDto;
	}
	
	public List<PostDto> publishAllPosts(){
		ParameterizedTypeReference<List<PostDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<PostDto>>() {};
		ResponseEntity<List<PostDto>> exchange = template
				.exchange(url, HttpMethod.GET, null, parameterizedTypeReference);
		List<PostDto> lists = exchange.getBody();
		try {
			kafkaTemplate.send("all", new ObjectMapper().writeValueAsString(lists));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}
}
