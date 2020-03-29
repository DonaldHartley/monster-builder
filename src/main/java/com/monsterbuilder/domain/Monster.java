package com.monsterbuilder.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Monster.
 */
@Entity
@Table(name = "monster")
public class Monster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "creator_id")
    private Long creatorId;

    @OneToOne
    @JoinColumn(unique = true)
    private Basetype basetype;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Monster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Monster creatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Basetype getBasetype() {
        return basetype;
    }

    public Monster basetype(Basetype basetype) {
        this.basetype = basetype;
        return this;
    }

    public void setBasetype(Basetype basetype) {
        this.basetype = basetype;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Monster)) {
            return false;
        }
        return id != null && id.equals(((Monster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Monster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creatorId=" + getCreatorId() +
            "}";
    }
}
