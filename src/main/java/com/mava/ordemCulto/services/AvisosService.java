package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.avisos.Avisos;
import com.mava.ordemCulto.domain.avisos.AvisosDTO;
import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.repositories.AvisosRepository;
import com.mava.ordemCulto.repositories.CultoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AvisosService {
    private final AvisosRepository avisosRepository;
    private final CultoRepository cultoRepository;

    private Avisos paraEntidade(AvisosDTO avisosDTO, Integer idCulto){
        Avisos avisos = new Avisos();
        avisos.setNomeAviso(avisosDTO.nomeAviso());
        avisos.setReferente(avisosDTO.referente());
        avisos.setHorarioEvento(avisosDTO.horarioEvento());
        avisos.setDiasEvento(avisosDTO.diasEvento());
        avisos.setCultoId(idCulto);
        return avisos;
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
}
