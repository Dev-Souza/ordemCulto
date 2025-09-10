package com.mava.ordemCulto.domain.oportunidades.dto;

import com.mava.ordemCulto.domain.oportunidades.MomentoOportunidade;

public record OportunidadesRequestDTO(
        String nomePessoa,
        MomentoOportunidade momento,
        Long cultoId
) {
}
