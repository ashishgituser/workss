package com.lambda;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class RLProducer implements Runnable {
	private ReentrantLock lock;
	private Condition condition;
	private Queue<String> storage;
	private int size;

	public RLProducer(ReentrantLock lock, Condition condition, Queue<String> storage, int size) {
		this.lock = lock;
		this.condition = condition;
		this.storage = storage;
		this.size = size;
	}

	public void run() {
		try {
			while (true) {
				lock.lock();
				while (storage.size() == size) {
					try {
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Thread.sleep(1000);
				String item = "Produced Item : " + (storage.size() + 1);
				System.out.println(item);
				storage.add(item);
				condition.signal();
				lock.unlock();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
}

class RLConsumer implements Runnable {
	private ReentrantLock lock;
	private Condition condition;
	private Queue<String> storage;

	public RLConsumer(ReentrantLock lock, Condition condition, Queue<String> storage) {
		this.lock = lock;
		this.condition = condition;
		this.storage = storage;
	}

	public void run() {
		try {
			while (true) {
				lock.lock();
				while (storage.isEmpty()) {
					try {
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Thread.sleep(1000);
				System.out.println("Item consumed : " + storage.remove());
				condition.signal();
				lock.unlock();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
}

public class PCUsingReentrantAndCondition {

	public static void main(String[] args) {
		Queue<String> list = new LinkedList<String>();
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		int size = 5;
		
		Thread producerThread = new Thread(new RLProducer(lock, condition, list, size));
		Thread consumerThread = new Thread(new RLConsumer(lock, condition, list));

		producerThread.start();
		consumerThread.start();
	}

}
