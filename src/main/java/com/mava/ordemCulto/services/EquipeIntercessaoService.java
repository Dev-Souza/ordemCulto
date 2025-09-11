package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessaoEntity;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoRequestDTO;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoResponseDTO;
import com.mava.ordemCulto.exceptions.IdInvalidoException;
import com.mava.ordemCulto.infra.mapper.CultoMapper;
import com.mava.ordemCulto.infra.mapper.EquipeIntercessaoMapper;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.EquipeIntercessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipeIntercessaoService {
    private final EquipeIntercessaoRepository equipeIntercessaoRepository;
    private final CultoRepository cultoRepository;
    private final EquipeIntercessaoMapper equipeIntercessaoMapper;
    private final CultoMapper cultoMapper;

    //ADD Intercessor In Culto
    public ResponseEntity<CultoResponseDTO> addIntercesor(Long idCulto, EquipeIntercessaoRequestDTO newIntercessor) {
        //Buscando culto existente
        CultoEntity cultoBuscado = cultoRepository.findById(idCulto).orElseThrow(() -> new RuntimeException("Culto não encontrado"));
        //Tranformando o meu newIntercessor em entidade
        EquipeIntercessaoEntity equipeIntercessao = equipeIntercessaoMapper.toEntity(newIntercessor);

        //Adicionando esse intercessor com os demais
        cultoBuscado.getEquipeIntercessao().add(equipeIntercessao);
        //Salvando este culto com o novo aviso
        CultoEntity cultoSaved = cultoRepository.save(cultoBuscado);

        return ResponseEntity.ok(cultoMapper.toDTO(cultoSaved));
    }

    //GET ALL EquipeIntercessão por um culto
    public ResponseEntity<List<EquipeIntercessaoResponseDTO>> getAllIntercessorPorUmCulto(Long idCulto) {
        CultoEntity cultoExistente = cultoRepository.getById(idCulto);
        //Setar os intercessores para fazer o return
        //Converte os avisos ára DTO
        List<EquipeIntercessaoResponseDTO> intercessores = cultoExistente.getEquipeIntercessao().stream()
                .map(equipeIntercessaoMapper::toDTO)
                .collect(Collectors.toList());

        //Retorna a lista de intercessores
        return ResponseEntity.ok(intercessores);
    }

    //GET BY ID INTERCESSOR
    public ResponseEntity<EquipeIntercessaoResponseDTO> getByIdIntercessor(Long idIntercessor) {
        return equipeIntercessaoRepository.findById(idIntercessor)
                .map(intercessores -> ResponseEntity.ok(equipeIntercessaoMapper.toDTO(intercessores)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum intercessor encontrado!")
                        .build());
    }

    //UPDATE INTERCESSOR
    public ResponseEntity<EquipeIntercessaoResponseDTO> updateIntercessor(Long idIntercessor, EquipeIntercessaoResponseDTO intercessorUpdated) {
        EquipeIntercessaoEntity equipeIntercessao = equipeIntercessaoRepository.findById(idIntercessor).orElseThrow(() -> new RuntimeException("Aviso não encontrado"));
        equipeIntercessao.setNomeObreiro(intercessorUpdated.nomeObreiro());
        equipeIntercessao.setCargoEquipeIntercessao(intercessorUpdated.cargoEquipeIntercessao());
        //Salvando este Intercessor alterado
        equipeIntercessaoRepository.save(equipeIntercessao);
        return ResponseEntity.ok(equipeIntercessaoMapper.toDTO(equipeIntercessao));
    }

    //DELETE INTERCESSOR
    public ResponseEntity<Void> deleteIntercessor(Long idIntercessor) {
        if (equipeIntercessaoRepository.existsById(idIntercessor)) {
            equipeIntercessaoRepository.deleteById(idIntercessor);
            return ResponseEntity.noContent().build();
        }
        throw new IdInvalidoException("ID não encontrado!");
    }
}