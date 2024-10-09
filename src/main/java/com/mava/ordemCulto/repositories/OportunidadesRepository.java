package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.oportunidades.Oportunidades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OportunidadesRepository extends JpaRepository<Oportunidades, Integer> {
}
