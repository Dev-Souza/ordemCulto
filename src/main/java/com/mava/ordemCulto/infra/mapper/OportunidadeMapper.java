package com.mava.ordemCulto.infra.mapper;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.oportunidades.OportunidadeEntity;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadeResponseDTO;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadesRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OportunidadeMapper {

    @Mapping(target = "cultoId", source = "culto.id")
    OportunidadeResponseDTO toDTO(OportunidadeEntity entity);

    @Mapping(target = "culto", source = "cultoId", qualifiedByName = "mapCultoIdToEntity")
    OportunidadeEntity toEntity(OportunidadesRequestDTO dto);

    @Named("mapCultoIdToEntity")
    default CultoEntity mapCultoIdToEntity(Long cultoId) {
        if (cultoId == null) return null;
        CultoEntity cultoEntity = new CultoEntity();
        cultoEntity.setId(cultoId);
        return cultoEntity;
    }
}
