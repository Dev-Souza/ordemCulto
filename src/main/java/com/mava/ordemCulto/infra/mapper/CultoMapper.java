package com.mava.ordemCulto.infra.mapper;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        OportunidadeMapper.class,
        EquipeIntercessaoMapper.class,
        AvisoMapper.class
})
public interface CultoMapper {

    // Mapeamento de DTO para Entity (caso precise criar a entidade a partir do DTO)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "oportunidades", ignore = true)
    @Mapping(target = "equipeIntercessao", ignore = true)
    @Mapping(target = "avisos", ignore = true)
    CultoEntity toEntity(CultoResponseDTO dto);

    // Mapeamento de Entity para DTO
    @Mapping(target = "oportunidades", source = "oportunidades")
    @Mapping(target = "equipeIntercessao", source = "equipeIntercessao")
    @Mapping(target = "avisos", source = "avisos")
    CultoResponseDTO toDTO(CultoEntity entity);
}