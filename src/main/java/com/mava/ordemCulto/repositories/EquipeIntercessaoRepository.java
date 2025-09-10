package com.mava.ordemCulto.repositories;

import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessaoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EquipeIntercessaoRepository extends JpaRepository<EquipeIntercessaoEntity, Long> {
    @Query(value = "SELECT COUNT(*) > 0 FROM avisos WHERE culto_id = :cultoId", nativeQuery = true)
    public boolean buscarRegistroExistente(Long cultoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM EquipeIntercessaoEntity e WHERE e.culto.id = :cultoId")
    void deleteByCultoId(Long cultoId);
}
