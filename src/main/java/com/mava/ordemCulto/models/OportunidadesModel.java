package com.mava.ordemCulto.models;

import com.mava.ordemCulto.models.enums.MomentoOportunidade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oportunidades")
public class OportunidadesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oportunidades_seq")
    @SequenceGenerator(name = "oportunidades_seq", sequenceName = "oportunidades_seq", allocationSize = 1)
    private Integer id;
    private String nomePessoa;
    //ENUM
    @Enumerated(EnumType.STRING)
    private MomentoOportunidade momento;
    @ManyToOne
    private CultoModel culto;
}
