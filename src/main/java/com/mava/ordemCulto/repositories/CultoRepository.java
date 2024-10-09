package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.cultos.Culto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CultoRepository extends JpaRepository<Culto, Integer>{
}
