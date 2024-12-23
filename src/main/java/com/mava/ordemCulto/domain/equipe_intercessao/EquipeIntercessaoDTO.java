package com.mava.ordemCulto.domain.equipe_intercessao;

public record EquipeIntercessaoDTO(
        Integer id,
        String nomeObreiro,
        CargoEquipeIntercessao cargoEquipeIntercessao,
        Integer cultoId
) {
}
