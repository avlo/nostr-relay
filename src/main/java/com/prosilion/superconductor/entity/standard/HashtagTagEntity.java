package com.prosilion.superconductor.entity.standard;

import com.prosilion.superconductor.dto.standard.HashtagTagDto;
import com.prosilion.superconductor.dto.AbstractTagDto;
import com.prosilion.superconductor.entity.AbstractTagEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import nostr.event.BaseTag;
import nostr.event.tag.HashtagTag;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "hashtag_tag")
public class HashtagTagEntity extends AbstractTagEntity {
  //  TODO: below annotations and id necessary for compilation even thuogh same is defined in GenericTagEntity
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String hashtagTag;

  public HashtagTagEntity(@NonNull HashtagTag hashtagTag) {
    this.hashtagTag = hashtagTag.getHashTag();
  }

  @Override
  public BaseTag getAsBaseTag() {
    return new HashtagTag(hashtagTag);
  }

  @Override
  public String getCode() {
    return "t";
  }

  @Override
  public AbstractTagDto convertEntityToDto() {
    return new HashtagTagDto(new HashtagTag(hashtagTag));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HashtagTagEntity that = (HashtagTagEntity) o;
    return Objects.equals(hashtagTag, that.hashtagTag);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(hashtagTag);
  }
}
