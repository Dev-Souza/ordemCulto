package com.mava.ordemCulto.domain.oportunidades.dto;

import com.mava.ordemCulto.domain.oportunidades.MomentoOportunidade;

public record OportunidadeDTO(
        Integer id,
        String nomePessoa,
        MomentoOportunidade momentoOportunidade,
        Integer cultoId
) {

}
