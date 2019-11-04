package northwind.listener;

import northwind.service.api.IOrderService;
import northwind.util.OrderIdsCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class NorthwindApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderIdsCache orderIdsCache;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ApplicationStartedEvent) {
            Mono<List<Integer>> orderIdsMono = orderService.getOrderIds();
            orderIdsMono.subscribe(orderIds->orderIdsCache.setOrderIds(orderIds));
        }
    }
}
