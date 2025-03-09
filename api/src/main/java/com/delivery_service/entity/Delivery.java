package com.delivery_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivery")
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "order_id")
  private String orderId;
  @Column(name = "location_lat")
  private Double locationLat;
  @Column(name = "location_lon")
  private Double locationLon;
  @Column(name = "rider_id")
  private Integer riderId;
  @Column(name = "match_status")
  private String matchStatus;
}
