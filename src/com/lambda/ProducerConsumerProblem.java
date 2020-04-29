package com.lambda;

import java.util.LinkedList;

class Producer implements Runnable {
	private LinkedList<String> storage;
	private int size;
	
	public Producer(LinkedList<String> storage, int size) {
		this.storage = storage;
		this.size = size;
	}
	
	public void run() {
		try {
			while(true) {
				synchronized (storage) {
					while(storage.size() == size) {
						storage.wait();
					}
					Thread.sleep(1000);
					String item = "Produced Item : " + (storage.size() + 1);
					System.out.println(item);
					storage.add(item);
					storage.notify();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
}

class Consumer implements Runnable {
	private LinkedList<String> storage;
	
	public Consumer(LinkedList<String> storage) {
		this.storage = storage;
	}
	
	public void run() {
		try {
			while(true) {
				synchronized (storage) {
					while(storage.isEmpty()) {
						storage.wait();
					}
					Thread.sleep(1000);
					System.out.println("Item consumed : " + storage.removeFirst());
					storage.notify();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
}

public class ProducerConsumerProblem {

	public static void main(String[] args) {
		
		LinkedList<String> list = new LinkedList<String>();
		int size = 5;
		
		Thread producerThread = new Thread(new Producer(list, size));
		Thread consumerThread = new Thread(new Consumer(list));

		producerThread.start();
		consumerThread.start();
	}

}
