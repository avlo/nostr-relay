package com.prosilion.nostrrelay.repository.join;

import com.prosilion.nostrrelay.entity.join.SubscriberFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SubscriberFilterRepository extends JpaRepository<SubscriberFilter, Long> {
}