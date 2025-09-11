package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.avisos.AvisosEntity;
import com.mava.ordemCulto.domain.avisos.dto.AvisosRequestDTO;
import com.mava.ordemCulto.domain.avisos.dto.AvisosResponseDTO;
import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.exceptions.IdInvalidoException;
import com.mava.ordemCulto.infra.mapper.AvisoMapper;
import com.mava.ordemCulto.infra.mapper.CultoMapper;
import com.mava.ordemCulto.repositories.AvisosRepository;
import com.mava.ordemCulto.repositories.CultoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvisosService {
    private final AvisosRepository avisosRepository;
    private final CultoRepository cultoRepository;
    private final AvisoMapper avisoMapper;
    private final CultoMapper cultoMapper;

    //ADD Aviso in Culto
    public ResponseEntity<CultoResponseDTO> addAviso(Long idCulto, AvisosRequestDTO newAviso) {
        //Buscando culto existente
        CultoEntity cultoBuscado = cultoRepository.findById(idCulto).orElseThrow(() -> new RuntimeException("Culto não encontrado"));
        //Transformando o meu newAviso em entidade
        AvisosEntity novoAviso = avisoMapper.toEntity(newAviso);

        //Adicionando esse aviso com os demais
        cultoBuscado.getAvisos().add(novoAviso);
        //Salvando este culto com o novo aviso
        cultoRepository.save(cultoBuscado);

        return ResponseEntity.ok(cultoMapper.toDTO(cultoBuscado));
    }

    //GET ALL Avisos por um culto
    public ResponseEntity<List<AvisosResponseDTO>> getAllAvisosPorUmCulto(Long idCulto) {
        CultoEntity cultoExistente = cultoRepository.getById(idCulto);
        //Setar os avisos para fazer o return
        //Converte os avisos para DTO
        List<AvisosResponseDTO> avisos = cultoExistente.getAvisos().stream()
                .map(avisoMapper::toDTO)
                .collect(Collectors.toList());

        //Retorna a lista de Avisos
        return ResponseEntity.ok(avisos);
    }

    //GET BY ID AVISO
    public ResponseEntity<AvisosResponseDTO> getByIdAviso(Long idAviso) {
        return avisosRepository.findById(idAviso)
                .map(avisos -> ResponseEntity.ok(avisoMapper.toDTO(avisos)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum aviso encontrado!")
                        .build());
    }

    //UPDATE AVISO
    public ResponseEntity<AvisosResponseDTO> updateAviso(Long idAviso, AvisosRequestDTO avisoUpdated) {
        AvisosEntity avisoBuscado = avisosRepository.findById(idAviso).orElseThrow(() -> new RuntimeException("Aviso não encontrado"));
        avisoBuscado.setNomeAviso(avisoUpdated.nomeAviso());
        avisoBuscado.setReferente(avisoUpdated.referente());
        avisoBuscado.setHorarioEvento(avisoUpdated.horarioEvento());
        avisoBuscado.setDiasEvento(avisoUpdated.diasEvento());
        //Salvando este Aviso alterado
        avisosRepository.save(avisoBuscado);
        return ResponseEntity.ok(avisoMapper.toDTO(avisoBuscado));
    }

    //DELETE AVISO
    public ResponseEntity<Void> deleteAviso (Long idAviso){
        if(avisosRepository.existsById(idAviso)){
            avisosRepository.deleteById(idAviso);
            return ResponseEntity.noContent().build();
        }
        throw new IdInvalidoException("ID não encontrado!");
    }
}
