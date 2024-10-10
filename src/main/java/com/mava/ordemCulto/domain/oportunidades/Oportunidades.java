package com.mava.ordemCulto.domain.oportunidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mava.ordemCulto.domain.cultos.Culto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oportunidades")
public class Oportunidades {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oportunidades_seq")
    @SequenceGenerator(name = "oportunidades_seq", sequenceName = "oportunidades_seq", allocationSize = 1)
    private Integer id;
    private String nomePessoa;
    //ENUM
    @Enumerated(EnumType.STRING)
    private MomentoOportunidade momento;
    @ManyToOne
    @JoinColumn(name = "culto_id")
    @JsonBackReference
    private Culto culto;
}
