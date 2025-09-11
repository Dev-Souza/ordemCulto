package com.mava.ordemCulto.services;

import com.mava.ordemCulto.domain.avisos.Avisos;
import com.mava.ordemCulto.domain.cultos.CultoEntity;
import com.mava.ordemCulto.domain.cultos.dto.CultoRequestDTO;
import com.mava.ordemCulto.domain.cultos.dto.CultoResponseDTO;
import com.mava.ordemCulto.domain.equipe_intercessao.EquipeIntercessaoEntity;
import com.mava.ordemCulto.domain.oportunidades.OportunidadeEntity;
import com.mava.ordemCulto.exceptions.IdInvalidoException;
import com.mava.ordemCulto.infra.mapper.AvisoMapper;
import com.mava.ordemCulto.infra.mapper.CultoMapper;
import com.mava.ordemCulto.infra.mapper.EquipeIntercessaoMapper;
import com.mava.ordemCulto.infra.mapper.OportunidadeMapper;
import com.mava.ordemCulto.repositories.AvisosRepository;
import com.mava.ordemCulto.repositories.CultoRepository;
import com.mava.ordemCulto.repositories.EquipeIntercessaoRepository;
import com.mava.ordemCulto.repositories.OportunidadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CultoService {
    private final CultoRepository cultoRepository;
    private final AvisosRepository avisosRepository;
    private final OportunidadesRepository oportunidadesRepository;
    private final EquipeIntercessaoRepository equipeIntercessaoRepository;
    private final CultoMapper cultoMapper;
    private final OportunidadeMapper oportunidadeMapper;
    private final EquipeIntercessaoMapper equipeIntercessaoMapper;
    private final AvisoMapper avisoMapper;

    // Criar um culto
    public ResponseEntity<CultoEntity> create(CultoRequestDTO cultoDTO) {
        //Salvar culto
        CultoEntity newCulto = cultoRepository.save(cultoMapper.toEntity(cultoDTO));

        // VARREDURA DE OPORTUNIDADES
        List<OportunidadeEntity> oportunidades = cultoDTO.oportunidades()
                .stream()
                .map(oportunidadeMapper::toEntity)
                .toList();
        // O FOR E PERSISTINDO
        for (OportunidadeEntity oportunidade : oportunidades) {
            oportunidade.setCulto(newCulto);
            oportunidadesRepository.save(oportunidade);
        }

        // VARREDURA DE INTERCESSORES
        List<EquipeIntercessaoEntity> intercessores = cultoDTO.equipeIntercessao()
                .stream()
                .map(equipeIntercessaoMapper::toEntity)
                .toList();
        // O FOR E PERSISTINDO
        for (EquipeIntercessaoEntity equipeIntercessao : intercessores) {
            equipeIntercessao.setCulto(newCulto);
            equipeIntercessaoRepository.save(equipeIntercessao);
        }

        // VARREDURA DE AVISOS
        List<Avisos> avisos = cultoDTO.avisos()
                .stream()
                .map(avisoMapper::toEntity)
                .toList();
        // O FOR E PRESISTINDO
        for (Avisos aviso : avisos) {
            aviso.setCulto(newCulto);
            avisosRepository.save(aviso);
        }

        // RESPONSE
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("created", "Culto criado com sucesso!")
                .body(newCulto);
    }

    // Buscar todos os cultos
    public ResponseEntity<List<CultoResponseDTO>> getAll(int pagina, int itens) {
        //Minha paginação
        Page<CultoEntity> cultos = cultoRepository.findAll(PageRequest.of(pagina, itens));
        List<CultoResponseDTO> cultoDTOs = cultos.stream()
                .map(cultoMapper::toDTO)
                .collect(Collectors.toList());

        return cultoDTOs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cultoDTOs);
    }

    // Buscar um culto específico
    public ResponseEntity<CultoResponseDTO> getByIdCulto(Long id) {
        return cultoRepository.findById(id)
                .map(culto -> ResponseEntity.ok(cultoMapper.toDTO(culto)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("error", "Nenhum culto encontrado")
                        .build());
    }

    // Alterar o culto encontrado por ID
    public ResponseEntity<CultoResponseDTO> update(Long id, CultoRequestDTO cultoDTOAtualizado) {
        // Buscando o culto pelo ID
        CultoEntity cultoExistente = cultoRepository.findById(id).orElseThrow(() -> new RuntimeException("Culto não encontrado"));

        // Atualizando os dados do culto
        cultoExistente.setTituloCulto(cultoDTOAtualizado.tituloCulto());
        cultoExistente.setTipoCulto(cultoDTOAtualizado.tipoCulto());
        cultoExistente.setDataCulto(cultoDTOAtualizado.dataCulto());
        cultoExistente.setDirigente(cultoDTOAtualizado.dirigente());
        cultoExistente.setHoraProsperar(cultoDTOAtualizado.horaProsperar());
        cultoExistente.setPreleitor(cultoDTOAtualizado.preleitor());

        // Varrendo a oportunidade e salvando as alterações
        // PEGANDO AS OPORTUNIDADES QUE VEM DA REQ E TRANSFORMANDO EM ENTIDADE
        List<OportunidadeEntity> oportunidadesAtualizadas = cultoDTOAtualizado.oportunidades()
                .stream()
                .map(oportunidadeMapper::toEntity)
                .toList();
        // PEGANDO AS QUE JÁ EXISTEM NO CULTO
        List<OportunidadeEntity> oportunidadesExistentes = cultoExistente.getOportunidades();

        // VERIFICANDO SE A QUANTIDADE É A MESMA
        if (oportunidadesAtualizadas.size() != oportunidadesExistentes.size()) throw new RuntimeException("Quantidade de oportunidades não corresponde!");

        // FAZENDO A VARREDURA PARA PEGAR ELEMENTO POR ELEMENTO
        for (int i = 0; i < oportunidadesExistentes.size(); i++) {
            // PEGANDO OPORTUNIDADE EXISTENTE PELA POSIÇÃO
            OportunidadeEntity oportunidadeExistente = oportunidadesExistentes.get(i);
            // PEGANDO A OPORTUNIDADE ATUALIZADA PELA POSIÇÃO
            OportunidadeEntity oportunidadeAtualizada = oportunidadesAtualizadas.get(i);

            // VERIFICANDO SE ALGUMA COISA MUDOU DE FATO PARA FAZER A PERSISTENCIA
            if (!Objects.equals(oportunidadeExistente.getNomePessoa(), oportunidadeAtualizada.getNomePessoa()) ||
                    !Objects.equals(oportunidadeExistente.getMomentoOportunidade(), oportunidadeAtualizada.getMomentoOportunidade())) {
                // SETANDO OS DADOS
                oportunidadeExistente.setNomePessoa(oportunidadeAtualizada.getNomePessoa());
                oportunidadeExistente.setMomentoOportunidade(oportunidadeAtualizada.getMomentoOportunidade());
                oportunidadesRepository.save(oportunidadeExistente);
            }
        }

        // VARRENDO A EQUIPE DE INTERCESSÃO E SALVANDO AS ALTERAÇÕES
        // PEGANDO AS EQUIPES QUE VEM DA REQ E TRANSFORMANDO EM ENTIDADE
        List<EquipeIntercessaoEntity> intecessoresAtualizado = cultoDTOAtualizado.equipeIntercessao()
                .stream()
                .map(equipeIntercessaoMapper::toEntity)
                .toList();

        // PEGANDO AS QUE JÁ EXISTEM NO CULTO
        List<EquipeIntercessaoEntity> intercessoresExistentes = cultoExistente.getEquipeIntercessao();

        // VERIFICANDO SE A QUANTIDADE É A MESMA
        if (intecessoresAtualizado.size() != intercessoresExistentes.size()) throw new RuntimeException("Quantidade de intecessores não corresponde!");

        // FAZENDO A VARREDURA PARA PEGAR ELEMENTO POR ELEMENTO
        for (int i = 0; i < intercessoresExistentes.size(); i++) {
            // PEGANDO INTERCESSOR EXISTENTE PELA POSIÇÃO
            EquipeIntercessaoEntity intercessorExistente = intercessoresExistentes.get(i);
            // PEGANDO INTERCESSOR ATUALIZADO PELA POSIÇÃO
            EquipeIntercessaoEntity intercessorAtualizado = intecessoresAtualizado.get(i);

            // VERIFICANDO SE ALGUMA COISA MUDOU DE FATO PARA FAZER A PERSISTÊNCIA
            if (!Objects.equals(intercessorExistente.getNomeObreiro(), intercessorAtualizado.getNomeObreiro()) ||
                    !Objects.equals(intercessorExistente.getCargoEquipeIntercessao(), intercessorAtualizado.getCargoEquipeIntercessao())) {
                // SETANDO OS DADOS
                intercessorExistente.setNomeObreiro(intercessorExistente.getNomeObreiro());
                intercessorExistente.setCargoEquipeIntercessao(intercessorAtualizado.getCargoEquipeIntercessao());
                equipeIntercessaoRepository.save(intercessorExistente);
            }
        }

        // VARRENDO OS AVISOS E SALVANDO AS ALTERAÇÕES
        // PEGANDO OS AVISOS QUE VEM DA REQ E TRANSFORMANDO EM ENTIDADE
        List<Avisos> avisosAtualizado = cultoDTOAtualizado.avisos()
                .stream()
                .map(avisoMapper::toEntity)
                .toList();

        // PEGANDO AS QUE JÁ EXISTEM NO CULTO
        List<Avisos> avisosExistentes = cultoExistente.getAvisos();

        // VERIFICANDO SE A QUANTIDADE É A MESMA
        if (avisosAtualizado.size() != avisosExistentes.size()) throw new RuntimeException("Quantidade de avisos não corresponde!");

        // FAZENDO A VARREDURA PARA PEGAR ELEMENTO POR ELEMENTO
        for (int i = 0; i < avisosExistentes.size(); i++) {
            // PEGANDO AVISO EXISTENTE PELA POSIÇÃO
            Avisos avisoExistente = avisosExistentes.get(i);
            // PEGANDO AVISO ATUALIZADO PELA POSIÇÃO
            Avisos avisoAtualizado = avisosAtualizado.get(i);

            // VERIFICANDO SE ALGUMA COISA MUDOU DE FATO PARA FAZER A PERSISTÊNCIA
            if (!Objects.equals(avisoExistente.getNomeAviso(), avisoAtualizado.getNomeAviso()) ||
                    !Objects.equals(avisoExistente.getReferente(), avisoAtualizado.getReferente()) ||
                        !Objects.equals(avisoExistente.getDiasEvento(), avisoAtualizado.getDiasEvento()) ||
                            !Objects.equals(avisoExistente.getHorarioEvento(), avisoAtualizado.getHorarioEvento())) {
                avisoExistente.setNomeAviso(avisoExistente.getNomeAviso());
                avisoExistente.setReferente(avisoExistente.getReferente());
                avisoExistente.setHorarioEvento(avisoExistente.getHorarioEvento());
                avisoExistente.setDiasEvento(avisoExistente.getDiasEvento());
                avisosRepository.save(avisoExistente);
            }
        }

        // PERSISTINDO O CULTO
        cultoRepository.save(cultoExistente);
        // RETORNANDO CULTO ATUALIZADO DTO
        return ResponseEntity.ok(cultoMapper.toDTO(cultoExistente));
    }

    // Deletar o culto buscado por ID
    public ResponseEntity<Void> delete(Long id) {
        if (cultoRepository.existsById(id)) {
            cultoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new IdInvalidoException("ID não encontrado!");
    }

    // Filtragem de data de culto
    public ResponseEntity<List<CultoEntity>> getCultoByData(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final.");
        }
        List<CultoEntity> listaCultos = cultoRepository.findByDataCultoBetween(dataInicial, dataFinal);
        return ResponseEntity.ok(listaCultos);
    }

    //Buscar datas mais recentes
    public ResponseEntity<List<CultoEntity>> getAllCultosRecentes() {
        List<CultoEntity> cultosFiltrados = cultoRepository.findAllByOrderByDataCultoDesc();
        return ResponseEntity.ok(cultosFiltrados);
    }

    public Long getCount() {
        Long countCultos = cultoRepository.count();
        return countCultos;
    }

    public ResponseEntity<List<CultoResponseDTO>> filtroTitulo(String titulo) {
        List<CultoEntity> cultosFiltrados = cultoRepository.findByTituloCultoContainingIgnoreCase(titulo);
        List<CultoResponseDTO> cultosDTOFiltrados = cultosFiltrados.stream()
                .map(cultoMapper::toDTO)
                .collect(Collectors.toList());

        return cultosDTOFiltrados.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cultosDTOFiltrados);
    }
}