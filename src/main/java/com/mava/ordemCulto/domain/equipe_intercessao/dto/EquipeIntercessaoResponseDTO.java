package com.mava.ordemCulto.domain.equipe_intercessao.dto;

import com.mava.ordemCulto.domain.equipe_intercessao.CargoEquipeIntercessao;

public record EquipeIntercessaoResponseDTO(
        Long id,
        String nomeObreiro,
        CargoEquipeIntercessao cargoEquipeIntercessao,
        Long cultoId
) {
}
