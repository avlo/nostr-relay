package com.prosilion.nostrrelay.entity.join;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "subscriber-filter-join")
public class SubscriberFilter implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long subscriberId;

  @Column(name = "\"since\"")
  private Long since;

  @Column(name = "\"until\"")
  private Long until;

  @Column(name = "\"limit\"")
  private Integer limit;

  public SubscriberFilter(Long subscriberId, Long since, Long until, Integer limit) {
    this.subscriberId = subscriberId;
    this.since = since;
    this.until = until;
    this.limit = limit;
  }
}