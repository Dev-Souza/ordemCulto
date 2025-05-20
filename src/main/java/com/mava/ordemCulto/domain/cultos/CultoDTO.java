package com.mava.ordemCulto.domain.cultos;

import com.mava.ordemCulto.domain.avisos.Avisos;
import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessao;
import com.mava.ordemCulto.domain.oportunidades.Oportunidades;

import java.time.LocalDate;
import java.util.List;

public record CultoDTO(
        Integer id,
        String tituloCulto,
        TipoCulto tipoCulto,
        LocalDate dataCulto,
        String dirigente,
        String horaProsperar,
        String preleitor,
        List<Oportunidades> oportunidades,
        List<EquipeIntercessao> equipeIntercessao,
        List<Avisos> avisos
) {}