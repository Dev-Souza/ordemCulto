package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadeResponseDTO;
import com.mava.ordemCulto.domain.oportunidades.dto.OportunidadesRequestDTO;
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
    public ResponseEntity<CultoResponseDTO> addNewOportunidade(@PathVariable("idCulto") Long idCulto, @RequestBody OportunidadesRequestDTO newOportunidade) {return oportunidadeService.addOportunidade(idCulto, newOportunidade);}

    //GET ALL Oportunidades DE UM CULTO
    @GetMapping("/{idCulto}")
    public ResponseEntity<List<OportunidadeResponseDTO>> getAllPorUmCulto(@PathVariable("idCulto") Long idCulto) { return oportunidadeService.getAllOportunidadesPorUmCulto(idCulto);}

    //UPDATE
    @PutMapping("/{idOportunidade}")
    public ResponseEntity<OportunidadeResponseDTO> updateOportunidade(@PathVariable("idOportunidade") Long idOportunidade, @RequestBody OportunidadeResponseDTO oportunidadeUpdated) {return oportunidadeService.updateOportunidade(idOportunidade, oportunidadeUpdated);}

    //DELETE
    @DeleteMapping("/{idOportunidade}")
    public ResponseEntity<Void> deleteOportunidade(@PathVariable("idOportunidade") Long idOportunidade) {return oportunidadeService.deleteOportunidade(idOportunidade);}
}