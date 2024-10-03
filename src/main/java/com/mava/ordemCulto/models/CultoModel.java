package com.mava.ordemCulto.models;

import com.mava.ordemCulto.models.enums.TipoCulto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class CultoModel {
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
    //Relacionamento com a classe OportunidadesModel
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OportunidadesModel> oportunidades = new ArrayList<>();
    //Relacionamento com a classe EquipeIntercessaoModel
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EquipeIntercessaoModel> equipeIntercessao = new ArrayList<>();
    //Relacionamento com a classe AvisosModel
    @OneToMany(mappedBy = "culto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AvisosModel> avisos = new ArrayList<>();
}
