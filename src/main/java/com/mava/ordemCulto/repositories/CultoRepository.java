package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.cultos.Culto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CultoRepository extends JpaRepository<Culto, Integer>{
    List<Culto> findByDataCultoBetween(LocalDate dataInicial, LocalDate dataFinal);
}
