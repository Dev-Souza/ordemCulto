package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.avisos.Avisos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvisosRepository extends JpaRepository<Avisos, Integer> {
    @Query(value = "SELECT COUNT(*) > 0 FROM avisos WHERE culto_id = :cultoId", nativeQuery = true)
    public boolean buscarRegistroExistente(Integer cultoId);
}
