package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.cultos.CultoDTO;
import com.mava.ordemCulto.repositories.AvisosRepository;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.EquipeIntercessaoRepository;
import com.mava.ordemCulto.repositories.OportunidadesRepository;
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
    private final AvisosRepository avisosRepository;
    private final OportunidadesRepository oportunidadesRepository;
    private final EquipeIntercessaoRepository equipeIntercessaoRepository;

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
        //Salvar culto
        Culto newCulto = cultoRepository.save(paraEntidade(cultoDTO));

        //Atualização das classes relacionadas
        cultoDTO.oportunidades().forEach(oportunidade -> {
            oportunidade.setCultoId(newCulto.getId());
            oportunidadesRepository.save(oportunidade);
        });
        cultoDTO.equipeIntercessao().forEach(intercessor -> {
            intercessor.setCultoId(newCulto.getId());
            equipeIntercessaoRepository.save(intercessor);
        });
        cultoDTO.avisos().forEach(aviso -> {
            aviso.setCultoId(newCulto.getId());
            avisosRepository.save(aviso);
        });
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

                    // Atualizando avisos
                    if (cultoDTOAtualizado.avisos() != null) {
                        cultoDTOAtualizado.avisos().forEach(aviso -> {
                            aviso.setCultoId(culto.getId());
                            // Verifica se o aviso já existe no banco
                            if (!avisosRepository.buscarRegistroExistente(aviso.getCultoId())) {
                                avisosRepository.save(aviso); // Salva apenas se não existir
                            }
                        });
                        culto.setAvisos(cultoDTOAtualizado.avisos());
                    }

                    // Atualizando equipe de intercessão
                    if (cultoDTOAtualizado.equipeIntercessao() != null) {
                        cultoDTOAtualizado.equipeIntercessao().forEach(intercessor -> {
                            intercessor.setCultoId(culto.getId());
                            // Verifica se o intercessor já existe no banco
                            if (!equipeIntercessaoRepository.buscarRegistroExistente(intercessor.getCultoId())) {
                                equipeIntercessaoRepository.save(intercessor); // Salva apenas se não existir
                            }
                        });
                        culto.setEquipeIntercessao(cultoDTOAtualizado.equipeIntercessao());
                    }

                    // Atualizando oportunidades
                    if (cultoDTOAtualizado.oportunidades() != null) {
                        cultoDTOAtualizado.oportunidades().forEach(oportunidade -> {
                            oportunidade.setCultoId(culto.getId());
                            // Verifica se a oportunidade já existe no banco
                            if (!oportunidadesRepository.buscarRegistroExistente(oportunidade.getCultoId())) {
                                oportunidadesRepository.save(oportunidade); // Salva apenas se não existir
                            }
                        });
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