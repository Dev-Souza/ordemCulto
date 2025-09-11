package com.mava.ordemCulto.domain.cultos;

import com.mava.ordemCulto.domain.avisos.AvisosEntity;
import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessaoEntity;
import com.mava.ordemCulto.domain.oportunidades.OportunidadeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cultos")
public class CultoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 8, max = 100)
    private String tituloCulto;
    //ENUM
    @Enumerated(EnumType.STRING)
    private TipoCulto tipoCulto;
    private LocalDate dataCulto;
    private String dirigente;
    private String horaProsperar;
    private String preleitor;
    //Relacionamento com a classe Oportunidades
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OportunidadeEntity> oportunidades = new ArrayList<>();
    //Relacionamento com a classe EquipeIntercessao
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EquipeIntercessaoEntity> equipeIntercessao = new ArrayList<>();
    //Relacionamento com a classe Avisos
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AvisosEntity> avisos = new ArrayList<>();
}