package com.monsterbuilder.web.rest;

import com.monsterbuilder.domain.Monster;
import com.monsterbuilder.repository.MonsterRepository;
import com.monsterbuilder.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.monsterbuilder.domain.Monster}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MonsterResource {

    private final Logger log = LoggerFactory.getLogger(MonsterResource.class);

    private static final String ENTITY_NAME = "monster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonsterRepository monsterRepository;

    public MonsterResource(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    /**
     * {@code POST  /monsters} : Create a new monster.
     *
     * @param monster the monster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monster, or with status {@code 400 (Bad Request)} if the monster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monsters")
    public ResponseEntity<Monster> createMonster(@Valid @RequestBody Monster monster) throws URISyntaxException {
        log.debug("REST request to save Monster : {}", monster);
        if (monster.getId() != null) {
            throw new BadRequestAlertException("A new monster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Monster result = monsterRepository.save(monster);
        return ResponseEntity.created(new URI("/api/monsters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monsters} : Updates an existing monster.
     *
     * @param monster the monster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monster,
     * or with status {@code 400 (Bad Request)} if the monster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monsters")
    public ResponseEntity<Monster> updateMonster(@Valid @RequestBody Monster monster) throws URISyntaxException {
        log.debug("REST request to update Monster : {}", monster);
        if (monster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Monster result = monsterRepository.save(monster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, monster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /monsters} : get all the monsters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monsters in body.
     */
    @GetMapping("/monsters")
    public List<Monster> getAllMonsters() {
        log.debug("REST request to get all Monsters");
        return monsterRepository.findAll();
    }

    /**
     * {@code GET  /monsters/:id} : get the "id" monster.
     *
     * @param id the id of the monster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monsters/{id}")
    public ResponseEntity<Monster> getMonster(@PathVariable Long id) {
        log.debug("REST request to get Monster : {}", id);
        Optional<Monster> monster = monsterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(monster);
    }

    /**
     * {@code DELETE  /monsters/:id} : delete the "id" monster.
     *
     * @param id the id of the monster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monsters/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable Long id) {
        log.debug("REST request to delete Monster : {}", id);
        monsterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
