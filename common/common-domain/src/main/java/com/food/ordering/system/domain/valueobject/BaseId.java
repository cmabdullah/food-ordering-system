package com.food.ordering.system.domain.valueobject;

import com.food.ordering.system.domain.entity.BaseEntity;

import java.util.Objects;

public class BaseId<T> {

	private final T value;

	public BaseId(T value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BaseId<?> baseId = (BaseId<?>) o;
		return Objects.equals(value, baseId.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "BaseId{" +
				"value=" + value +
				'}';
	}
}
