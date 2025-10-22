package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.TipoCulto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CultoRepository extends JpaRepository<CultoEntity, Long>, JpaSpecificationExecutor<CultoEntity> {
    //Filtragem por data
    List<CultoEntity> findByDataCultoBetween(LocalDate dataInicial, LocalDate dataFinal);

    //Sistema de paginação
    Page<CultoEntity> findAll(Pageable pageable);
}
