package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.avisos.Avisos;
import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoRequestDTO;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessaoEntity;
import com.mava.ordemCulto.domain.oportunidades.OportunidadeEntity;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadesRequestDTO;
import com.mava.ordemCulto.infra.mapper.AvisoMapper;
import com.mava.ordemCulto.infra.mapper.CultoMapper;
import com.mava.ordemCulto.infra.mapper.EquipeIntercessaoMapper;
import com.mava.ordemCulto.infra.mapper.OportunidadeMapper;
import com.mava.ordemCulto.repositories.AvisosRepository;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.EquipeIntercessaoRepository;
import com.mava.ordemCulto.repositories.OportunidadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CultoService {
    private final CultoRepository cultoRepository;
    private final AvisosRepository avisosRepository;
    private final OportunidadesRepository oportunidadesRepository;
    private final EquipeIntercessaoRepository equipeIntercessaoRepository;
    private final CultoMapper cultoMapper;
    private final OportunidadeMapper oportunidadeMapper;
    private final EquipeIntercessaoMapper equipeIntercessaoMapper;
    private final AvisoMapper avisoMapper;

    // Criar um culto
    public ResponseEntity<CultoEntity> create(CultoRequestDTO cultoDTO) {
        //Salvar culto
        CultoEntity newCulto = cultoRepository.save(cultoMapper.toEntity(cultoDTO));

        // VARREDURA DE OPORTUNIDADES
        List<OportunidadeEntity> oportunidades = cultoDTO.oportunidades()
                .stream()
                .map(oportunidadeMapper::toEntity)
                .toList();
        // O FOR E PERSISTINDO
        for (OportunidadeEntity oportunidade : oportunidades) {
            oportunidade.setCulto(newCulto);
            oportunidadesRepository.save(oportunidade);
        }

        // VARREDURA DE INTERCESSORES
        List<EquipeIntercessaoEntity> intercessores = cultoDTO.equipeIntercessao()
                .stream()
                .map(equipeIntercessaoMapper::toEntity)
                .toList();
        // O FOR E PERSISTINDO
        for (EquipeIntercessaoEntity equipeIntercessao : intercessores) {
            equipeIntercessao.setCulto(newCulto);
            equipeIntercessaoRepository.save(equipeIntercessao);
        }

        // VARREDURA DE AVISOS
        List<Avisos> avisos = cultoDTO.avisos()
                .stream()
                .map(avisoMapper::toEntity)
                .toList();
        // O FOR E PRESISTINDO
        for (Avisos aviso : avisos) {
            aviso.setCulto(newCulto);
            avisosRepository.save(aviso);
        }

        // RESPONSE
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
                .map(cultoMapper::toDTO)
                .collect(Collectors.toList());

        return cultoDTOs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cultoDTOs);
    }

    // Buscar um culto específico
    public ResponseEntity<CultoResponseDTO> getByIdCulto(Long id) {
        return cultoRepository.findById(id)
                .map(culto -> ResponseEntity.ok(cultoMapper.toDTO(culto)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum culto encontrado")
                        .build());
    }

    // Alterar o culto encontrado por ID
    public ResponseEntity<CultoResponseDTO> update(Long id, CultoRequestDTO cultoDTOAtualizado) {
        // Buscando o culto pelo ID
        CultoEntity cultoExistente = cultoRepository.findById(id).orElseThrow(() -> new RuntimeException("Culto não encontrado"));

        // Atualizando os dados do culto
        cultoExistente.setTituloCulto(cultoDTOAtualizado.tituloCulto());
        cultoExistente.setTipoCulto(cultoDTOAtualizado.tipoCulto());
        cultoExistente.setDataCulto(cultoDTOAtualizado.dataCulto());
        cultoExistente.setDirigente(cultoDTOAtualizado.dirigente());
        cultoExistente.setHoraProsperar(cultoDTOAtualizado.horaProsperar());
        cultoExistente.setPreleitor(cultoDTOAtualizado.preleitor());

        // Salvando o culto atualizado
        cultoRepository.save(cultoExistente);
        // Retornando o culto atualizado como DTO
        return ResponseEntity.ok(cultoMapper.toDTO(cultoExistente));
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
                .map(cultoMapper::toDTO)
                .collect(Collectors.toList());

        return cultosDTOFiltrados.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cultosDTOFiltrados);
    }
}