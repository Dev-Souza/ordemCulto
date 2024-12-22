package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.cultos.CultoDTO;
import com.mava.ordemCulto.domain.oportunidades.OportunidadeDTO;
import com.mava.ordemCulto.domain.oportunidades.Oportunidades;
import com.mava.ordemCulto.services.OportunidadeService;
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
    private final OportunidadeService oportunidadeService;

    //ADD Oportunidade In Culto
    @PostMapping("/{idCulto}")
    public ResponseEntity<Culto> addNewOportunidades(@PathVariable("idCulto") Integer idCulto, @RequestBody OportunidadeDTO newOportunidade) {return oportunidadeService.addOportunidade(idCulto, newOportunidade);}

    //GET ALL Oportunidades DE UM CULTO
    @GetMapping("/{idCulto}")
    public ResponseEntity<List<OportunidadeDTO>> getAllPorUmCulto(@PathVariable("idCulto") Integer idCulto) { return oportunidadeService.getAllOportunidadesPorUmCulto(idCulto);}

    //GET BY ID Oportunidade DE UM CULTO

    //UPDATE

    //DELETE
}
