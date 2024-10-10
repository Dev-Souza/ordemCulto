package com.mava.ordemCulto.domain.equipe_intercessao;

public record EquipeIntercesaoDTO(
        Integer id,
        String nomeObreiro,
        CargoEquipeIntercessao cargoEquipeIntercessao,
        Integer cultoId
) {
}
