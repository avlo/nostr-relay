package com.prosilion.superconductor.entity.classified;

import com.prosilion.superconductor.dto.ClassifiedListingDto;
import com.prosilion.superconductor.dto.PriceTagDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "classified_listing")
public class ClassifiedListingEventEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String summary;
  private String location;

  @Column(name = "published_at")
  private Long publishedAt;

  public ClassifiedListingEventEntity(@NonNull String title, @NonNull String summary, String location, Long publishedAt) {
    this.title = title;
    this.summary = summary;
    this.location = location;
    this.publishedAt = publishedAt;
  }

  public ClassifiedListingDto convertEntityToDto(PriceTagDto priceTagDto) {
    return new ClassifiedListingDto(title, summary, priceTagDto);
  }
}