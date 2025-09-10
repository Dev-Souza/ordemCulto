package com.mava.ordemCulto.infra.mapper;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessaoEntity;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EquipeIntercessaoMapper {

    @Mapping(target = "cultoId", source = "culto.id")
    EquipeIntercessaoResponseDTO toDTO(EquipeIntercessaoEntity entity);

    @Mapping(target = "culto", source = "cultoId", qualifiedByName = "mapCultoIdToEntity")
    EquipeIntercessaoEntity toEntity(EquipeIntercessaoResponseDTO dto);

    @Named("mapCultoIdToEntity")
    default CultoEntity mapCultoIdToEntity(Long cultoId) {
        if(cultoId == null) return null;
        CultoEntity cultoEntity = new CultoEntity();
        cultoEntity.setId(cultoId);
        return cultoEntity;
    }
}
