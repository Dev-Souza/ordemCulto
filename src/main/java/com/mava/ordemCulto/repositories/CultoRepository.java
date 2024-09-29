package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.models.CultoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CultoRepository extends JpaRepository<CultoModel, Integer>{
}
