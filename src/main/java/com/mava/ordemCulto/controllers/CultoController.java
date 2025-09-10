package com.mava.ordemCulto.controllers;

import com.mava.ordemCulto.domain.cultos.Culto;
import com.mava.ordemCulto.domain.cultos.dto.CultoDTO;
import com.mava.ordemCulto.domain.cultos.dto.FiltrarCultoPorData;
import com.mava.ordemCulto.services.CultoService;
import com.mava.ordemCulto.services.JasperReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/culto")
@RequiredArgsConstructor
@Validated
public class CultoController {

    private final CultoService cultoService;
    private final JasperReportService jasperReportService;

    // CREATE
    @PostMapping
    public ResponseEntity<Culto> createNewCulto(@Valid @RequestBody CultoDTO cultoDTO) {
        return this.cultoService.create(cultoDTO);
    }

    // GET ALL Pagination
    @GetMapping
    public ResponseEntity<List<CultoDTO>> getAllCultos(@RequestParam int pagina, @RequestParam int itens) {
        return cultoService.getAll(pagina, itens);
    }

    // GET COUNT Cultos
    @GetMapping("/qtd")
    public ResponseEntity<Map<String, Long>> countCultos() {
        long total = cultoService.getCount();
        Map<String, Long> resposta = Map.of("quantidade", total);
        return ResponseEntity.ok(resposta);
    }

    // GET BY ID
    @GetMapping("/{id}") // Use a barra inicial para melhor prática de URL
    public ResponseEntity<CultoDTO> getByIdCulto(@PathVariable("id") Integer id) {
        return cultoService.getByIdCulto(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CultoDTO> updateByIdCulto(
            @PathVariable("id") Integer id,
            @Valid @RequestBody CultoDTO cultoDTOAtualizado) {
        return cultoService.update(id, cultoDTOAtualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdCulto(@PathVariable("id") Integer id) {
        return cultoService.delete(id);
    }

    // FILTRAGEM POR DATA
    @PostMapping("/filtroData")
    public ResponseEntity<List<Culto>> filtrarCultosPorData(@Valid @RequestBody FiltrarCultoPorData filtro) {return cultoService.getCultoByData(filtro.getDataInicial(), filtro.getDataFinal());}

    //BUSCAR CULTOS RECENTES
    @GetMapping("/cultosRecentes")
    public ResponseEntity<List<Culto>> getCultosRecentes() {return cultoService.getAllCultosRecentes();}

    @PostMapping("/gerarPDF")
    public void gerarPDF(@RequestBody Culto culto){
        this.jasperReportService.gerar(culto);
    }

    @GetMapping("/filtroTitulo")
    public ResponseEntity<List<CultoDTO>> filtroTitulo(@RequestParam String titulo){return cultoService.filtroTitulo(titulo);}
}