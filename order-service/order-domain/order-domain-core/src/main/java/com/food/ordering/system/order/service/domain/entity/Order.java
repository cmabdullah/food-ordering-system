package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;

import java.util.List;

public class Order extends AggregateRoot<OrderId> {
	private final CustomerId customerId;

	private final RestaurantId restaurantId;
	private final StreetAddress streetAddress;

	private final Money money;

	private final List<OrderItem> items;

	private TrackingId trackingId;
	private OrderStatus orderStatus;

	private List<String> errorMessages;

	private Order(Builder builder) {
		super.setId(builder.orderId);
		customerId = builder.customerId;
		restaurantId = builder.restaurantId;
		streetAddress = builder.streetAddress;
		money = builder.money;
		items = builder.items;
		trackingId = builder.trackingId;
		orderStatus = builder.orderStatus;
		errorMessages = builder.errorMessages;
	}


	public CustomerId getCustomerId() {
		return customerId;
	}

	public RestaurantId getRestaurantId() {
		return restaurantId;
	}

	public StreetAddress getStreetAddress() {
		return streetAddress;
	}

	public Money getMoney() {
		return money;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public TrackingId getTrackingId() {
		return trackingId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public static final class Builder {
		private OrderId orderId;
		private CustomerId customerId;
		private RestaurantId restaurantId;
		private StreetAddress streetAddress;
		private Money money;
		private List<OrderItem> items;
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> errorMessages;

		private Builder() {
		}

		public static Builder builder() {
			return new Builder();
		}

		public Builder orderId(OrderId val) {
			orderId = val;
			return this;
		}

		public Builder customerId(CustomerId val) {
			customerId = val;
			return this;
		}

		public Builder restaurantId(RestaurantId val) {
			restaurantId = val;
			return this;
		}

		public Builder streetAddress(StreetAddress val) {
			streetAddress = val;
			return this;
		}

		public Builder money(Money val) {
			money = val;
			return this;
		}

		public Builder items(List<OrderItem> val) {
			items = val;
			return this;
		}

		public Builder trackingId(TrackingId val) {
			trackingId = val;
			return this;
		}

		public Builder orderStatus(OrderStatus val) {
			orderStatus = val;
			return this;
		}

		public Builder errorMessages(List<String> val) {
			errorMessages = val;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}
}
