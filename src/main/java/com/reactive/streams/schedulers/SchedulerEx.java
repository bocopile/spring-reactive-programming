package com.reactive.streams.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 토비의 봄 TV 7화 - 리엑티브 스프링 웹 개발 (3) - Reactive Streams - Schedulers
 *
 */
@Slf4j
public class SchedulerEx {
	// springboot 2.0.0 -> reactive web
	public static void main(String[] args) {
		Publisher<Integer> pub = sub -> {
			sub.onSubscribe(new Subscription() {
				
				@Override
				public void request(long n) {
					log.debug("request()");
					sub.onNext(1);
					sub.onNext(2);
					sub.onNext(3);
					sub.onNext(4);
					sub.onNext(5);
					sub.onComplete();
				}
				
				@Override
				public void cancel() {
					
					
				}
			});
		};
		
		/*
		Publisher<Integer> subOnPub = sub -> {
			ExecutorService es = Executors.newSingleThreadExecutor();
			es.execute(()->pub.subscribe(sub));
			
					pubOnPub.subscribe(new Subscriber<Integer>() {

			@Override
			public void onSubscribe(Subscription s) {
				log.debug("onSubscribe");
				s.request(Long.MAX_VALUE);		
			}

			@Override
			public void onNext(Integer t) {
				log.debug("onNext {}", t);
			}

			@Override
			public void onError(Throwable t) {			
				log.debug("onError {} ", t);
			}

			@Override
			public void onComplete() {			
				log.debug("onComplete");
			}
		});
		
		System.out.println("exit");
		};
		*/ 
		
		Publisher<Integer> pubOnPub = sub -> {
			pub.subscribe(new Subscriber<Integer>() {
				
				ExecutorService es = Executors.newSingleThreadExecutor();

				@Override
				public void onSubscribe(Subscription s) {
					sub.onSubscribe(s);
					
				}

				@Override
				public void onNext(Integer t) {
					es.execute(()-> sub.onNext(t));
					
				}

				@Override
				public void onError(Throwable t) {
					es.execute(()-> sub.onError(t));
					
				}

				@Override
				public void onComplete() {
					es.execute(()-> sub.onComplete());
					
				}
			});
		};
		
		pubOnPub.subscribe(new Subscriber<Integer>() {

			@Override
			public void onSubscribe(Subscription s) {
				log.debug("onSubscribe");
				s.request(Long.MAX_VALUE);		
			}
			
			@Override
			public void onNext(Integer t) {
				log.debug("onNext {}", t);
			}
			
			@Override
			public void onError(Throwable t) {			
				log.debug("onError {} ", t);
			}
			
			@Override
			public void onComplete() {			
				log.debug("onComplete");
			}
		});
		

	}
	

}
