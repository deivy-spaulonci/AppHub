package com.br.restcontroller;

import com.br.business.service.DocumentoService;
import com.br.dto.Diretorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentosController {

    @Autowired
    private DocumentoService documentoService;

    @GetMapping("/listar-arquivos")
    public ResponseEntity<List<Diretorio>> documentos(){
        return ok(documentoService.listarArquivosEmFormatoDeArvore("/media/deivy/hd01/Pagamentos/Pagamentos_PF"));
    }
}
