package com.monsterbuilder.repository;

import com.monsterbuilder.domain.Basetype;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Basetype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BasetypeRepository extends JpaRepository<Basetype, Long> {

}
