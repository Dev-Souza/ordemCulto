package com.mava.ordemCulto.domain.avisos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mava.ordemCulto.domain.cultos.Culto;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avisos_seq")
    @SequenceGenerator(name = "avisos_seq", sequenceName = "avisos_seq", allocationSize = 1)
    private Integer id;

    private String nomeAviso;
    private String referente;
    private LocalTime horarioEvento;

    @ElementCollection
    @CollectionTable(name = "avisos_dias", joinColumns = @JoinColumn(name = "aviso_id"))
    @Column(name = "dia_evento")
    private List<LocalDate> diasEvento;

    @ManyToOne
    @JsonBackReference
    private Culto culto;
}