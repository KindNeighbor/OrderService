package OrderService.test;

import OrderService.test.domain.*;
import OrderService.test.domain.item.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);
            Food food1 = createFood("Pizza", 30000, 100);
            em.persist(food1);
            Food food2 = createFood("Hamburger", 10000, 100);
            em.persist(food2);
            OrderItem orderItem1 = OrderItem.createOrderItem(food1, 30000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(food2, 10000, 2);
            Order order = Order.createOrder(member, createDelivery(member),
                    orderItem1, orderItem2);
            em.persist(order);
        }
        public void dbInit2() {
            Member member = createMember("userB", "인천", "2", "2222");
            em.persist(member);
            Food food1 = createFood("Ramen", 5000, 100);
            em.persist(food1);
            Food food2 = createFood("Kim-Bob", 3000, 100);
            em.persist(food2);
            Delivery delivery = createDelivery(member);
            OrderItem orderItem1 = OrderItem.createOrderItem(food1, 5000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(food2, 3000, 4);
            Order order = Order.createOrder(member, delivery, orderItem1,
                    orderItem2);
            em.persist(order);
        }
        private Member createMember(String name, String city, String street,
                                    String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
        private Food createFood(String name, int price, int stockQuantity) {
            Food food = new Food();
            food.setName(name);
            food.setPrice(price);
            food.setStockQuantity(stockQuantity);
            return food;
        }
        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
