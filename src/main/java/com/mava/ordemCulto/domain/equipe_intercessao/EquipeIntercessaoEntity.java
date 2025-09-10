package com.mava.ordemCulto.domain.equipe_intercessao;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "equipeIntercessao")
public class EquipeIntercessaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeObreiro;
    //ENUM
    @Enumerated(EnumType.STRING)
    private CargoEquipeIntercessao cargo;
    @ManyToOne
    @JoinColumn(name = "culto_id")
    private CultoEntity culto;
}
