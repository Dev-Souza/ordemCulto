package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.repositories.AvisosRepository;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.EquipeIntercessaoRepository;
import com.mava.ordemCulto.repositories.OportunidadesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private CultoEntity paraEntidade(CultoResponseDTO dto) {
        CultoEntity culto = new CultoEntity();
        culto.setId(dto.id());
        culto.setTituloCulto(dto.tituloCulto());
        culto.setTipoCulto(dto.tipoCulto());
        culto.setDataCulto(dto.dataCulto());
        culto.setDirigente(dto.dirigente());
        culto.setHoraProsperar(dto.horaProsperar());
        culto.setPreleitor(dto.preleitor());
        culto.setOportunidades(dto.oportunidades());
        culto.setEquipeIntercessao(dto.equipeIntercessao());
        culto.setAvisos(dto.avisos());
        return culto;
    }

    // Converter Entity para DTO
    private CultoResponseDTO paraDTO(CultoEntity culto) {
        return new CultoResponseDTO(
                culto.getId(),
                culto.getTituloCulto(),
                culto.getTipoCulto(),
                culto.getDataCulto(),
                culto.getDirigente(),
                culto.getHoraProsperar(),
                culto.getPreleitor(),
                culto.getOportunidades(),
                culto.getEquipeIntercessao(),
                culto.getAvisos()
        );
    }

    // Criar um culto
    public ResponseEntity<CultoEntity> create(CultoResponseDTO cultoDTO) {
        //Salvar culto
        CultoEntity newCulto = cultoRepository.save(paraEntidade(cultoDTO));

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
    public ResponseEntity<List<CultoResponseDTO>> getAll(int pagina, int itens) {
        //Minha paginação
        Page<CultoEntity> cultos = cultoRepository.findAll(PageRequest.of(pagina, itens));
        List<CultoResponseDTO> cultoDTOs = cultos.stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());

        return cultoDTOs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cultoDTOs);
    }

    // Buscar um culto específico
    public ResponseEntity<CultoResponseDTO> getByIdCulto(Long id) {
        return cultoRepository.findById(id)
                .map(culto -> ResponseEntity.ok(paraDTO(culto)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum culto encontrado")
                        .build());
    }

    // Alterar o culto encontrado por ID
    public ResponseEntity<CultoResponseDTO> update(Long id, CultoResponseDTO cultoDTOAtualizado) {
        // Buscando o culto pelo ID
        CultoEntity cultoExistente = cultoRepository.findById(id).orElseThrow(() -> new RuntimeException("Culto não encontrado"));

        // Atualizando os dados do culto
        cultoExistente.setTituloCulto(cultoDTOAtualizado.tituloCulto());
        cultoExistente.setTipoCulto(cultoDTOAtualizado.tipoCulto());
        cultoExistente.setDataCulto(cultoDTOAtualizado.dataCulto());
        cultoExistente.setDirigente(cultoDTOAtualizado.dirigente());
        cultoExistente.setHoraProsperar(cultoDTOAtualizado.horaProsperar());
        cultoExistente.setPreleitor(cultoDTOAtualizado.preleitor());

        //Salvando alterações nas outras tabelas de relacionamento
        cultoDTOAtualizado.oportunidades().forEach(oportunidade -> {
            oportunidade.setCultoId(cultoExistente.getId());
            oportunidadesRepository.save(oportunidade);
        });
        cultoDTOAtualizado.equipeIntercessao().forEach(intercessor -> {
            intercessor.setCultoId(cultoExistente.getId());
            equipeIntercessaoRepository.save(intercessor);
        });
        cultoDTOAtualizado.avisos().forEach(aviso -> {
            aviso.setCultoId(cultoExistente.getId());
            avisosRepository.save(aviso);
        });

        // Salvando o culto atualizado
        cultoRepository.save(cultoExistente);
        // Retornando o culto atualizado como DTO
        return ResponseEntity.ok(cultoDTOAtualizado);
    }

    // Deletar o culto buscado por ID
    public ResponseEntity<Void> delete(Long id) {
        ResponseEntity<CultoResponseDTO> cultoBuscado = getByIdCulto(id);
        cultoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Filtragem de data de culto
    public ResponseEntity<List<CultoEntity>> getCultoByData(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final.");
        }
        List<CultoEntity> listaCultos = cultoRepository.findByDataCultoBetween(dataInicial, dataFinal);
        return ResponseEntity.ok(listaCultos);
    }

    //Buscar datas mais recentes
    public ResponseEntity<List<CultoEntity>> getAllCultosRecentes() {
        List<CultoEntity> cultosFiltrados = cultoRepository.findAllByOrderByDataCultoDesc();
        return ResponseEntity.ok(cultosFiltrados);
    }

    public Long getCount() {
        Long countCultos = cultoRepository.count();
        return countCultos;
    }

    public ResponseEntity<List<CultoResponseDTO>> filtroTitulo(String titulo) {
        List<CultoEntity> cultosFiltrados = cultoRepository.findByTituloCultoContainingIgnoreCase(titulo);
        List<CultoResponseDTO> cultosDTOFiltrados = cultosFiltrados.stream()
                                                .map(this::paraDTO)
                                                .collect(Collectors.toList());

        return cultosDTOFiltrados.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cultosDTOFiltrados);
    }
}