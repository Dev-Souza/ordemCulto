package com.mava.ordemCulto.domain.oportunidades;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomePessoa;
    //ENUM
    @Enumerated(EnumType.STRING)
    private MomentoOportunidade momento;
    @ManyToOne
    @JoinColumn(name = "culto_id")
    private Culto culto;
}
