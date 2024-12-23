package com.mava.ordemCulto.services;

import com.mava.ordemCulto.repositories.AvisosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AvisosService {
    private final AvisosRepository avisosRepository;
}
