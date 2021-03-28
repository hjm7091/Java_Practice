package chapter11;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShopTest {

	static Shop shop;
	
	static List<Shop> shops;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		shop = new Shop("BestShop");
		shops = Arrays.asList(new Shop("BestPrice"),
					  new Shop("LetsSaveBig"),
					  new Shop("MyFavoriteShop"),
					  new Shop("BuyItAll"));
	}

	@Test
	@DisplayName(value = "shop의 priceAsync 메소드 테스트")
	public void getPriceAsync() {
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getPriceAsyncV1("my favorite product");
		long invocationTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Invocation returned after " + invocationTime + " msecs");
		
		try {
			double price = futurePrice.get();
			System.out.printf("Price is %.2f%n", price);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");
	}
	
	@Test
	@DisplayName(value = "findPrices 메소드 테스트")
	public void findPrices() {
		
		System.out.println("core count is : " + Runtime.getRuntime().availableProcessors());
		
		long start = System.nanoTime();
		System.out.println(findPricesV1("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("findPricesV1 Done in " + duration + " msecs");
		
		start = System.nanoTime();
		System.out.println(findPricesV2("myPhone27S"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("findPricesV2 Done in " + duration + " msecs");
		
		start = System.nanoTime();
		System.out.println(findPricesV3("myPhone27S"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("findPricesV3 Done in " + duration + " msecs");
		
		start = System.nanoTime();
		System.out.println(findPricesV4("myPhone27S"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("findPricesV4 Done in " + duration + " msecs");
	}
	
	public List<String> findPricesV1(String product) {
		return shops.stream()
				.map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
				.collect(toList());
	}
	
	public List<String> findPricesV2(String product) {
		return shops.parallelStream()
				.map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
				.collect(toList());
	}
	
	public List<String> findPricesV3(String product) {
		List<CompletableFuture<String>> priceFutures = 
				shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
				.collect(toList());
		
		return priceFutures.stream()
				.map(CompletableFuture::join)
				.collect(toList());
	}
	
	public List<String> findPricesV4(String product) {
		Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});
		
		List<CompletableFuture<String>> priceFutures = 
				shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor))
				.collect(toList());
		
		return priceFutures.stream()
				.map(CompletableFuture::join)
				.collect(toList());
	}

}
