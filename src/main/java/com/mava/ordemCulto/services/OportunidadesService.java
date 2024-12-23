package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.oportunidades.OportunidadeDTO;
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
    private OportunidadeDTO paraDTO(Oportunidades oportunidades) {
        return new OportunidadeDTO(
                oportunidades.getId(),
                oportunidades.getNomePessoa(),
                oportunidades.getMomento(),
                oportunidades.getCultoId()
        );
    }

    private Oportunidades paraEntidade(OportunidadeDTO oportunidadeDTO, Integer idCulto) {
        Oportunidades oportunidades = new Oportunidades();
        oportunidades.setNomePessoa(oportunidadeDTO.nomePessoa());
        oportunidades.setMomento(oportunidadeDTO.momentoOportunidade());
        oportunidades.setCultoId(idCulto);
        return oportunidades;
    }

    //ADD Oportunidade In Culto
    public ResponseEntity<Culto> addOportunidade(Integer idCulto, OportunidadeDTO newOportunidade) {
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
    public ResponseEntity<List<OportunidadeDTO>> getAllOportunidadesPorUmCulto(Integer idCulto) {
        Culto cultoExistente = cultoRepository.getById(idCulto);
        //Setar as oportunidades para fazer o return
        // Converte as oportunidades para DTO
        List<OportunidadeDTO> oportunidades = cultoExistente.getOportunidades().stream()
                .map(oportunidade -> new OportunidadeDTO(
                        oportunidade.getId(),
                        oportunidade.getNomePessoa(),
                        oportunidade.getMomento(),
                        idCulto
                ))
                .collect(Collectors.toList());

        // Retorna a lista de oportunidades
        return ResponseEntity.ok(oportunidades);
    }

    public ResponseEntity<OportunidadeDTO> getByIdOportunidade(Integer idOportunidade) {
        return oportunidadesRepository.findById(idOportunidade)
                .map(oportunidades -> ResponseEntity.ok(paraDTO(oportunidades)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhuma oportunidade encontrada")
                        .build());
    }

    //UPDATE OPORTUNIDADE
    public ResponseEntity<OportunidadeDTO> updateOportunidade(Integer idOportunidade, OportunidadeDTO oportunidadeUpdated) {
        Oportunidades oportunidadeBuscada = oportunidadesRepository.findById(idOportunidade).orElseThrow(() -> new RuntimeException("Oportunidade não encontrada"));
        oportunidadeBuscada.setNomePessoa(oportunidadeUpdated.nomePessoa());
        oportunidadeBuscada.setMomento(oportunidadeUpdated.momentoOportunidade());
        oportunidadeBuscada.setCultoId(oportunidadeUpdated.cultoId());
        oportunidadesRepository.save(oportunidadeBuscada);
        return ResponseEntity.ok(oportunidadeUpdated);
    }

    //DELETE OPORTUNIDADE
    public ResponseEntity<Void> deleteOportunidade(Integer idOportunidade) {
        ResponseEntity<OportunidadeDTO> oportunidadeEncontrada = getByIdOportunidade(idOportunidade);
        oportunidadesRepository.deleteById(idOportunidade);
        return ResponseEntity.noContent().build();
    }
}
