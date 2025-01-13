package com.delivery_service.owners.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "shop")
@Getter
@Setter
@ToString
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String description;
  private String address;
  @Column(name = "is_open", nullable = false)
  private Boolean isOpen;
  private String latitude;
  private String longitude;
  private String category;
  private String image;

}
