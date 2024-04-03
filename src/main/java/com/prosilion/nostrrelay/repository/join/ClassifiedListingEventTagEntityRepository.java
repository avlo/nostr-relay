package com.prosilion.nostrrelay.repository.join;

import com.prosilion.nostrrelay.entity.join.EventTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EventTagEntityRepository extends JpaRepository<EventTagEntity, Long> {
}