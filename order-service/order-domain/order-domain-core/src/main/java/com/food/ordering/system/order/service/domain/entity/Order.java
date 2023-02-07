package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
	private final CustomerId customerId;

	private final RestaurantId restaurantId;
	private final StreetAddress streetAddress;

	private final Money price;

	private final List<OrderItem> items;

	private TrackingId trackingId;
	private OrderStatus orderStatus;

	private List<String> errorMessages;

	public void initializeOrder(){
		setId(new OrderId(UUID.randomUUID()));
		trackingId = new TrackingId(UUID.randomUUID());
		orderStatus = OrderStatus.PENDING;
		initializeOrderItems();
	}

	private void initializeOrderItems() {
		long orderId = 1;

		for (OrderItem orderItem: items){
			orderItem.initializeOrderItem(super.getId(), new OrderItemId(orderId++));
		}
	}


	public void validateOrder(){
		validateInitialOrder();
		validateTotalPrice();
		validateItemPrice();
	}


	public void pay() {
		if (orderStatus != OrderStatus.PENDING) {
			throw new OrderDomainException("Order is not in correct state for pay operation!");
		}
		orderStatus = OrderStatus.PAID;
	}

	public void approve() {
		if(orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("Order is not in correct state for approve operation!");
		}
		orderStatus = OrderStatus.APPROVED;
	}

	public void initCancel(List<String> failureMessages) {
		if (orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("Order is not in correct state for initCancel operation!");
		}
		orderStatus = OrderStatus.CANCELLING;
		updateFailureMessages(failureMessages);
	}

	public void cancel(List<String> failureMessages) {
		if (!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)) {
			throw new OrderDomainException("Order is not in correct state for cancel operation!");
		}
		orderStatus = OrderStatus.CANCELLED;
		updateFailureMessages(failureMessages);
	}

	private void updateFailureMessages(List<String> failureMessages) {
		if (this.errorMessages != null && failureMessages != null) {
			this.errorMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
		}
		if (this.errorMessages == null) {
			this.errorMessages = failureMessages;
		}
	}

	private void validateInitialOrder() {
		if (orderStatus != null || getId() != null) {
			throw new OrderDomainException("Order is not in correct state for initialization!");
		}
	}

//	private void validateItemPrice() {
//		if (orderStatus != null || getId() != null){
//			throw new OrderDomainException("Order is not in correct state for initialization!");
//		}
//	}

	private void validateTotalPrice() {

		if (price == null || !price.isGreaterThenZero()){
			throw new OrderDomainException("Total price must be greater then zero!");
		}
	}

	private void validateItemPrice() {
		Money orderItemsTotal = items.stream().map(orderItem -> {
			validatePrice(orderItem);
			return orderItem.getSubTotal();
		}).reduce(Money.ZERO, Money::add);

		if (!price.equals(orderItemsTotal)){
			throw new OrderDomainException("Total price: "+ price.getAmount()
					+" is not equal to Order items total: "+orderItemsTotal.getAmount()+"!");
		}
	}

	private void validatePrice(OrderItem orderItem) {

		if (!orderItem.isPriceValid()){
			throw new OrderDomainException("Order item price: "+orderItem.getPrice().getAmount()+
					" is not valid for product "+ orderItem.getProduct().getId().getValue());
		}
	}

	private Order(Builder builder) {
		super.setId(builder.orderId);
		customerId = builder.customerId;
		restaurantId = builder.restaurantId;
		streetAddress = builder.streetAddress;
		price = builder.price;
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

	public Money getPrice() {
		return price;
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


	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private OrderId orderId;
		private CustomerId customerId;
		private RestaurantId restaurantId;
		private StreetAddress streetAddress;
		private Money price;
		private List<OrderItem> items;
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> errorMessages;

		private Builder() {
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

		public Builder deliveryAddress(StreetAddress val) {
			streetAddress = val;
			return this;
		}

		public Builder price(Money val) {
			price = val;
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
