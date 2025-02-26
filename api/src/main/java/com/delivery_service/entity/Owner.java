package com.delivery_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "owner")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "shop_id")
  @Setter
  private Integer shopId;

  public Owner(Integer id) {
    this.id = id;
  }


}
