package com.br.restcontroller;

import com.br.business.service.DocumentoService;
import com.br.dto.Diretorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/diretorio")
    public ResponseEntity<List<String>> diretorio(@RequestParam String path) throws IOException {
        Path pasta = Paths.get(path);

        List<String> arquivos = Files.list(pasta)  // Stream<Path>
                .filter(Files::isRegularFile)     // apenas arquivos (ignora pastas)
                .map(p -> p.getFileName().toString()) // pega só o nome do arquivo
                .sorted()
                .collect(Collectors.toList());

        return ok(arquivos);
    }

    @GetMapping("/base64")
    public ResponseEntity<FileResponse> getFileBase64(@RequestParam String path) throws IOException {

        Path filePath = Path.of(path);

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] bytes = Files.readAllBytes(filePath);
        String base64 = Base64.getEncoder().encodeToString(bytes);

        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        FileResponse response = new FileResponse(mimeType, base64);
        return ResponseEntity.ok(response);
    }

    public static class FileResponse {
        private String mimeType;
        private String base64;

        public FileResponse(String mimeType, String base64) {
            this.mimeType = mimeType;
            this.base64 = base64;
        }

        public String getMimeType() {
            return mimeType;
        }

        public String getBase64() {
            return base64;
        }
    }
}
