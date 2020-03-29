package com.monsterbuilder.web.rest;

import com.monsterbuilder.domain.Basetype;
import com.monsterbuilder.repository.BasetypeRepository;
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
 * REST controller for managing {@link com.monsterbuilder.domain.Basetype}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BasetypeResource {

    private final Logger log = LoggerFactory.getLogger(BasetypeResource.class);

    private static final String ENTITY_NAME = "basetype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BasetypeRepository basetypeRepository;

    public BasetypeResource(BasetypeRepository basetypeRepository) {
        this.basetypeRepository = basetypeRepository;
    }

    /**
     * {@code POST  /basetypes} : Create a new basetype.
     *
     * @param basetype the basetype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new basetype, or with status {@code 400 (Bad Request)} if the basetype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/basetypes")
    public ResponseEntity<Basetype> createBasetype(@Valid @RequestBody Basetype basetype) throws URISyntaxException {
        log.debug("REST request to save Basetype : {}", basetype);
        if (basetype.getId() != null) {
            throw new BadRequestAlertException("A new basetype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Basetype result = basetypeRepository.save(basetype);
        return ResponseEntity.created(new URI("/api/basetypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /basetypes} : Updates an existing basetype.
     *
     * @param basetype the basetype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated basetype,
     * or with status {@code 400 (Bad Request)} if the basetype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the basetype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/basetypes")
    public ResponseEntity<Basetype> updateBasetype(@Valid @RequestBody Basetype basetype) throws URISyntaxException {
        log.debug("REST request to update Basetype : {}", basetype);
        if (basetype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Basetype result = basetypeRepository.save(basetype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, basetype.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /basetypes} : get all the basetypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of basetypes in body.
     */
    @GetMapping("/basetypes")
    public List<Basetype> getAllBasetypes() {
        log.debug("REST request to get all Basetypes");
        return basetypeRepository.findAll();
    }

    /**
     * {@code GET  /basetypes/:id} : get the "id" basetype.
     *
     * @param id the id of the basetype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the basetype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/basetypes/{id}")
    public ResponseEntity<Basetype> getBasetype(@PathVariable Long id) {
        log.debug("REST request to get Basetype : {}", id);
        Optional<Basetype> basetype = basetypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(basetype);
    }

    /**
     * {@code DELETE  /basetypes/:id} : delete the "id" basetype.
     *
     * @param id the id of the basetype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/basetypes/{id}")
    public ResponseEntity<Void> deleteBasetype(@PathVariable Long id) {
        log.debug("REST request to delete Basetype : {}", id);
        basetypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
