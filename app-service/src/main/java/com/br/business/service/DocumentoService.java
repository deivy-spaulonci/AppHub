package com.br.business.service;

import com.br.dto.response.DiretorioResponseDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentoService {

    public static List<DiretorioResponseDTO> listarArquivosEmFormatoDeArvore(String path) {
        List<DiretorioResponseDTO> estrutura = new ArrayList<>();
        File pasta = new File(path);

        if (pasta.exists() && pasta.isDirectory()) {
            DiretorioResponseDTO diretorioResponseDTORaiz = listarArquivosEmFormatoDeArvore(pasta);
            estrutura.add(diretorioResponseDTORaiz);
        } else {
            System.out.println("Caminho não é uma pasta válida.");
        }

        return estrutura;
    }

    private static DiretorioResponseDTO listarArquivosEmFormatoDeArvore(File pasta) {
        DiretorioResponseDTO diretorioResponseDTO = new DiretorioResponseDTO(pasta.getName());

        // Obtém todos os arquivos e subpastas na pasta atual
        File[] itens = pasta.listFiles();

        if (itens != null) {
            for (File item : itens) {
                if (item.isDirectory()) {
                    // Adiciona subdiretório recursivamente
                    diretorioResponseDTO.addSubDiretorio(listarArquivosEmFormatoDeArvore(item));
                } else {
                    // Adiciona arquivo
                    diretorioResponseDTO.addArquivo(item.getName());
                    //diretorio.setPath("file://"+pasta.getAbsolutePath() + "/" + item.getName());
                }
            }
        }
        return diretorioResponseDTO;
    }

//    public static void imprimirEstrutura(List<DiretorioResponseDTO> estrutura, String indentacao) {
//        for (DiretorioResponseDTO diretorioResponseDTO : estrutura) {
//            System.out.println(indentacao + "[DIR] " + diretorioResponseDTO.getNome());
//
//            // Imprime arquivos no diretório
//            for (String arquivo : diretorioResponseDTO.getArquivos()) {
//                System.out.println(indentacao + "  [ARQUIVO] " + arquivo);
//            }
//
//            // Imprime os subdiretórios
//            if (!diretorioResponseDTO.getSubDiretorioResponsDTOS().isEmpty()) {
//                imprimirEstrutura(diretorioResponseDTO.getSubDiretorioResponsDTOS(), indentacao + "  ");
//            }
//        }
//    }
//    public static void main(String[] args) {
//        // Substitua o caminho pelo diretório que você deseja passar
//        String caminhoDaPasta = "/media/deivy/hd01/Pagamentos/Pagamentos_PF"; // Caminho do diretório
//        List<Diretorio> estrutura = listarArquivosEmFormatoDeArvore(caminhoDaPasta);
//
//        // Imprimir a estrutura armazenada
//        imprimirEstrutura(estrutura, "");
//    }
}
