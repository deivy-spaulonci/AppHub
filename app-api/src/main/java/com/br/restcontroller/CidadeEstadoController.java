package com.br.restcontroller;

import com.br.business.service.CidadeService;
import com.br.dto.CidadeDTO;
import com.br.entity.Cidade;
import com.br.entity.Estado;
import com.br.mapper.CidadeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/cidade")
public class CidadeEstadoController {


    private CidadeService cidadeService;
    private static final CidadeMapper cidadeMapper = CidadeMapper.INSTANCE;

    @Autowired
    public CidadeEstadoController(CidadeService cidadeService) {
        this.cidadeService=cidadeService;
    }

    @GetMapping("/estados")
    public List<Estado> getEstados() {
        return Arrays.stream(Estado.values()).sorted().toList();
    }

    @GetMapping("/{uf}")
    @ResponseStatus(HttpStatus.OK)
    public List<CidadeDTO> getEstados(@PathVariable("uf") String uf) {
        List<CidadeDTO> cidades = cidadeService.listCidadeByUf(uf);
        if (cidades.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma cidade encontrada para a UF: " + uf);
        }
        return cidades;
    }
}
