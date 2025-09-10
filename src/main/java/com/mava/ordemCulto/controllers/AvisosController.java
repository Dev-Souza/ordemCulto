package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.avisos.dto.AvisosResponseDTO;
import com.mava.ordemCulto.domain.cultos.CultoEntity;
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
    public ResponseEntity<CultoEntity> addNewAviso(@PathVariable("idCulto") Long idCulto, @RequestBody AvisosResponseDTO newAviso) {return avisosService.addAviso(idCulto, newAviso);}

    //GET ALL Avisos DE UM CULTO
    @GetMapping("/{idCulto}")
    public ResponseEntity<List<AvisosResponseDTO>> getAllPorUmCulto(@PathVariable("idCulto") Long idCulto) {return avisosService.getAllAvisosPorUmCulto(idCulto);}

    //UPDATE
    @PutMapping("/{idAviso}")
    public ResponseEntity<AvisosResponseDTO> updateAviso(@PathVariable("idAviso") Long idAviso, @RequestBody AvisosResponseDTO avisoUpdated) {return avisosService.updateAviso(idAviso, avisoUpdated);}

    //DELETE
    @DeleteMapping("/idAviso")
    public ResponseEntity<Void> deleteAviso(@PathVariable("idAviso") Long idAviso){return avisosService.deleteAviso(idAviso);}
}