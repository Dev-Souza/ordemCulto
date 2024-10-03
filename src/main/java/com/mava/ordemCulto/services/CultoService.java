package com.mava.ordemCulto.services;

import com.mava.ordemCulto.models.CultoModel;
import com.mava.ordemCulto.repositories.CultoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CultoService {
    private final CultoRepository cultoRepository;

    // Criar um culto
    public ResponseEntity<CultoModel> create(CultoModel cultoModel) {
        // Caso meu cultoModel já venha com ID é porque já existe um culto cadastrado
        if (cultoModel.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Error", "Culto já existe")
                    .body(null);
        }
        // Caso não venha com ID, salva um novo registro
        CultoModel newCulto = cultoRepository.save(cultoModel);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("created", "Culto criado com sucesso!")
                .body(newCulto);
    }

    // Buscar todos os cultos
    public ResponseEntity<List<CultoModel>> getAll() {
        List<CultoModel> cultos = cultoRepository.findAll();
        return cultos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cultos);
    }

    // Buscar um culto em específico
    public ResponseEntity<CultoModel> getById(Integer id) {
        return cultoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum culto encontrado")
                        .build());
    }

    // Alterar o culto encontrado por ID
    public ResponseEntity<CultoModel> update(Integer id, CultoModel cultoAtualizado) {
        return cultoRepository.findById(id)
                .map(culto -> {
                    culto.setTituloCulto(cultoAtualizado.getTituloCulto());
                    culto.setTipoCulto(cultoAtualizado.getTipoCulto());
                    culto.setDataCulto(cultoAtualizado.getDataCulto());
                    culto.setDirigente(cultoAtualizado.getDirigente());
                    culto.setHoraProsperar(cultoAtualizado.getHoraProsperar());

                    if (cultoAtualizado.getAvisos() != null) {
                        culto.setAvisos(cultoAtualizado.getAvisos());
                    }

                    if (cultoAtualizado.getEquipeIntercessao() != null) {
                        culto.setEquipeIntercessao(cultoAtualizado.getEquipeIntercessao());
                    }

                    if (cultoAtualizado.getOportunidades() != null) {
                        culto.setOportunidades(cultoAtualizado.getOportunidades());
                    }

                    cultoRepository.save(culto);
                    return ResponseEntity.ok()
                            .header("update", "Culto alterado com sucesso!")
                            .body(culto);
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Culto não existe!")
                        .build());
    }

    // Deletar o culto buscado por ID
    public ResponseEntity<Void> delete(Integer id) {
        if (!cultoRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("error", "Nenhum culto encontrado")
                    .build();
        }

        cultoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
