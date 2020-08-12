package com.reactive.streams.futurefx;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

//Future //
@Slf4j
public class FutureEX {

    interface SuccessCallback{
        void onSuccess(String result);
    }
    interface  ExceptionCallback{
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String>{
        SuccessCallback sc;
        ExceptionCallback ec;
        public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }
        @Override
        protected void done() {
            try{
               sc.onSuccess(get());
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }catch (ExecutionException e){
                ec.onError(e.getCause());
            }
        }

    }

    public static void main(String[] args) throws  InterruptedException, ExecutionException {

        ExecutorService es = Executors.newCachedThreadPool();
        CallbackFutureTask f = new CallbackFutureTask(() ->{
            Thread.sleep(2000);
            if(1==1) throw  new RuntimeException("Async ERROR!!!");
            log.info("Async");
            return "Hello";
        },
                s-> System.out.println("Result : " + s),
                e-> System.out.println("Error : " + e.getMessage()));

        es.execute(f);
        es.shutdown();

       FutureTask<String> f2 = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "Hello";

        }){
            @Override
            protected void done() {
               try{
                   System.out.println(get());
               }catch (InterruptedException e){
                    e.printStackTrace();
               }catch (ExecutionException e){
                   e.printStackTrace();
               }
            }
        };



        es.execute(f2);
        es.shutdown();



    }
}
