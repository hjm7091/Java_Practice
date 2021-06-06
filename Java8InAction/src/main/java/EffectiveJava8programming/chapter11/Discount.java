package EffectiveJava8programming.chapter11;

import java.util.Random;

public class Discount {

	private static final Random random = new Random();
	
	public enum Code {
		NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
		
		private final int percentage;

		private Code(int percentage) {
			this.percentage = percentage;
		}
	}
	
	public static String applyDiscountRandom(Quote quote) {
		return quote.getShopName() + " price is " + Discount.applyRandom(quote.getPrice(), quote.getDiscountCode());
	}

	private static double applyRandom(double price, Code code) {
		randomDelay();
		return format(price * (100 - code.percentage) / 100);
	}
	
	public static String applyDiscount(Quote quote) {
		return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
	}

	private static double apply(double price, Code code) {
		delay();
		return format(price * (100 - code.percentage) / 100);
	}

	private static double format(double d) {
		return Double.parseDouble(String.format("%.2f", d));
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
