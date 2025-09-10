package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.domain.oportunidades.OportunidadeEntity;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadeResponseDTO;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadesRequestDTO;
import com.mava.ordemCulto.infra.mapper.CultoMapper;
import com.mava.ordemCulto.infra.mapper.OportunidadeMapper;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.OportunidadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OportunidadesService {
    private final CultoRepository cultoRepository;
    private final OportunidadesRepository oportunidadesRepository;
    private final OportunidadeMapper oportunidadeMapper;
    private final CultoMapper cultoMapper;

    //ADD Oportunidade In Culto
    public ResponseEntity<CultoResponseDTO> addOportunidade(Long idCulto, OportunidadesRequestDTO newOportunidade) {
        //Buscando culto existente
        CultoEntity cultoBuscado = cultoRepository.findById(idCulto).orElseThrow(() -> new RuntimeException("Culto não encontrado"));
        //Transformando a minha newOportunidade em entidade
        OportunidadeEntity novaOportunidade = oportunidadeMapper.toEntity(newOportunidade);

        //Adicionando essa oportunidade com as demais
        cultoBuscado.getOportunidades().add(novaOportunidade);
        //Salvando este culto com a nova oportunidade
        CultoEntity cultoSaved = cultoRepository.save(cultoBuscado);

        return ResponseEntity.ok(cultoMapper.toDTO(cultoSaved));
    }

    //GET ALL Oportunidades por um culto
    public ResponseEntity<List<OportunidadeResponseDTO>> getAllOportunidadesPorUmCulto(Long idCulto) {
        CultoEntity cultoExistente = cultoRepository.getById(idCulto);
        //Setar as oportunidades para fazer o return
        // Converte as oportunidades para DTO
        List<OportunidadeResponseDTO> oportunidades = cultoExistente.getOportunidades().stream()
                .map(oportunidade -> new OportunidadeResponseDTO(
                        oportunidade.getId(),
                        oportunidade.getNomePessoa(),
                        oportunidade.getMomento(),
                        idCulto
                ))
                .collect(Collectors.toList());

        // Retorna a lista de oportunidades
        return ResponseEntity.ok(oportunidades);
    }

    //GET BY ID OPORTUNIDADE
    public ResponseEntity<OportunidadeResponseDTO> getByIdOportunidade(Long idOportunidade) {
        return oportunidadesRepository.findById(idOportunidade)
                .map(oportunidades -> ResponseEntity.ok(oportunidadeMapper.toDTO(oportunidades)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhuma oportunidade encontrada!")
                        .build());
    }

    //UPDATE OPORTUNIDADE
    public ResponseEntity<OportunidadeResponseDTO> updateOportunidade(Long idOportunidade, OportunidadeResponseDTO oportunidadeUpdated) {
        OportunidadeEntity oportunidadeBuscada = oportunidadesRepository.findById(idOportunidade).orElseThrow(() -> new RuntimeException("Oportunidade não encontrada"));
        oportunidadeBuscada.setNomePessoa(oportunidadeUpdated.nomePessoa());
        oportunidadeBuscada.setMomento(oportunidadeUpdated.momentoOportunidade());
        oportunidadesRepository.save(oportunidadeBuscada);
        return ResponseEntity.ok(oportunidadeMapper.toDTO(oportunidadeBuscada));
    }

    //DELETE OPORTUNIDADE
    public ResponseEntity<Void> deleteOportunidade(Long idOportunidade) {
        ResponseEntity<OportunidadeResponseDTO> oportunidadeEncontrada = getByIdOportunidade(idOportunidade);
        oportunidadesRepository.deleteById(idOportunidade);
        return ResponseEntity.noContent().build();
    }
}
