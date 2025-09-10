package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoDTO;
import com.mava.ordemCulto.services.EquipeIntercessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("equipeIntercessor")
@RequiredArgsConstructor
@Validated
public class EquipeIntercessaoController {
    private final EquipeIntercessaoService equipeIntercessaoService;

    //ADD Intercessao In Culto
    @PostMapping("/{idCulto}")
    public ResponseEntity<Culto> addNewIntercessor(@PathVariable("idCulto") Integer idCulto, @RequestBody EquipeIntercessaoDTO newIntercessor) {return equipeIntercessaoService.addIntercesor(idCulto, newIntercessor);}

    //GET ALL Intercessores DE UM CULTO
    @GetMapping("/{idCulto}")
    public ResponseEntity<List<EquipeIntercessaoDTO>> getAllPorUmCulto(@PathVariable("idCulto") Integer idCulto) {return equipeIntercessaoService.getAllIntercessorPorUmCulto(idCulto);}

    //UPDATE
    @PutMapping("/{idIntercessor}")
    public ResponseEntity<EquipeIntercessaoDTO> updateIntercessor(@PathVariable("idIntercessor") Integer idIntercessor, @RequestBody EquipeIntercessaoDTO intercessorUpdated) {return equipeIntercessaoService.updateIntercessor(idIntercessor, intercessorUpdated);}

    //DELETE
    @DeleteMapping("/idIntercessor")
    public ResponseEntity<Void> deleteIntercessor(@PathVariable("idIntercessor") Integer idIntercessor) {return equipeIntercessaoService.deleteIntercessor(idIntercessor);}
}