package com.delivery_service.entity;

import com.delivery_service.enumeration.RiderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rider")
public class Rider {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @Enumerated(EnumType.STRING)
  private RiderStatus status;
  private Double latitude;
  private Double longitude;

}
