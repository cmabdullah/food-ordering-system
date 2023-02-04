package com.food.ordering.system.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {

	private final BigDecimal amount;

	public Money(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isGreaterThenZero() {
		return amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
	}

	public boolean isGreaterThen(Money money) {
		return amount != null && this.amount.compareTo(money.getAmount()) > 0;
	}

	public Money add(Money money) {
		return new Money(setScale(this.amount.add(money.getAmount())));
	}

	public Money subtract(Money money) {
		return new Money(setScale(this.amount.subtract(money.getAmount())));
	}

	public Money multiply(int multiply) {
		return new Money(setScale(this.amount.multiply(new BigDecimal(multiply))));
	}

	public BigDecimal setScale(BigDecimal input) {
		return input.setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public String toString() {
		return "Money{" +
				"amount=" + amount +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Money money = (Money) o;
		return Objects.equals(amount, money.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount);
	}

	public BigDecimal getAmount() {
		return amount;
	}
}