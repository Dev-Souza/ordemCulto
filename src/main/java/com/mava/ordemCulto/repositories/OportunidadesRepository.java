package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.oportunidades.OportunidadeDTO;
import com.mava.ordemCulto.domain.oportunidades.Oportunidades;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OportunidadesRepository extends JpaRepository<Oportunidades, Integer> {
    @Query(value = "SELECT COUNT(*) > 0 FROM avisos WHERE culto_id = :cultoId", nativeQuery = true)
    public boolean buscarRegistroExistente(Integer cultoId);

}
