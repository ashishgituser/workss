package com.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class MultithreadingPractice {
	
	public static void reentranctLockExample() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		ReentrantLock reLock = new ReentrantLock();
		
		executor.submit(() -> {
			reLock.lock();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reLock.unlock();
			}
		});
		
		executor.submit(() -> {
			System.out.println("Is Locked : " + reLock.isLocked());
			System.out.println("Held by me : " + reLock.isHeldByCurrentThread());
			boolean locked = reLock.tryLock();
			System.out.println("Is lock available : " + locked);
		});
		
		executor.shutdown();
	}
	
	public static void reentranctReadWriteLockExample() {
		ExecutorService exector = Executors.newFixedThreadPool(2);
		ReadWriteLock lock = new ReentrantReadWriteLock();
		Map<String, String> data = new HashMap<String, String>();
		
		exector.submit(() -> {
			lock.readLock().lock();
			try {
				System.out.println("Inside read lock.");
				System.out.println(data.get("foo"));
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Inside read unlock.");
				lock.readLock().unlock();
			}
		});
		
		exector.submit(() -> {
			lock.writeLock().lock();
			try {
				System.out.println("Inside write lock.");
				Thread.sleep(1000);
				data.put("foo", "welcome to read write lock");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Inside wrtie unlock");
				lock.writeLock().unlock();
			}
		});
		
		exector.submit(() -> {
			lock.readLock().lock();
			try {
				System.out.println("Inside read lock.");
				System.out.println(data.get("foo"));
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Inside read unlock.");
				lock.readLock().unlock();
			}
		});
		
		exector.submit(() -> {
			lock.readLock().lock();
			try {
				System.out.println("Inside read lock.");
				System.out.println(data.get("foo"));
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Inside read unlock.");
				lock.readLock().unlock();
			}
		});
		
		exector.shutdown();
	}

	public static void main(String arg[]) {

		reentranctReadWriteLockExample();
	}
}

class RunThread implements Runnable {
	@Override
	public void run() {
		System.out.println("Works...");
	}
}