package FunctionalDataProcessing.chapter5.practice5_5;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TradingTest {

	List<Transaction> transactions;
	
	@BeforeEach
	void setUp() throws Exception {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		
		transactions = Arrays.asList(
			new Transaction(brian, 2011, 300),
			new Transaction(raoul, 2012, 1000),
			new Transaction(raoul, 2011, 400),
			new Transaction(mario, 2012, 710),
			new Transaction(mario, 2012, 700),
			new Transaction(alan, 2012, 950)
		);
	}

	@Test
	public void problem1() {
		// 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리
		List<Transaction> tr2011 = transactions
			.stream()
			.filter(t -> t.getYear() == 2011)
			.sorted(Comparator.comparing(Transaction::getValue))
			.collect(Collectors.toList());
		
		System.out.println("p1 answer : " + tr2011);
		assertEquals(tr2011.size(), 2);
	}
	
	@Test
	public void problem2() {
		// 거래자가 근무하는 모든 도시를 중복 없이 나열
		List<String> cities1 = transactions
			.stream()
			.map(t -> t.getTrader().getCity())
			.distinct()
			.collect(Collectors.toList());
		
		Set<String> cities2 = transactions
			.stream()
			.map(t -> t.getTrader().getCity())
			.collect(Collectors.toSet());
		
		System.out.println("p2 answer1 : " + cities1);
		System.out.println("p2 answer2 : " + cities2);
		assertEquals(cities1.size(), 2);
		assertEquals(cities2.size(), 2);
	}
	
	@Test
	public void problem3() {
		// 케임브리지(Cambridge)에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬
		List<Trader> traders = transactions
			.stream()
			.map(Transaction::getTrader)
			.filter(t -> t.getCity().equals("Cambridge"))
			.distinct()
			.sorted(Comparator.comparing(Trader::getName))
			.collect(Collectors.toList());
		
		System.out.println("p3 answer : " + traders);
		assertEquals(traders.size(), 3);
	}
	
	@Test
	public void problem4() {
		// 모든 거래자 이름을 알파벳순으로 정렬해서 반환
		String traderStr1 = transactions
			.stream()
			.map(t -> t.getTrader().getName())
			.distinct()
			.sorted()
			.reduce("", (n1, n2) -> n1 + n2);
		
		String traderStr2 = transactions
			.stream()
			.map(t -> t.getTrader().getName())
			.distinct()
			.sorted()
			.collect(Collectors.joining());
		
		System.out.println("p4 answer1 : " + traderStr1);
		System.out.println("p4 answer2 : " + traderStr2);
		assertEquals(traderStr1.length(), 19);
		assertEquals(traderStr2.length(), 19);
	}
	
	@Test
	public void problem5() {
		// 밀라노(Milan)에 거래자가 있는지 여부
		boolean isMilan = transactions
			.stream()
			.anyMatch(t -> t.getTrader().getCity().equals("Milan"));
		
		assertTrue(isMilan);
	}
	
	@Test
	public void problem6() {
		// 케임브리지(Cambridge)에 거주하는 거래자의 모든 트랜잭션값을 출력
		transactions
			.stream()
			.filter(t -> t.getTrader().getCity().equals("Cambridge"))
			.map(Transaction::getValue)
			.forEach(System.out::println);
	}
	
	@Test
	public void problem7() {
		// 전체 트랜잭션 중 최댓값
		Optional<Integer> max = transactions
			.stream()
			.map(Transaction::getValue)
			.reduce(Integer::max);
		
		assertEquals(max.get(), 1000);
	}
	
	@Test
	public void problem8() {
		// 전체 트랜잭션 중 최솟값
		Optional<Integer> min = transactions
				.stream()
				.map(Transaction::getValue)
				.reduce(Integer::min);
		
		assertEquals(min.get(), 300);
	}

}
