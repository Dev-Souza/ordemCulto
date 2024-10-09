package com.mava.ordemCulto.domain.cultos;

import com.mava.ordemCulto.domain.avisos.Avisos;
import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessao;
import com.mava.ordemCulto.domain.oportunidades.Oportunidades;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Culto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cultos_seq")
    @SequenceGenerator(name = "cultos_seq", sequenceName = "cultos_seq", allocationSize = 1)
    private Integer id;
    @NotNull
    @Size(min = 8, max = 100)
    private String tituloCulto;
    //ENUM
    @Enumerated(EnumType.STRING)
    private TipoCulto tipoCulto;
    @NotNull
    private LocalDate dataCulto;
    @NotNull
    private String dirigente;
    @NotNull
    private String horaProsperar;
    //Relacionamento com a classe Oportunidades
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Oportunidades> oportunidades = new ArrayList<>();
    //Relacionamento com a classe EquipeIntercessao
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EquipeIntercessao> equipeIntercessao = new ArrayList<>();
    //Relacionamento com a classe Avisos
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Avisos> avisos = new ArrayList<>();
}
