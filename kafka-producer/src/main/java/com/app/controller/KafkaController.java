package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PostDto;
import com.app.service.KafkaService;

@RestController
@RequestMapping("/v1/api/post")
public class KafkaController {
	@Autowired
	private KafkaService kafkaService;

	@GetMapping
	public ResponseEntity<PostDto> getPost(@RequestParam("id") Integer id){
		return new ResponseEntity<>(kafkaService.getPost(id), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<PostDto>> getPosts(){
		return new ResponseEntity<>(kafkaService.publishAllPosts(), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto){
		return new ResponseEntity<>(kafkaService.addPost(postDto), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto){
		return new ResponseEntity<>(kafkaService.updatePost(postDto), HttpStatus.OK);
	}
}
