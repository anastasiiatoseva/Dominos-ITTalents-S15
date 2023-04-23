package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private BigDecimal price;

    @Column(name = "time_and_date")
    private LocalDateTime orderingTime;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "id")
    private List<OrderedProduct> orderedProducts;

    @OneToMany(mappedBy = "id")
    private List<OrderedPizza> orderedPizzas;



    public Order( BigDecimal price,User user,
                //  OrderStatus orderStatus,
                  Address address) {
        this.user = user;
        this.price = price;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderingTime = LocalDateTime.now();
    }
    }


//    public Order(Map<ItemInCartDTO, Integer> items, int userId, double price){
//        this.OrderedProducts=items;
//
//  }

