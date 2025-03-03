package com.delivery_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order")
@ToString
@Setter
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "order_num")
  private String orderNum;
  @Column(name = "shop_id")
  private Integer shopId;
  @Column(name = "customer_id")
  private Integer customerId;
  @Column(name = "total_price")
  private Integer totalPrice;
  private String status;
  private String address;


}
