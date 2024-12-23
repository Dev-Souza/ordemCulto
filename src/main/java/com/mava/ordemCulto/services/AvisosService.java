package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.avisos.Avisos;
import com.mava.ordemCulto.domain.avisos.AvisosDTO;
import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.repositories.AvisosRepository;
import com.mava.ordemCulto.repositories.CultoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvisosService {
    private final AvisosRepository avisosRepository;
    private final CultoRepository cultoRepository;

    private Avisos paraEntidade(AvisosDTO avisosDTO, Integer idCulto) {
        Avisos avisos = new Avisos();
        avisos.setNomeAviso(avisosDTO.nomeAviso());
        avisos.setReferente(avisosDTO.referente());
        avisos.setHorarioEvento(avisosDTO.horarioEvento());
        avisos.setDiasEvento(avisosDTO.diasEvento());
        avisos.setCultoId(idCulto);
        return avisos;
    }

    private AvisosDTO paraDTO(Avisos avisos) {
        return new AvisosDTO(
                avisos.getId(),
                avisos.getNomeAviso(),
                avisos.getReferente(),
                avisos.getHorarioEvento(),
                avisos.getDiasEvento(),
                avisos.getCultoId()
        );
    }

    //ADD Aviso in Culto
    public ResponseEntity<Culto> addAviso(Integer idCulto, AvisosDTO newAviso) {
        //Buscando culto existente
        Culto cultoBuscado = cultoRepository.findById(idCulto).orElseThrow(() -> new RuntimeException("Culto não encontrado"));
        //Transformando o meu newAviso em entidade
        Avisos novoAviso = paraEntidade(newAviso, idCulto);

        //Adicionando esse aviso com os demais
        cultoBuscado.getAvisos().add(novoAviso);
        //Salvando este culto com o novo aviso
        cultoRepository.save(cultoBuscado);

        return ResponseEntity.ok(cultoBuscado);
    }

    //GET ALL Avisos por um culto
    public ResponseEntity<List<AvisosDTO>> getAllAvisosPorUmCulto(Integer idCulto) {
        Culto cultoExistente = cultoRepository.getById(idCulto);
        //Setar os avisos para fazer o return
        //Converte os avisos para DTO
        List<AvisosDTO> avisos = cultoExistente.getAvisos().stream()
                .map(aviso -> new AvisosDTO(
                        aviso.getId(),
                        aviso.getNomeAviso(),
                        aviso.getReferente(),
                        aviso.getHorarioEvento(),
                        aviso.getDiasEvento(),
                        idCulto
                ))
                .collect(Collectors.toList());

        //Retorna a lista de Avisos
        return ResponseEntity.ok(avisos);
    }

    //GET BY ID AVISO
    public ResponseEntity<AvisosDTO> getByIdAviso(Integer idAviso) {
        return avisosRepository.findById(idAviso)
                .map(avisos -> ResponseEntity.ok(paraDTO(avisos)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum aviso encontrado!")
                        .build());
    }

    //UPDATE AVISO
    public ResponseEntity<AvisosDTO> updateAviso(Integer idAviso, AvisosDTO avisoUpdated) {
        Avisos avisoBuscado = avisosRepository.findById(idAviso).orElseThrow(() -> new RuntimeException("Aviso não encontrado"));
        avisoBuscado.setNomeAviso(avisoUpdated.nomeAviso());
        avisoBuscado.setReferente(avisoUpdated.referente());
        avisoBuscado.setHorarioEvento(avisoUpdated.horarioEvento());
        avisoBuscado.setDiasEvento(avisoUpdated.diasEvento());
        //Salvando este Aviso alterado
        avisosRepository.save(avisoBuscado);
        return ResponseEntity.ok(paraDTO(avisoBuscado));
    }

    //DELETE AVISO
    public ResponseEntity<Void> deleteAviso (Integer idAviso){
        ResponseEntity<AvisosDTO> avisoEncontrado = getByIdAviso(idAviso);
        avisosRepository.deleteById(idAviso);
        return ResponseEntity.noContent().build();
    }
}
