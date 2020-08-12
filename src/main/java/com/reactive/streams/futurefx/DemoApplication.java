package com.reactive.streams.futurefx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.Callable;


/**
 * 토비의 봄 TV 8화 - 스프링 리엑티브 웹 개발 4부, 자바와 스프링의 비동기 개발 기술 살펴보기.
 */
@SpringBootApplication
@Slf4j
@EnableAsync
public class DemoApplication {

	@RestController
	public static class MyController{

		@GetMapping("/callable")
		public Callable<String> callable() throws InterruptedException{

			log.info(("async"));
			return () -> {
				log.info("");
				Thread.sleep(2000);
				return "hello";

			};
		}

		@GetMapping("/callable2")
		public String callable2() throws  InterruptedException{
			log.info(("async"));
			Thread.sleep(2000);
			return "hello";
		}

	}


	public static void main(String[] args) {
		 SpringApplication.run(DemoApplication.class, args);
	}




}
