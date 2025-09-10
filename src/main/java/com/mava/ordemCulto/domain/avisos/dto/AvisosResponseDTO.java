package com.mava.ordemCulto.domain.avisos.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AvisosResponseDTO(
        Long id,
        String nomeAviso,
        String referente,
        LocalTime horarioEvento,
        List<LocalDate>diasEvento,
        Long cultoId
) {
}