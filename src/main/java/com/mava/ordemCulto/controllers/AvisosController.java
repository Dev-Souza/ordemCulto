package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.avisos.dto.AvisosDTO;
import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.services.AvisosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aviso")
@RequiredArgsConstructor
@Validated
public class AvisosController {
    private final AvisosService avisosService;

    //ADD Aviso In Culto
    @PostMapping("/{idCulto}")
    public ResponseEntity<Culto> addNewAviso(@PathVariable("idCulto") Integer idCulto, @RequestBody AvisosDTO newAviso) {return avisosService.addAviso(idCulto, newAviso);}

    //GET ALL Avisos DE UM CULTO
    @GetMapping("/{idCulto}")
    public ResponseEntity<List<AvisosDTO>> getAllPorUmCulto(@PathVariable("idCulto") Integer idCulto) {return avisosService.getAllAvisosPorUmCulto(idCulto);}

    //UPDATE
    @PutMapping("/{idAviso}")
    public ResponseEntity<AvisosDTO> updateAviso(@PathVariable("idAviso") Integer idAviso, @RequestBody AvisosDTO avisoUpdated) {return avisosService.updateAviso(idAviso, avisoUpdated);}

    //DELETE
    @DeleteMapping("/idAviso")
    public ResponseEntity<Void> deleteAviso(@PathVariable("idAviso") Integer idAviso){return avisosService.deleteAviso(idAviso);}
}