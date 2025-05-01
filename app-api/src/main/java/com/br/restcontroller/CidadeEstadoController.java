package com.br.restcontroller;

import com.br.business.service.CidadeService;
import com.br.dto.CidadeDto;
import com.br.entity.Cidade;
import com.br.entity.Estado;
import com.br.mapper.CidadeMapper;
import com.br.mapper.FornecedorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/cidade")
public class CidadeEstadoController {

    @Autowired
    private CidadeService cidadeService;
    private static final CidadeMapper cidadeMapper = CidadeMapper.INSTANCE;

    @GetMapping("/estados")
    public List<Estado> getEstados() {
        return Arrays.stream(Estado.values()).sorted().toList();
    }

    @GetMapping("/{uf}")
    public ResponseEntity<List<CidadeDto>> getEstados(@PathVariable("uf") String uf) {
        List<Cidade> cidades = cidadeService.listCidadeByUf(uf);
        if(cidades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ok(cidadeMapper.toDtoList(cidades));
    }
}
