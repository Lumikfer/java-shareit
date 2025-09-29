package ru.practicum.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
		"ru.practicum.shareit.user.entity", "ru.practicum.shareit.item.entity","ru.practicum.shareit.booking.entity","ru.practicum.shareit.item.comments.entity"
})
@EnableJpaRepositories(basePackages = {
		"ru.practicum.shareit.user.storage","ru.practicum.shareit.item.storage","ru.practicum.shareit.booking.storage"
})

public class ShareItApp {

	public static void main(String[] args) {
		SpringApplication.run(ShareItApp.class, args);
	}

}
