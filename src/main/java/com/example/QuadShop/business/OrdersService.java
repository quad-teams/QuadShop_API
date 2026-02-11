package com.example.QuadShop.business;

import com.example.QuadShop.business.Mapper.ToDomain;
import com.example.QuadShop.business.Mapper.ToEntity;
import com.example.QuadShop.domain.Order;
import com.example.QuadShop.domain.OrderItem;
import com.example.QuadShop.domain.Product;
import com.example.QuadShop.persistence.OrdersRepo;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.persistence.entity.OrderEntity;
import com.example.QuadShop.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrdersService {
    public final ProductsRepo productsRepo;
    private final OrdersRepo ordersRepo;

    void CreateCart(){

    }


    void addOrderItem(Long cartId, Long productId, int quantity) {

        Optional<OrderEntity> entity = ordersRepo.findById(cartId);

        if (entity.isEmpty()) return;

        Order cart = ToDomain.ToOrder(entity.get());
        List<OrderItem> items = cart.getOrderItems();

        Product product = ToDomain.toProduct((productsRepo.findById(productId)).get());

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);

        boolean inCart = false;
        for (OrderItem item : items) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(item.getQuantity() + quantity);
                inCart = true;
                break;
            }
        }
        if (!inCart) {
           items.add(orderItem);
           cart.setOrderItems(items);
        }

        ToEntity(cart)


    }


    void RemoveOrderItem(long CartID, int productID){
        Optional<OrderEntity> entity= ordersRepo.findById(CartID);
        if (entity.isEmpty()) return;
        Order cart = ToDomain.ToOrder(entity.get());

        List<OrderItem> Orderitems= cart.getOrderItems();

        for(OrderItem orderItem : Orderitems){
            if (orderItem.getProduct().getId()==productID){

                if (orderItem.getQuantity()>1){
                    orderItem.setQuantity(orderItem.getQuantity()-1);
                }
                else{
                    Orderitems.remove(orderItem);
                }
                break;
            }
        }
        cart.setOrderItems(Orderitems);
        ordersRepo.save(cart);
    }

    void ChangeStatus (long CartID, String status){
        Optional<OrderEntity> entity= ordersRepo.findById(CartID);
        if (entity.isPresent()){
            OrderEntity Cart=entity.get();
            ordersRepo.save(Cart);
        }
    }

    void CancelOrder(long CartID){
        Optional<OrderEntity> cart= ordersRepo.findById(CartID);
        cart.ifPresent(ordersRepo::delete);
    }

    public Product getCartByID(long id) {
        Optional<OrderEntity> entity= ordersRepo.findById(id);
        if (entity.isPresent()){return ToDomain.ToOrder(entity.get());}
        else {return null;}
    }
}
