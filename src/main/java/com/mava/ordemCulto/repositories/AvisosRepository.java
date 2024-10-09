package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.avisos.Avisos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisosRepository extends JpaRepository<Avisos, Integer> {
}
