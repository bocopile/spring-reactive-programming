package com.reactive.streams.operator;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

/**
 * TOBY TY 6회 - 스프링 리엑티브 웹 개발 (2) - O	perators
 * 
 * Reactive Sterams - Operators
 * 
 * pub -> [Data1] -> mapPub -> [Data2] -> LogSub
 * 				  <- subscribe(logSub)
 *     			  <- onSubscribe(s)
 *       		  -> onNext
 *       		  -> onNext
 *        		  <- onComplete
 * 1. map (d1 -> f -> d2)
 */
@Slf4j
public class PubSub {

	public static void main(String[] args) {
		Publisher<Integer> pub = iterPub(Stream.iterate(1, a->a+1).limit(10).collect(Collectors.toList())); 
		Publisher<String> mapPub = mapPub(pub, s-> "[" + s + "]");
		//Publisher<Integer> map2Pub = mapPub(mapPub, s-> -s);
		mapPub.subscribe(logSub());
		

	//	Publisher<Integer> sumPub = sumPub(pub);
	//	sumPub.subscribe(logSub());
		
		Publisher<StringBuilder> reducePub = reducePub(pub, new StringBuilder(), (a,b) -> a.append(b+","));
		reducePub.subscribe(logSub());

				
	}
	
	
	private static <T,R> Publisher<R> reducePub(Publisher<T> pub, R init,
			BiFunction<R, T, R> bf) {
	
		return new Publisher<R>() {

			@Override
			public void subscribe(Subscriber<? super R> sub) {
				
				pub.subscribe(new DelegateSub<T, R>(sub) {
					R result = init;
					@Override
					public void onNext(T t) {
						result = bf.apply(result,t);
					}
					
					@Override
					public void onComplete() {
						// TODO Auto-generated method stub
						sub.onNext(result);
						sub.onComplete();
					}
				});
				
			}
		};
	}
	/*
	private static Publisher<Integer> sumPub(Publisher<Integer> pub) {	
		return new Publisher<Integer>() {

			@Override
			public void subscribe(Subscriber<? super Integer> sub) {
				pub.subscribe(new DelegateSub(sub) {
					int sum = 0;
					
					@Override
					public void onNext(Integer t) {
						sum += t;
					}
					
					@Override
					public void onComplete() {
						sub.onNext(sum);
						sub.onComplete();
					}
				});
				
			}
		};
	}
*/
	private static <T,R> Publisher<R> mapPub(Publisher<T> pub, Function<T, R> f){
		return new Publisher<R>() {

			@Override
			public void subscribe(Subscriber<? super R> sub) {
				pub.subscribe(new DelegateSub<T,R>(sub) {
					
					@Override
					public void onNext(T i) {
						sub.onNext(f.apply(i));
					}
				});
			}
		};
	}
	
	// T -> R
	private static <T> Publisher<T> iterPub(List<T> iter){
		return new Publisher<T>() {
			
			@Override
			public void subscribe(Subscriber<? super T> sub) {
					sub.onSubscribe(new Subscription() {
						
						@Override
						public void request(long n) {
							try {
								iter.forEach(s->sub.onNext(s));
								sub.onComplete();
							}catch (Throwable t) {
								sub.onError(t);
							}
						}
						
						@Override
						public void cancel() {
							
							
						}
					});
				
			}
		};
	}
	
	private static <T> Subscriber<T> logSub(){
		return new Subscriber<T>() {

			@Override
			public void onSubscribe(Subscription s) {
				log.debug("onSubscribe:");
				s.request(Long.MAX_VALUE);
				
			}

			@Override
			public void onNext(T t) {
				log.debug("onNext : {}", t);
				
			}

			@Override
			public void onError(Throwable t) {
				log.debug("onError : {}", t);
				
			}

			@Override
			public void onComplete() {
				log.debug("onComplete");
				
			}
		};
	}

	

}
