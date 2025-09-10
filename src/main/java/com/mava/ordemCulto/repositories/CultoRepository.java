package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.TipoCulto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CultoRepository extends JpaRepository<CultoEntity, Long>{
    //Filtragem por data
    List<CultoEntity> findByDataCultoBetween(LocalDate dataInicial, LocalDate dataFinal);

    // Filtragem de data mais recentes
    @Query("SELECT c FROM CultoEntity c ORDER BY c.dataCulto")
    List<CultoEntity> findAllByOrderByDataCultoDesc();

    //Buscar tipos de culto
    @Query("SELECT c.tipoCulto FROM CultoEntity c")
    List<TipoCulto> findAllTiposCulto();

    //Sistema de paginação
    Page<CultoEntity> findAll(Pageable pageable);

    List<CultoEntity> findByTituloCultoContainingIgnoreCase(String titulo);
}
