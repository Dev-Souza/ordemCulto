package com.mava.ordemCulto.domain.oportunidades;

public record OportunidadeDTO(
        Integer id,
        String nomePessoa,
        MomentoOportunidade momentoOportunidade,
        Integer cultoId
) {

}
