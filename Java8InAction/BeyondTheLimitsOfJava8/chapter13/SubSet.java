package chapter13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubSet {

	public static void main(String[] args) {
		List<List<Integer>> subsets = subsets(Arrays.asList(1, 4, 9));
		System.out.println("result : " + subsets);
	}
	
	private static List<List<Integer>> subsets(List<Integer> list) {
		if(list.isEmpty()) {
			List<List<Integer>> ans = new ArrayList<>();
			ans.add(Collections.emptyList());
			return ans;
		}
		Integer first = list.get(0);
		List<Integer> rest = list.subList(1, list.size());
		System.out.println("first : " + first + ", rest : " + rest);
		
		List<List<Integer>> subans = subsets(rest);
		List<List<Integer>> subans2 = insertAll(first, subans);
		System.out.println("subans : " + subans + ", subans2 : " + subans2);
		return concat(subans, subans2);
	}

	private static List<List<Integer>> concat(List<List<Integer>> a, List<List<Integer>> b) {
		List<List<Integer>> r = new ArrayList<>(a);
		r.addAll(b);
		return r;
	}

	private static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
		List<List<Integer>> result = new ArrayList<>();
		for(List<Integer> list : lists) {
			List<Integer> copyList = new ArrayList<>();
			copyList.add(first);
			copyList.addAll(list);
			result.add(copyList);
		}
		return result;
	}
}
