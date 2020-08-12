package com.reactive.streams.basic;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Future;
 */
import java.util.concurrent.TimeUnit;



/*public class PubSub {

	// Publisher  <-- Observable
	// Subscriber <-- Observer
	// 1시간 07분 59초

	public static void main(String[] args) throws InterruptedException {
		Iterable<Integer> itr = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		ExecutorService es = Executors.newCachedThreadPool();

		Publisher p = new Publisher() {

			@Override
			public void subscribe(Subscriber subscriber) {
				Iterator<Integer> it = itr.iterator();

				subscriber.onSubscribe(new Subscription() {

					@Override
					public void request(long n) {

						es.execute(() ->{
							int i = 0;
							while(i++ < n){
								if(it.hasNext()) {
									subscriber.onNext(it.next());
								}else {
									subscriber.onComplete();
									break;
								}
							}

						});

					}

					@Override
					public void cancel() {


					}
				});
			}

		};

		Subscriber<Integer> s = new Subscriber<Integer>() {

			Subscription subscription;

			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println(Thread.currentThread().getName() + " onSubscribe");
				this.subscription = subscription;
				this.subscription.request(1);
			}

			@Override
			public void onNext(Integer item) {

				System.out.println(Thread.currentThread().getName()+" onNext " + item);
				this.subscription.request(1);
			}

			@Override
			public void onError(Throwable throwable) {

				System.out.println("onError :: " +throwable.getMessage());
			}

			@Override
			public void onComplete() {

				System.out.println("onComplete");
			}


		};
		p.subscribe(s);
		es.awaitTermination(10, TimeUnit.HOURS);
		es.shutdown();

	}*/




}
