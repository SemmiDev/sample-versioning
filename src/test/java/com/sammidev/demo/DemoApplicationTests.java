package com.sammidev.demo;

import com.sammidev.demo.Entity.Post;
import com.sammidev.demo.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	PostRepository postRepository;

	@org.junit.Test
	public void testCreatePost() {

		Post post = new Post("ini adalah tes pos");
		webTestClient.post().uri("/post")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(post), Post.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.text").isEqualTo("this is a test post");
	}

	@Test
	public void testGetAllPosts() {
		webTestClient.get().uri("/posts")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Post.class);
	}

	@Test
	public void testGetSinglePost() {
		Post post =  postRepository.save(new Post("Hello Sam")).block();
		webTestClient.get()
				.uri("/posts/{id}", Collections.singletonMap("id", post.getId()))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

	@Test
	public void	TestUpdatePost() {
		Post post = postRepository.save(new Post("initial posts")).block();
		Post newPostData = new Post("updated post");

		webTestClient.put()
				.uri("/posts/{id}", Collections.singletonMap("id", post.getId()))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(newPostData), Post.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.text").isEqualTo("updated post");
	}

	@Test
	public void testDeletePost() {
		Post post =  postRepository.save(new Post("init gan")).block();
		webTestClient.delete()
				.uri("/posts/{id}", Collections.singletonMap("id",  post.getId()))
				.exchange()
				.expectStatus().isOk();
	}

}