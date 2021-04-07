package com.example.demo.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pojo.Post;

@RestController
@RequestMapping("/post")
public class MyController {
	@Autowired
	RestTemplate restTemplate;
	
	
	// Reading Feed from Url
	@GetMapping
	public List<Post> getposts() {
		String theUrl = "https://jsonplaceholder.typicode.com/posts";
		ResponseEntity<List<Post>> response = restTemplate.exchange(theUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Post>>() {
				});
		List<Post> todoList = response.getBody();
		return todoList;
	}

	
	//Count of the Objects
	@GetMapping("/count")
	public String getCount() {
		String theUrl = "https://jsonplaceholder.typicode.com/posts";
		ResponseEntity<List<Post>> response = restTemplate.exchange(theUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Post>>() {
				});
		List<Post> todoList = response.getBody();
		return "Count = " + todoList.size();
	}

	//Unique Ids
	@GetMapping("/unique")
	public List<Post> getUniqueposts() {

		String theUrl = "https://jsonplaceholder.typicode.com/posts";
		ResponseEntity<List<Post>> response = restTemplate.exchange(theUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Post>>() {
				});
		List<Post> todoList = response.getBody();
		Set<String> nameSet = new HashSet<>();
		List<Post> post = todoList.stream().filter(e -> nameSet.add(e.getUserId())).collect(Collectors.toList());
		return post;
		
	}
	
	//Modification of the 4th JSON array item
	@PutMapping("/modify")
    public Post updatePostWithResponse() {
        String url = "https://jsonplaceholder.typicode.com/posts/{userId}";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a post object
        Post post = new Post("4", "1800Flowers", "1800Flowers");

        // build the request
        HttpEntity<Post> entity = new HttpEntity<>(post, headers);

        // send PUT request to update post with `id` 31
        ResponseEntity<Post> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, Post.class, 31);
        // check response status code
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
	}   
}
