package com.mava.ordemCulto.domain.oportunidades.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mava.ordemCulto.domain.oportunidades.MomentoOportunidade;

public record OportunidadesRequestDTO(
        String nomePessoa,
        MomentoOportunidade momentoOportunidade,
        @JsonIgnore
        Long cultoId
) {
}
