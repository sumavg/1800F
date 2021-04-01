package com.example.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pojo.Post;

@RestController
@RequestMapping("/posts")
public class MyController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public List<Post> getPosts() {
		String theUrl = "https://jsonplaceholder.typicode.com/posts";
		ResponseEntity<List<Post>> response = restTemplate.exchange(theUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Post>>() {
				});
		List<Post> todoList = response.getBody();

		Set<String> nameSet = new HashSet<>();
		List<Post> post = todoList.stream().filter(e -> nameSet.add(e.getUserId())).collect(Collectors.toList());

		for (Post user1 : post) {
			if (user1.getUserId().equals("4")) {
				user1.setTitle("1800Flowers");
				user1.setBody("1800Flowers");
				break;
			}
		}

		return post;
	}

}
