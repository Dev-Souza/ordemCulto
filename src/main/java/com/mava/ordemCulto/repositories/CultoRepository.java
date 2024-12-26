package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.cultos.CultoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CultoRepository extends JpaRepository<Culto, Integer>{
    //Filtragem por data
    List<Culto> findByDataCultoBetween(LocalDate dataInicial, LocalDate dataFinal);

    // Filtragem de data mais recentes
    @Query("SELECT c FROM Culto c ORDER BY c.dataCulto")
    List<Culto> findAllByOrderByDataCultoDesc();
}
