package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreateCommandHandler {

	private final OrderDataMapper orderDataMapper;

	private final OrderCreateHelper orderCreateHelper;

	private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;


	public OrderCreateCommandHandler(
			OrderCreateHelper orderCreateHelper,
			OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher,
			OrderDataMapper orderDataMapper
	) {

		this.orderDataMapper = orderDataMapper;
		this.orderCreateHelper = orderCreateHelper;
		this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
	}


	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
		OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
		orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
		log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
		return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(), "Order created successfully");
	}

}
