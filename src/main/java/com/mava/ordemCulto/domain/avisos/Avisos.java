package com.mava.ordemCulto.domain.avisos;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "avisos")
public class Avisos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeAviso;
    private String referente;
    private LocalTime horarioEvento;

    @ElementCollection
    @CollectionTable(name = "avisos_dias", joinColumns = @JoinColumn(name = "aviso_id"))
    @Column(name = "dia_evento")
    private List<LocalDate> diasEvento;

    @ManyToOne
    @JoinColumn(name = "culto_id")
    private CultoEntity culto;
}