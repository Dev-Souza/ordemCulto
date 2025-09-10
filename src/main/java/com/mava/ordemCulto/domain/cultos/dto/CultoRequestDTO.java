package com.mava.ordemCulto.domain.cultos.dto;

import com.mava.ordemCulto.domain.avisos.dto.AvisosRequestDTO;
import com.mava.ordemCulto.domain.cultos.TipoCulto;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoRequestDTO;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadesRequestDTO;

import java.time.LocalDate;
import java.util.List;

public record CultoRequestDTO(
        String tituloCulto,
        TipoCulto tipoCulto,
        LocalDate dataCulto,
        String dirigente,
        String horaProsperar,
        String preleitor,
        List<OportunidadesRequestDTO> oportunidades,
        List<EquipeIntercessaoRequestDTO> equipeIntercessao,
        List<AvisosRequestDTO> avisos
) {
}
