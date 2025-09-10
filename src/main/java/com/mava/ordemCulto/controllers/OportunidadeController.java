package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadeDTO;
import com.mava.ordemCulto.services.OportunidadesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oportunidade")
@RequiredArgsConstructor
@Validated
public class OportunidadeController {
    private final OportunidadesService oportunidadeService;

    //ADD Oportunidade In Culto
    @PostMapping("/{idCulto}")
    public ResponseEntity<Culto> addNewOportunidade(@PathVariable("idCulto") Integer idCulto, @RequestBody OportunidadeDTO newOportunidade) {return oportunidadeService.addOportunidade(idCulto, newOportunidade);}

    //GET ALL Oportunidades DE UM CULTO
    @GetMapping("/{idCulto}")
    public ResponseEntity<List<OportunidadeDTO>> getAllPorUmCulto(@PathVariable("idCulto") Integer idCulto) { return oportunidadeService.getAllOportunidadesPorUmCulto(idCulto);}

    //UPDATE
    @PutMapping("/{idOportunidade}")
    public ResponseEntity<OportunidadeDTO> updateOportunidade(@PathVariable("idOportunidade") Integer idOportunidade, @RequestBody OportunidadeDTO oportunidadeUpdated) {return oportunidadeService.updateOportunidade(idOportunidade, oportunidadeUpdated);}

    //DELETE
    @DeleteMapping("/{idOportunidade}")
    public ResponseEntity<Void> deleteOportunidade(@PathVariable("idOportunidade") Integer idOportunidade) {return oportunidadeService.deleteOportunidade(idOportunidade);}
}