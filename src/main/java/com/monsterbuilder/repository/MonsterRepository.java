package com.monsterbuilder.repository;

import com.monsterbuilder.domain.Monster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Monster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {

}
