package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadeResponseDTO;
import com.mava.ordemCulto.domain.oportunidades.Oportunidades;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.OportunidadesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OportunidadesService {
    private final CultoRepository cultoRepository;
    private final OportunidadesRepository oportunidadesRepository;

    // Converter Entity para DTO
    private OportunidadeResponseDTO paraDTO(Oportunidades oportunidades) {
        return new OportunidadeResponseDTO(
                oportunidades.getId(),
                oportunidades.getNomePessoa(),
                oportunidades.getMomento(),
                oportunidades.getCultoId()
        );
    }

    private Oportunidades paraEntidade(OportunidadeResponseDTO oportunidadeDTO, Long idCulto) {
        Oportunidades oportunidades = new Oportunidades();
        oportunidades.setNomePessoa(oportunidadeDTO.nomePessoa());
        oportunidades.setMomento(oportunidadeDTO.momentoOportunidade());
        oportunidades.setCultoId(idCulto);
        return oportunidades;
    }

    //ADD Oportunidade In Culto
    public ResponseEntity<Culto> addOportunidade(Long idCulto, OportunidadeResponseDTO newOportunidade) {
        //Buscando culto existente
        Culto cultoBuscado = cultoRepository.findById(idCulto).orElseThrow(() -> new RuntimeException("Culto não encontrado"));
        //Transformando a minha newOportunidade em entidade
        Oportunidades novaOportunidade = paraEntidade(newOportunidade, idCulto);

        //Adicionando essa oportunidade com as demais
        cultoBuscado.getOportunidades().add(novaOportunidade);
        //Salvando este culto com a nova oportunidade
        cultoRepository.save(cultoBuscado);

        return ResponseEntity.ok(cultoBuscado);
    }

    //GET ALL Oportunidades por um culto
    public ResponseEntity<List<OportunidadeResponseDTO>> getAllOportunidadesPorUmCulto(Long idCulto) {
        Culto cultoExistente = cultoRepository.getById(idCulto);
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
                .map(oportunidades -> ResponseEntity.ok(paraDTO(oportunidades)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhuma oportunidade encontrada!")
                        .build());
    }

    //UPDATE OPORTUNIDADE
    public ResponseEntity<OportunidadeResponseDTO> updateOportunidade(Long idOportunidade, OportunidadeResponseDTO oportunidadeUpdated) {
        Oportunidades oportunidadeBuscada = oportunidadesRepository.findById(idOportunidade).orElseThrow(() -> new RuntimeException("Oportunidade não encontrada"));
        oportunidadeBuscada.setNomePessoa(oportunidadeUpdated.nomePessoa());
        oportunidadeBuscada.setMomento(oportunidadeUpdated.momentoOportunidade());
        oportunidadeBuscada.setCultoId(oportunidadeUpdated.cultoId());
        oportunidadesRepository.save(oportunidadeBuscada);
        return ResponseEntity.ok(paraDTO(oportunidadeBuscada));
    }

    //DELETE OPORTUNIDADE
    public ResponseEntity<Void> deleteOportunidade(Long idOportunidade) {
        ResponseEntity<OportunidadeResponseDTO> oportunidadeEncontrada = getByIdOportunidade(idOportunidade);
        oportunidadesRepository.deleteById(idOportunidade);
        return ResponseEntity.noContent().build();
    }
}
