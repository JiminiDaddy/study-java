package shoppingmall;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import shoppingmall.domain.*;
import shoppingmall.domain.item.Item;

import java.util.List;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 07/09/2020
 * Time : 6:07 AM
 */
@DataJpaTest
@RunWith(SpringRunner.class)
//@SpringBootTest
public class BasicCRUDTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;

    private Member member;
    private Order order;

    @Before
    public void init() {
        member = createMember("chpark", "Suwon", "BongYoungLo", "00001");
        order = createOrder(member);
    }

    @Test
    public void saveMemberTest() {
        Assert.assertNotNull(memberRepository);
        Member savedMember = saveMember(member);
        Assert.assertEquals(savedMember.getName(), member.getName());
        System.out.println("Finish, saveMemberTest");
    }

    @Test
    public void findMemberTest() {
        saveMember(member);
        Assert.assertEquals(memberRepository.count(), 1);

        List<Member> members = memberRepository.findByName(member.getName());
        Assert.assertNotNull(members);
        Assert.assertEquals(members.get(0).getAddress().getCity(), member.getAddress().getCity());
        System.out.println("Finish, findMemberTest");
    }

    @Test
    public void saveOrderTest() {
        Assert.assertNotNull(orderRepository);
        Member orderer = saveMember(member);
        Order savedOrder = saveOrder(order);
        Assert.assertNotNull(savedOrder);

        Assert.assertEquals(savedOrder.getMember().getName(), orderer.getName());
        System.out.println("Finish, saveOrderTest");
    }

    /* Item 클래스 변경으로 인해 테스트 일시적 제거
    @Test
    public void findOrderTest() {
        Assert.assertNotNull(order);
        Item item = createItem("macbookpro", 240*10000, 1);
        Assert.assertNotNull(item);
        OrderItem orderItem = createOrderItem(order, item);
        Assert.assertNotNull(orderItem);
        order.addOrderItem(orderItem);
        member.addOrder(order);
        Order savedOrder = orderRepository.save(order);
        Assert.assertNotNull(savedOrder);

        saveMember(member);

        Order findOrder = orderRepository.findById(savedOrder.getId())
                .orElseThrow(null);
        Assert.assertNotNull(findOrder);

        OrderItem findOrderItem = findOrder.getOrderItems().get(0);
        Assert.assertNotNull(findOrderItem);

        Assert.assertEquals(findOrderItem.getItem().getName(), item.getName());
        System.out.println("Finish, findOrderTest");
    }
    */

    private Member createMember(String name, String city, String street, String zipcode) {
        Member m = new Member();
        m.setName(name);
        /* Address Entity 추가로 인해 테스트 방식 변경
        m.setCity(city);
        m.setStreet(street);
        m.setZipcode(zipcode);
        */
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setZipcode(zipcode);
        m.setAddress(address);
        return m;

    }

    private Item createItem(String name, int price, int quantity) {
        /* Item 클래스 변경으로 인해 일시적 제거
        return Item.builder()
                .name(name)
                .price(price)
                .stockQuantity(quantity).build();
         */
        return null;
    }

    private Order createOrder(Member member) {
        return new Order(member);
    }

    private OrderItem createOrderItem(Order order, Item item) {
        return new OrderItem(order, item);
    }

    private Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

}
