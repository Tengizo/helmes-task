package com.helmes.task.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@Entity
@Audited
@AuditTable(value = "SECTOR_AUDIT")
public class Sector extends RevisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToOne
    @JsonManagedReference
    private Sector parent;


    @Column(name = "CHILDREN")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Sector> children;


    public Sector(Long id) {
        this.id = id;
    }

    public Sector() {
    }

    @Override
    public String toString() {
        return "Sector{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }

    @PrePersist
    public void trim() {
        name = name.replace("\u00a0", " ").trim();
    }
}
