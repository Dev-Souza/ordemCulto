package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.cultos.CultoDTO;
import com.mava.ordemCulto.domain.oportunidades.OportunidadeDTO;
import com.mava.ordemCulto.domain.oportunidades.Oportunidades;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.OportunidadesRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OportunidadeService {
    private final OportunidadesRepository oportunidadesRepository;
    private final CultoRepository cultoRepository;
    private final CultoService cultoService;

    //ADD Oportunidade In Culto
    public ResponseEntity<Culto> addOportunidade(Integer idCulto, OportunidadeDTO newOportunidade) {
        //Buscando culto existente
        Culto culto = cultoRepository.findById(idCulto).orElseThrow(() -> new RuntimeException("Culto não encontrado"));
        //Transformando a minha nova oportunidadeDTO em oportunidade
        Oportunidades novaOportunidade = new Oportunidades();
        novaOportunidade.setNomePessoa(newOportunidade.nomePessoa());
        novaOportunidade.setMomento(newOportunidade.momentoOportunidade());
        novaOportunidade.setCultoId(idCulto);

        //Adicionando essa oportunidade com as demais
        culto.getOportunidades().add(novaOportunidade);
        //Salvando este culto
        cultoRepository.save(culto);

        return ResponseEntity.ok(culto);
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
}
