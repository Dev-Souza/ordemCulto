package com.mava.ordemCulto.domain.cultos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FiltrarCultoPorData {
    private LocalDate dataInicial;
    private LocalDate dataFinal;
}
