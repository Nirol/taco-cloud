package tacos.data;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tacos.domain.Order;




public interface OrderRepository
extends CrudRepository<Order, Long> {
	
	List<Order> findByDeliveryZip(String deliveryZip);
	
	
	List<Order> readOrdersByDeliveryZipAndCreatedAtBetween(
			 String deliveryZip, Date startDate, Date endDate);
	
	// the old fashion way for building queries:
	@Query("SELECT o FROM Order o WHERE o.deliveryCity='Seattle'")
	List<Order> readOrdersDeliveredInSeattle();


	
}