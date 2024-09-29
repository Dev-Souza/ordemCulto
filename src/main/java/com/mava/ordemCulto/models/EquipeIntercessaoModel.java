package com.mava.ordemCulto.models;

import com.mava.ordemCulto.models.enums.CargoEquipeIntercessao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "equipeIntercessao")
public class EquipeIntercessaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipe_intercessao_seq")
    @SequenceGenerator(name = "equipe_intercessao_seq", sequenceName = "equipe_intercessao_seq", allocationSize = 1)
    private Integer id;
    private String nomeObreiro;
    //ENUM
    @Enumerated(EnumType.STRING)
    private CargoEquipeIntercessao cargo;
    @ManyToOne
    private CultoModel culto;
}
