package com.mava.ordemCulto.domain.equipe_intercessao.dto;

import com.mava.ordemCulto.domain.equipe_intercessao.CargoEquipeIntercessao;

public record EquipeIntercessaoRequestDTO(
        String nomeObreiro,
        CargoEquipeIntercessao cargo,
        Long cultoId
) {
}
