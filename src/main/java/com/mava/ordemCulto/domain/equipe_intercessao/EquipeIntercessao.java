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
    @JsonBackReference // Referência inversa não será serializada
    private Culto culto;

    @Transient
    @JsonIgnore //Para ignorar o campo durante a serialização
    private Integer cultoId;

    public void setCultoId(Integer cultoId){
        this.cultoId = cultoId;
        if (cultoId != null) {
            this.culto = new Culto();
            this.culto.setId(cultoId);
        }
    }
}
