package com.reactive.streams.futurefx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;


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

		Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();

		@GetMapping("/dr")
		public DeferredResult<String> callableDr() {
			log.info("dr");
			DeferredResult<String> dr = new DeferredResult<>(600000L);
			results.add(dr);
			return dr;
		}

		@GetMapping("/dr/count")
		public String drCount(){
			return String.valueOf(results.size());
		}

		@GetMapping("/dr/event")
		public String drEvent(String msg){
			for(DeferredResult<String> dr : results){
				dr.setResult("Hello " +msg);
				results.remove(dr);
			}
			return "OK";
		}

		@GetMapping("/emitter")
		public ResponseBodyEmitter emitter() throws InterruptedException{
			ResponseBodyEmitter emitter = new ResponseBodyEmitter();

			Executors.newSingleThreadExecutor().submit(()->{
				try {
					for (int i = 1; i <= 50; i++) {
						emiiter.send("<p>Stream " + i + "</p>");
						Thread.sleep(2000);
					}
				}catch (Exception e){

				}
			});


		}*/

	}


	public static void main(String[] args) {
		 SpringApplication.run(DemoApplication.class, args);
	}




}
