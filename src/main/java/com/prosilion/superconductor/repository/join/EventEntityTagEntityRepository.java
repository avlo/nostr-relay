package com.prosilion.superconductor.repository.join;

import com.prosilion.superconductor.entity.join.EventEntityTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EventEntityTagEntityRepository extends JpaRepository<EventEntityTagEntity, Long> {}