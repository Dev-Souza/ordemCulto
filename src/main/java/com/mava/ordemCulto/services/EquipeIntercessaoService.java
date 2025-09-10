package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoResponseDTO;
import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessao;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.EquipeIntercessaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EquipeIntercessaoService {
    private final EquipeIntercessaoRepository equipeIntercessaoRepository;
    private final CultoRepository cultoRepository;

    private EquipeIntercessao paraEntidade(EquipeIntercessaoResponseDTO equipeIntercessaoDTO, Integer idCulto) {
        EquipeIntercessao equipeIntercessao = new EquipeIntercessao();
        equipeIntercessao.setNomeObreiro(equipeIntercessaoDTO.nomeObreiro());
        equipeIntercessao.setCargo(equipeIntercessaoDTO.cargoEquipeIntercessao());
        equipeIntercessao.setCultoId(idCulto);
        return equipeIntercessao;
    }

    private EquipeIntercessaoResponseDTO paraDTO(EquipeIntercessao equipeIntercessao) {
        return new EquipeIntercessaoResponseDTO(
                equipeIntercessao.getId(),
                equipeIntercessao.getNomeObreiro(),
                equipeIntercessao.getCargo(),
                equipeIntercessao.getCulto().getId()
        );
    }

    //ADD Intercessor In Culto
    public ResponseEntity<Culto> addIntercesor(Long idCulto, EquipeIntercessaoResponseDTO newIntercessor) {
        //Buscando culto existente
        Culto cultoBuscado = cultoRepository.findById(idCulto).orElseThrow(() -> new RuntimeException("Culto não encontrado"));
        //Tranformando o meu newIntercessor em entidade
        EquipeIntercessao equipeIntercessao = paraEntidade(newIntercessor, idCulto);

        //Adicionando esse intercessor com os demais
        cultoBuscado.getEquipeIntercessao().add(equipeIntercessao);
        //Salvando este culto com o novo aviso
        cultoRepository.save(cultoBuscado);

        return ResponseEntity.ok(cultoBuscado);
    }

    //GET ALL EquipeIntercessão por um culto
    public ResponseEntity<List<EquipeIntercessaoResponseDTO>> getAllIntercessorPorUmCulto(Integer idCulto) {
        Culto cultoExistente = cultoRepository.getById(idCulto);
        //Setar os intercessores para fazer o return
        //Converte os avisos ára DTO
        List<EquipeIntercessaoResponseDTO> intercessores = cultoExistente.getEquipeIntercessao().stream()
                .map(intercessor -> paraDTO(intercessor))
                .collect(Collectors.toList());

        //Retorna a lista de intercessores
        return ResponseEntity.ok(intercessores);
    }

    //GET BY ID INTERCESSOR
    public ResponseEntity<EquipeIntercessaoResponseDTO> getByIdIntercessor(Integer idIntercessor) {
        return equipeIntercessaoRepository.findById(idIntercessor)
                .map(intercessores -> ResponseEntity.ok(paraDTO(intercessores)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum intercessor encontrado!")
                        .build());
    }

    //UPDATE INTERCESSOR
    public ResponseEntity<EquipeIntercessaoResponseDTO> updateIntercessor(Integer idIntercessor, EquipeIntercessaoResponseDTO intercessorUpdated) {
        EquipeIntercessao equipeIntercessao = equipeIntercessaoRepository.findById(idIntercessor).orElseThrow(() -> new RuntimeException("Aviso não encontrado"));
        equipeIntercessao.setNomeObreiro(intercessorUpdated.nomeObreiro());
        equipeIntercessao.setCargo(intercessorUpdated.cargoEquipeIntercessao());
        //Salvando este Intercessor alterado
        equipeIntercessaoRepository.save(equipeIntercessao);
        return ResponseEntity.ok(paraDTO(equipeIntercessao));
    }

    //DELETE INTERCESSOR
    public ResponseEntity<Void> deleteIntercessor(Integer idIntercessor) {
        ResponseEntity<EquipeIntercessaoResponseDTO> equipeIntercessaoEncontrado = getByIdIntercessor(idIntercessor);
        equipeIntercessaoRepository.deleteById(idIntercessor);
        return ResponseEntity.noContent().build();
    }
}