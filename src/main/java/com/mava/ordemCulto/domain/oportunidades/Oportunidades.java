package com.mava.ordemCulto.domain.oportunidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mava.ordemCulto.domain.cultos.Culto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oportunidades")
@NoArgsConstructor
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
    @JsonBackReference // Referência inversa não será serializada
    private Culto culto;

    @Transient
    @JsonIgnore //Para ignorar o campo durante a serialização
    private Integer cultoId;

    public Oportunidades(Integer id, String nomePessoa, MomentoOportunidade momentoOportunidade, Integer idCulto) {
        this.id = id;
        this.nomePessoa = nomePessoa;
        this.momento = momentoOportunidade;
        setCultoId(cultoId);
    }

    public void setCultoId(Integer cultoId){
        this.cultoId = cultoId;
        if (cultoId != null) {
            this.culto = new Culto();
            this.culto.setId(cultoId);
        }
    }
}
