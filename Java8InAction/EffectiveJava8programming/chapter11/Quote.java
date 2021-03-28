package chapter11;

import chapter11.Discount.Code;
import lombok.Getter;

@Getter
public class Quote {

	private final String shopName;
	private final double price;
	private final Discount.Code discountCode;
	
	public Quote(String shopName, double price, Code discountCode) {
		this.shopName = shopName;
		this.price = price;
		this.discountCode = discountCode;
	}
	
	public static Quote parse(String s) {
		String[] split = s.split(":");
		String shopName = split[0];
		double price = Double.parseDouble(split[1]);
		Discount.Code discountCode = Discount.Code.valueOf(split[2]);
		return new Quote(shopName, price, discountCode);
	}
	
}
