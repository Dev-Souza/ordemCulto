package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.oportunidades.Oportunidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OportunidadesRepository extends JpaRepository<Oportunidades, Long> {
    @Query(value = "SELECT COUNT(*) > 0 FROM avisos WHERE culto_id = :cultoId", nativeQuery = true)
    public boolean buscarRegistroExistente(Long cultoId);

}
