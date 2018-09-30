package com.helmes.task.persistence.model;

import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@AuditTable(value = "USER_AUDIT")
public class User extends RevisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "AGREE_TO_TERMS")
    private Boolean agreeToTerms;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Sector> sectors;
}
