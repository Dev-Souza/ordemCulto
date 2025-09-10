package com.mava.ordemCulto.domain.equipe_intercessao.dto;

import com.mava.ordemCulto.domain.equipe_intercessao.CargoEquipeIntercessao;

public record EquipeIntercessaoDTO(
        Integer id,
        String nomeObreiro,
        CargoEquipeIntercessao cargoEquipeIntercessao,
        Integer cultoId
) {
}
