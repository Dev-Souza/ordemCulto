package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.oportunidades.Oportunidades;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OportunidadesRepository extends JpaRepository<Oportunidades, Integer> {
    @Query(value = "SELECT COUNT(*) > 0 FROM avisos WHERE culto_id = :cultoId", nativeQuery = true)
    public boolean buscarRegistroExistente(Integer cultoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Oportunidades o WHERE o.culto.id = :cultoId")
    void deleteByCultoId(Integer cultoId);
}
