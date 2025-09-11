package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoRequestDTO;
import com.mava.ordemCulto.domain.equipe_intercessao.dto.EquipeIntercessaoResponseDTO;
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
    public ResponseEntity<CultoResponseDTO> addNewIntercessor(@PathVariable("idCulto") Long idCulto, @RequestBody EquipeIntercessaoRequestDTO newIntercessor) {return equipeIntercessaoService.addIntercesor(idCulto, newIntercessor);}

    //GET ALL Intercessores DE UM CULTO
    @GetMapping("/{idCulto}")
    public ResponseEntity<List<EquipeIntercessaoResponseDTO>> getAllPorUmCulto(@PathVariable("idCulto") Long idCulto) {return equipeIntercessaoService.getAllIntercessorPorUmCulto(idCulto);}

    //UPDATE
    @PutMapping("/{idIntercessor}")
    public ResponseEntity<EquipeIntercessaoResponseDTO> updateIntercessor(@PathVariable("idIntercessor") Long idIntercessor, @RequestBody EquipeIntercessaoResponseDTO intercessorUpdated) {return equipeIntercessaoService.updateIntercessor(idIntercessor, intercessorUpdated);}

    //DELETE
    @DeleteMapping("/{idIntercessor}")
    public ResponseEntity<Void> deleteIntercessor(@PathVariable("idIntercessor") Long idIntercessor) {return equipeIntercessaoService.deleteIntercessor(idIntercessor);}
}