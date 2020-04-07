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

    @Column(name = "str")
    private Integer str;

    @Column(name = "con")
    private Integer con;

    @Column(name = "dex")
    private Integer dex;

    @Column(name = "intl")
    private Integer intl;

    @Column(name = "wis")
    private Integer wis;

    @Column(name = "cha")
    private Integer cha;

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

    public Integer getStr() {
        return str;
    }

    public Monster str(Integer str) {
        this.str = str;
        return this;
    }

    public void setStr(Integer str) {
        this.str = str;
    }

    public Integer getCon() {
        return con;
    }

    public Monster con(Integer con) {
        this.con = con;
        return this;
    }

    public void setCon(Integer con) {
        this.con = con;
    }

    public Integer getDex() {
        return dex;
    }

    public Monster dex(Integer dex) {
        this.dex = dex;
        return this;
    }

    public void setDex(Integer dex) {
        this.dex = dex;
    }

    public Integer getIntl() {
        return intl;
    }

    public Monster intl(Integer intl) {
        this.intl = intl;
        return this;
    }

    public void setIntl(Integer intl) {
        this.intl = intl;
    }

    public Integer getWis() {
        return wis;
    }

    public Monster wis(Integer wis) {
        this.wis = wis;
        return this;
    }

    public void setWis(Integer wis) {
        this.wis = wis;
    }

    public Integer getCha() {
        return cha;
    }

    public Monster cha(Integer cha) {
        this.cha = cha;
        return this;
    }

    public void setCha(Integer cha) {
        this.cha = cha;
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
            ", str=" + getStr() +
            ", con=" + getCon() +
            ", dex=" + getDex() +
            ", intl=" + getIntl() +
            ", wis=" + getWis() +
            ", cha=" + getCha() +
            "}";
    }
}
