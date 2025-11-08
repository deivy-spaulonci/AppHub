package com.br.dto.response;

public record ArquivoResponse(Long id, String nome, String ext, long sz, String base64) {
}
