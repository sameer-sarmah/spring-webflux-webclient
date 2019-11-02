import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import northwind.model.Order;
import northwind.service.api.IOrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import northwind.config.AppConfig;
import northwind.model.Customer;
import northwind.model.OrderItems;
import northwind.model.Product;
import northwind.service.api.ICustomerService;
import northwind.service.api.IOrderItemService;
import northwind.service.api.IProductService;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;



public class Driver {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext
				(AppConfig.class);
		
		String dateStr= "1996-07-04T00:00:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime zonedDateTime = LocalDateTime.parse(dateStr, formatter);
		System.out.println(zonedDateTime);
		
		IProductService productService = ctx.getBean(IProductService.class);
		Mono<Product> productMono = productService.getProduct(1);
		
		ICustomerService customerService  = ctx.getBean(ICustomerService.class);
		Mono<Customer> customerMono = customerService.getCustomer("ALFKI");
		
		IOrderItemService orderItemsSvc = ctx.getBean(IOrderItemService.class);
		Mono<List<OrderItems>> oiMono = orderItemsSvc.getOrderItems(10248);
		
		Mono<Tuple2<Product,Customer>> tuple = Mono.zip(productMono,customerMono);
		tuple.subscribe((t)->{
			Product product = t.getT1();
			Customer customer = t.getT2();
			System.out.println("Customer first name "+customer.getFirstName());
			System.out.println("Product name "+product.getName());
		});

		oiMono.subscribe((orderItems)->{
			for(OrderItems item : orderItems) {
				System.out.println(item.getProduct().getName());
			}
		});

		IOrderService orderService = ctx.getBean(IOrderService.class);
		Mono<Order> orderMono = orderService.getOrder(10248);
		orderMono.subscribe(order ->{
			List<OrderItems> orderItems = order.getOrderItems();
			for(OrderItems item : orderItems) {
				System.out.println(item.getProduct().getName());
			}
		});
		Thread.sleep(100000);
	}

}
