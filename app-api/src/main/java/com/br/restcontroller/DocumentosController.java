package com.br.restcontroller;

import com.br.business.service.DocumentoService;
import com.br.dto.Diretorio;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentosController {

    private DocumentoService documentoService;

    @Autowired
    public DocumentosController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping("/listar-arquivos")
    public ResponseEntity<List<Diretorio>> documentos(){
        return ok(documentoService.listarArquivosEmFormatoDeArvore("/media/deivy/hd01/Pagamentos/Pagamentos_PF"));
    }

    @GetMapping("/diretorio")
    public ResponseEntity<List<Arquivo>> diretorio(@RequestParam String path) throws Exception{

        Path pasta = Paths.get(path);
        AtomicLong seq = new AtomicLong(1);
        List<Arquivo> arquivos = Files.list(pasta)  // Stream<Path>
                .filter(Files::isRegularFile)     // apenas arquivos (ignora pastas)
                .map(p -> {
                    String nomeCompleto = p.getFileName().toString();

                    String ext = "";
                    int ponto = nomeCompleto.lastIndexOf('.');
                    if (ponto > 0) {
                        ext = nomeCompleto.substring(ponto + 1);
                    }
                    try {

                        byte[] bytes = Files.readAllBytes(p.toAbsolutePath());
                        String base64 = Base64.getEncoder().encodeToString(bytes);

                        return new Arquivo(seq.getAndIncrement(), nomeCompleto, ext, Files.size(p.toAbsolutePath()), base64);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).sorted(Comparator.comparing(Arquivo::nome))
                .collect(Collectors.toList());

        return ok(arquivos);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteDoc(@RequestBody List<String> paths) {

        log.info("Deletando documentos ");
        paths.forEach(log::info);

        try {
            for(String path : paths) {
                if(!Files.exists(Paths.get(path)))
                    return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro alguns o arquivo ("+path+") nao existe !");
            }
            for(String path : paths) {
                Files.delete(Paths.get(path));
            }
            return ok().body("arquivo apagado com sucesso");
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar arquivo");
        }
    }

    @PutMapping("/rename")
    public ResponseEntity<String> renameDoc(@RequestBody RenameRequest request) {
        Path arquivoAntigo = Paths.get(request.oldPath());
        Path arquivoNovo   = Paths.get(request.newPath());
        try {
            Files.move(arquivoAntigo, arquivoNovo, StandardCopyOption.REPLACE_EXISTING);
            return ok("Arquivo renomeado com sucesso!");
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao renomear arquivo!");
        }
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

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam String path) throws IOException {
        Path arquivo = Paths.get(path);
        log.info(arquivo.toString());
        if(Files.exists(arquivo)) {
            String nome = arquivo.getFileName().toString();
            String extensao = "";
            int i = nome.lastIndexOf('.');
            if (i > 0) {
                extensao = nome.substring(i + 1); // tira o ponto
            }

            byte[] pdfBytes = Files.readAllBytes(arquivo);

            if(extensao.equals("pdf")){
                return ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=arquivo.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes);

            }else{
                String mimeType = Files.probeContentType(arquivo);
                return ok()
                        .header(HttpHeaders.CONTENT_TYPE, mimeType)
                        .body(pdfBytes);
            }
        }else
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

}

record Arquivo(Long id, String nome, String ext, long sz, String base64) {}
record RenameRequest(String oldPath, String newPath) {}



