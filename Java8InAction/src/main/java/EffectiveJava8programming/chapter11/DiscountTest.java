package EffectiveJava8programming.chapter11;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountTest {
	
	static List<Shop> shops;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		shops = Arrays.asList(new Shop("BestPrice"),
					  new Shop("LetsSaveBig"),
					  new Shop("MyFavoriteShop"),
					  new Shop("BuyItAll"),
					  new Shop("ShopEasy"));
	}
	
	@Test
	@DisplayName(value = "findPrices 메소드 테스트")
	public void findPrices() {
		long start = System.nanoTime();
		System.out.println(findPricesV1("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("findPricesV1 Done in " + duration + " msecs");
		
		start = System.nanoTime();
		System.out.println(findPricesV2("myPhone27S"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("findPricesV2 Done in " + duration + " msecs");
	}
	
	@Test
	@DisplayName(value = "findPricesV3 메소드 테스트")
	@SuppressWarnings("rawtypes")
	public void findPricesV3() {
		long start = System.nanoTime();
		
		CompletableFuture[] futures = findPricesV3("myPhone")
			.map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + (System.nanoTime() - start) / 1_000_000 + " msecs)")))
			.toArray(size -> new CompletableFuture[size]);
		
		CompletableFuture.allOf(futures).join();
		
		System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
	}
	
	public List<String> findPricesV1(String product) {
		return shops.stream()
				.map(shop -> shop.getPriceAndDiscountCode(product))
				.map(Quote::parse)
				.map(Discount::applyDiscount)
				.collect(toList());
	}
	
	public List<String> findPricesV2(String product) {
		Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});
		
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceAndDiscountCode(product), executor))
				.map(future -> future.thenApply(Quote::parse))
				.map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
				.collect(toList());
		
		return priceFutures.stream()
				.map(CompletableFuture::join)
				.collect(toList());
	}
	
	public Stream<CompletableFuture<String>> findPricesV3(String product) {
		Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});
		
		return shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceAndDiscountCodeRandom(product), executor))
				.map(future -> future.thenApply(Quote::parse))
				.map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscountRandom(quote), executor)));
	}


}
