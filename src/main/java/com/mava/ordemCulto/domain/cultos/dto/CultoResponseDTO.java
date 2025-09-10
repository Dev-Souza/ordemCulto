package com.mava.ordemCulto.domain.cultos.dto;

import com.mava.ordemCulto.domain.avisos.dto.AvisosResponseDTO;
import com.mava.ordemCulto.domain.cultos.TipoCulto;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoResponseDTO;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadeResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record CultoResponseDTO(
        Long id,
        String tituloCulto,
        TipoCulto tipoCulto,
        LocalDate dataCulto,
        String dirigente,
        String horaProsperar,
        String preleitor,
        List<OportunidadeResponseDTO> oportunidades,
        List<EquipeIntercessaoResponseDTO> equipeIntercessao,
        List<AvisosResponseDTO> avisos
) {}