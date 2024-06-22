package com.prosilion.superconductor.entity.join.subscriber;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "subscriber-filter_referenced_event")
public class SubscriberFilterReferencedEvent implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long filterId;
  private String referencedEventId;

  public SubscriberFilterReferencedEvent(Long filterId, String referencedEventId) {
    this.filterId = filterId;
    this.referencedEventId = referencedEventId;
  }
}