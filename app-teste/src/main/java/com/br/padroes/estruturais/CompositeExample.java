package com.br.padroes.estruturais;

import java.util.ArrayList;
import java.util.List;

// Interface do componente
interface FileSystemComponent {
    void showDetails();
    int getSize();
}

// Classe folha (arquivo)
class File implements FileSystemComponent {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void showDetails() {
        System.out.println("File: " + name + " (Size: " + size + " KB)");
    }

    @Override
    public int getSize() {
        return size;
    }
}

// Classe composta (pasta)
class Folder implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void addComponent(FileSystemComponent component) {
        components.add(component);
    }

    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public void showDetails() {
        System.out.println("Folder: " + name + " (Size: " + getSize() + " KB)");
        for (FileSystemComponent component : components) {
            component.showDetails();
        }
    }

    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}

// Exemplo de uso
public class CompositeExample {
    public static void main(String[] args) {
        // Criando arquivos (folhas)
        FileSystemComponent file1 = new File("document.txt", 100);
        FileSystemComponent file2 = new File("image.jpg", 200);
        FileSystemComponent file3 = new File("video.mp4", 500);

        // Criando pastas (composições)
        Folder documentsFolder = new Folder("Documents");
        documentsFolder.addComponent(file1);

        Folder mediaFolder = new Folder("Media");
        mediaFolder.addComponent(file2);
        mediaFolder.addComponent(file3);

        Folder rootFolder = new Folder("Root");
        rootFolder.addComponent(documentsFolder);
        rootFolder.addComponent(mediaFolder);

        // Mostrando detalhes da estrutura
        rootFolder.showDetails();
    }
}