package com.mava.ordemCulto.domain.oportunidades;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oportunidades")
public class OportunidadeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomePessoa;
    //ENUM
    @Enumerated(EnumType.STRING)
    private MomentoOportunidade momentoOportunidade;
    @ManyToOne
    @JoinColumn(name = "culto_id")
    private CultoEntity culto;
}
