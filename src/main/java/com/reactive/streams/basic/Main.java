package com.reactive.streams.basic;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

	/*
    //Iterable
    public static void main(String[] args) {
        Iterable<Integer> iter = () ->
                new Iterator<Integer>() {
                int i = 0;
                final  static  int MAX = 10;
                    @Override
                    public boolean hasNext() {
                        return i< MAX;
                    }

                    @Override
                    public Integer next() {
                        return ++i;
                    }
                };

            for(Integer i : iter){
                System.out.println(i);
            }

            for(Iterator<Integer> it = iter.iterator(); it.hasNext();){
                System.out.println(it.next());
            }

    }*/

    static class IntObservable extends Observable implements Runnable{

        @Override
        public void run() {
            for(int i=1; i<=10 ; i++) {
                setChanged();
                notifyObservers(i); //push
                //실제 넣어줘야 할 데이터 <-->Iterator
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        // Source -> Event/Data -> Observer
        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() + " " +arg);
            }
        };
        IntObservable io = new IntObservable();
        io.addObserver(ob);
        //io.run();

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);

        System.out.println(Thread.currentThread().getName() + " Exit");
        es.shutdown();


    }
}
