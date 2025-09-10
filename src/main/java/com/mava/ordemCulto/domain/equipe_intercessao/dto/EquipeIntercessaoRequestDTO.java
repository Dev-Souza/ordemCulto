package com.mava.ordemCulto.domain.equipe_intercessao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mava.ordemCulto.domain.equipe_intercessao.CargoEquipeIntercessao;

public record EquipeIntercessaoRequestDTO(
        String nomeObreiro,
        CargoEquipeIntercessao cargoEquipeIntercessao,
        @JsonIgnore
        Long cultoId
) {
}
