package northwind.controller;

import northwind.model.Order;
import northwind.service.api.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(path = "/order/{orderId}",method = RequestMethod.GET)
    public Mono<Order> getOrder(@PathVariable("orderId") int orderId){
        return orderService.getOrder(orderId);
    }
}
