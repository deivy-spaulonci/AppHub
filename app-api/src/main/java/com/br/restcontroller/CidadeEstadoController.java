package com.br.restcontroller;

import com.br.business.service.CidadeService;
import com.br.dto.response.CidadeResponseDTO;
import com.br.entity.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cidade")
public class CidadeEstadoController {

    private CidadeService cidadeService;

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
    public List<CidadeResponseDTO> getEstados(@PathVariable("uf") String uf) {
        List<CidadeResponseDTO> cidades = cidadeService.listCidadeByUf(uf);
        if (cidades.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma cidade encontrada para a UF: " + uf);
        }
        return cidades;
    }
}
