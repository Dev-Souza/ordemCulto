package com.mava.ordemCulto.domain.oportunidades.dto;

import com.mava.ordemCulto.domain.oportunidades.MomentoOportunidade;

public record OportunidadeDTO(
        Long id,
        String nomePessoa,
        MomentoOportunidade momentoOportunidade,
        Long cultoId
) {

}
