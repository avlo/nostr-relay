package com.prosilion.nostrrelay.repository.join;

import com.prosilion.nostrrelay.entity.join.ClassifiedListingEntityEventEntityJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ClassifiedListingEntityEventEntityRepositoryJoin extends JpaRepository<ClassifiedListingEntityEventEntityJoin, Long> {
}