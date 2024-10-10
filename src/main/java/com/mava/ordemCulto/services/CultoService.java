package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.cultos.CultoDTO;
import com.mava.ordemCulto.repositories.CultoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CultoService {
    private final CultoRepository cultoRepository;

    // Converter DTO para Entity
    private Culto paraEntidade(CultoDTO dto) {
        Culto culto = new Culto();
        culto.setId(dto.id());
        culto.setTituloCulto(dto.tituloCulto());
        culto.setTipoCulto(dto.tipoCulto());
        culto.setDataCulto(dto.dataCulto());
        culto.setDirigente(dto.dirigente());
        culto.setHoraProsperar(dto.horaProsperar());
        culto.setOportunidades(dto.oportunidades());
        culto.setEquipeIntercessao(dto.equipeIntercessao());
        culto.setAvisos(dto.avisos());
        return culto;
    }

    // Converter Entity para DTO
    private CultoDTO paraDTO(Culto culto) {
        return new CultoDTO(
                culto.getId(),
                culto.getTituloCulto(),
                culto.getTipoCulto(),
                culto.getDataCulto(),
                culto.getDirigente(),
                culto.getHoraProsperar(),
                culto.getOportunidades(),
                culto.getEquipeIntercessao(),
                culto.getAvisos()
        );
    }

    // Criar um culto
    public ResponseEntity<Culto> create(CultoDTO cultoDTO) {
        Culto newCulto = cultoRepository.save(paraEntidade(cultoDTO));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("created", "Culto criado com sucesso!")
                .body(newCulto);
    }

    // Buscar todos os cultos
    public ResponseEntity<List<CultoDTO>> getAll() {
        List<Culto> cultos = cultoRepository.findAll();
        List<CultoDTO> cultoDTOs = cultos.stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());

        return cultoDTOs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cultoDTOs);
    }

    // Buscar um culto específico
    public ResponseEntity<CultoDTO> getById(Integer id) {
        return cultoRepository.findById(id)
                .map(culto -> ResponseEntity.ok(paraDTO(culto)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum culto encontrado")
                        .build());
    }

    // Alterar o culto encontrado por ID
    public ResponseEntity<CultoDTO> update(Integer id, CultoDTO cultoDTOAtualizado) {
        return cultoRepository.findById(id)
                .map(culto -> {
                    culto.setTituloCulto(cultoDTOAtualizado.tituloCulto());
                    culto.setTipoCulto(cultoDTOAtualizado.tipoCulto());
                    culto.setDataCulto(cultoDTOAtualizado.dataCulto());
                    culto.setDirigente(cultoDTOAtualizado.dirigente());
                    culto.setHoraProsperar(cultoDTOAtualizado.horaProsperar());

                    if (cultoDTOAtualizado.avisos() != null) {
                        culto.setAvisos(cultoDTOAtualizado.avisos());
                    }

                    if (cultoDTOAtualizado.equipeIntercessao() != null) {
                        culto.setEquipeIntercessao(cultoDTOAtualizado.equipeIntercessao());
                    }

                    if (cultoDTOAtualizado.oportunidades() != null) {
                        culto.setOportunidades(cultoDTOAtualizado.oportunidades());
                    }

                    cultoRepository.save(culto);
                    return ResponseEntity.ok()
                            .header("update", "Culto alterado com sucesso!")
                            .body(paraDTO(culto));
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