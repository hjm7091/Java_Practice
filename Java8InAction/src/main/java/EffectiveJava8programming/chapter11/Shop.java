package EffectiveJava8programming.chapter11;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import lombok.Getter;

@Getter
public class Shop {
	
	String name;
	private static final Random random = new Random();
	
	public Shop(String name) {
		this.name = name;
	}
	
	public Future<Double> getPriceAsyncV1(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread(() -> {
			double price = calculatePrice(product);
			futurePrice.complete(price);
		}).start();
		return futurePrice;
	}
	
	public Future<Double> getPriceAsyncV2(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread(() -> {
			try {
				double price = calculatePrice(product);
				futurePrice.complete(price);
			} catch (Exception e) {
				futurePrice.completeExceptionally(e);
			}
		}).start();
		return futurePrice;
	}
	
	public Future<Double> getPriceAsyncV3(String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product));
	}
	
	// 동기 메서드
	public double getPrice(String product) {
		return calculatePrice(product);
	}
	
	public String getPriceAndDiscountCode(String product) {
		double price = calculatePrice(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return String.format("%s:%.2f:%s", name, price, code);
	}
	
	private double calculatePrice(String product) {
		delay();
		return random.nextDouble() + product.charAt(0) + product.charAt(1);
	}
	
	public String getPriceAndDiscountCodeRandom(String product) {
		double price = calculatePriceRandom(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return String.format("%s:%.2f:%s", name, price, code);
	}
	
	private double calculatePriceRandom(String product) {
		randomDelay();
		return random.nextDouble() + product.charAt(0) + product.charAt(1);
	}
	
	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void randomDelay() {
		int delay = 500 + random.nextInt(2000);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
