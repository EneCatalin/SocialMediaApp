package com.example.testdb;

import com.example.testdb.entity.SimpleEntity;
import com.example.testdb.repository.SimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TestdbApplication implements CommandLineRunner {


	@Autowired
	private SimpleRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(TestdbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Save a new entity
		SimpleEntity entity = new SimpleEntity("TestEntity");
		repository.save(entity);

		// Retrieve all entities
		List<SimpleEntity> entities = repository.findAll();
		entities.forEach(System.out::println);

		// Optionally, exit the application
		System.exit(0);
	}
}
