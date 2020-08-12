package com.reactive.streams.futurefx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 토비의 봄 TV 8화 - 스프링 리엑티브 웹 개발 4부, 자바와 스프링의 비동기 개발 기술 살펴보기.
 */
@SpringBootApplication
@Slf4j
public class DemoApplication {

	@Component
	public static class MyService{
		@Async
		public Future<String> hello() throws InterruptedException {
			log.debug("hello()");
			Thread.sleep(1000);
			return new AsyncResult<>("hello");
		}
	}

	public static void main(String[] args) {
		try (ConfigurableApplicationContext c =  SpringApplication.run(DemoApplication.class, args)) {

		};
	}

	@Autowired MyService myService;

	@Bean
	ApplicationRunner run(){
		return args -> {
			log.info("run()");
			Future<String> f = myService.hello();
			log.info("exit : " + f.isDone());
			log.info("result : " + f.get());

		};
	}

}
