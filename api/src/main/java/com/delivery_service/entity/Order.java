package com.delivery_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@ToString
@Getter
@Setter
public class Order {

  @Id
  @Column(name = "id")
  private String id;
  @Column(name = "shop_id")
  private Integer shopId;
  @Column(name = "customer_id")
  private Integer customerId;
  @Column(name = "delivery_fee")
  private Integer deliveryFee;
  @Column(name = "total_price")
  private Integer totalPrice;
  private String status;
  private String address;
  @Column(name = "is_contactless")
  private Boolean isContactless;
  //  @Column(name = "ordered_at")
//  private LocalDateTime orderedAt;

}
