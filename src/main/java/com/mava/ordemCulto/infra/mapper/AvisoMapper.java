package com.mava.ordemCulto.infra.mapper;

import com.mava.ordemCulto.domain.avisos.AvisosEntity;
import com.mava.ordemCulto.domain.avisos.dto.AvisosRequestDTO;
import com.mava.ordemCulto.domain.avisos.dto.AvisosResponseDTO;
import com.mava.ordemCulto.domain.cultos.CultoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AvisoMapper {

    @Mapping(target = "cultoId", source = "culto.id")
    AvisosResponseDTO toDTO(AvisosEntity entity);

    @Mapping(target = "culto", source = "cultoId", qualifiedByName = "mapCultoIdToEntity")
    AvisosEntity toEntity(AvisosRequestDTO dto);

    @Named("mapCultoIdToEntity")
    default CultoEntity mapCultoIdToEntity(Long cultoId) {
        if (cultoId == null) return null;
        CultoEntity culto = new CultoEntity();
        culto.setId(cultoId);
        return culto;
    }
}
