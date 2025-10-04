package com.br.business.service;

import com.br.dto.Diretorio;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentoService {

//    // Classe para armazenar a estrutura de diretórios e arquivos
//    static class Diretorio {
//        String nome;
//        List<Diretorio> subDiretorios;
//        List<String> arquivos;
//
//        Diretorio(String nome) {
//            this.nome = nome;
//            this.subDiretorios = new ArrayList<>();
//            this.arquivos = new ArrayList<>();
//        }
//
//        // Adiciona um subdiretório
//        void addSubDiretorio(Diretorio subdiretorio) {
//            this.subDiretorios.add(subdiretorio);
//        }
//
//        // Adiciona um arquivo
//        void addArquivo(String arquivo) {
//            this.arquivos.add(arquivo);
//        }
//    }

    public static List<Diretorio> listarArquivosEmFormatoDeArvore(String path) {
        List<Diretorio> estrutura = new ArrayList<>();
        File pasta = new File(path);

        if (pasta.exists() && pasta.isDirectory()) {
            Diretorio diretorioRaiz = listarArquivosEmFormatoDeArvore(pasta);
            estrutura.add(diretorioRaiz);
        } else {
            System.out.println("Caminho não é uma pasta válida.");
        }

        return estrutura;
    }

    private static Diretorio listarArquivosEmFormatoDeArvore(File pasta) {
        Diretorio diretorio = new Diretorio(pasta.getName());

        // Obtém todos os arquivos e subpastas na pasta atual
        File[] itens = pasta.listFiles();

        if (itens != null) {
            for (File item : itens) {
                if (item.isDirectory()) {
                    // Adiciona subdiretório recursivamente
                    diretorio.addSubDiretorio(listarArquivosEmFormatoDeArvore(item));
                } else {
                    // Adiciona arquivo
                    diretorio.addArquivo(item.getName());
                    //diretorio.setPath("file://"+pasta.getAbsolutePath() + "/" + item.getName());
                }
            }
        }

        return diretorio;
    }

    public static void imprimirEstrutura(List<Diretorio> estrutura, String indentacao) {
        for (Diretorio diretorio : estrutura) {
            System.out.println(indentacao + "[DIR] " + diretorio.getNome());

            // Imprime arquivos no diretório
            for (String arquivo : diretorio.getArquivos()) {
                System.out.println(indentacao + "  [ARQUIVO] " + arquivo);
            }

            // Imprime os subdiretórios
            if (!diretorio.getSubDiretorios().isEmpty()) {
                imprimirEstrutura(diretorio.getSubDiretorios(), indentacao + "  ");
            }
        }
    }
//    public static void main(String[] args) {
//        // Substitua o caminho pelo diretório que você deseja passar
//        String caminhoDaPasta = "/media/deivy/hd01/Pagamentos/Pagamentos_PF"; // Caminho do diretório
//        List<Diretorio> estrutura = listarArquivosEmFormatoDeArvore(caminhoDaPasta);
//
//        // Imprimir a estrutura armazenada
//        imprimirEstrutura(estrutura, "");
//    }
}
