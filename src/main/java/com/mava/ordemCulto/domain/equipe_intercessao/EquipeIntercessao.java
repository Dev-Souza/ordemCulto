package com.mava.ordemCulto.domain.equipe_intercessao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mava.ordemCulto.domain.cultos.Culto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "equipeIntercessao")
public class EquipeIntercessao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipe_intercessao_seq")
    @SequenceGenerator(name = "equipe_intercessao_seq", sequenceName = "equipe_intercessao_seq", allocationSize = 1)
    private Integer id;
    private String nomeObreiro;
    //ENUM
    @Enumerated(EnumType.STRING)
    private CargoEquipeIntercessao cargo;
    @ManyToOne
    @JoinColumn(name = "culto_id")
    @JsonBackReference
    private Culto culto;
}
