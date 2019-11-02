package northwind.service.api;

import northwind.model.Order;
import reactor.core.publisher.Mono;

public interface IOrderService {
	Mono<Order> getOrder(int orderID);
}
