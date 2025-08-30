package com.br.padroes.estruturais;

// Interface do sujeito
interface Image {
    void display();
}

// Sujeito real
class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading image: " + filename);
    }

    @Override
    public void display() {
        System.out.println("Displaying image: " + filename);
    }
}

// Proxy
class ImageProxy implements Image {
    private RealImage realImage;
    private String filename;

    public ImageProxy(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // Carregamento lazy
        }
        realImage.display();
    }
}

// Exemplo de uso
public class ProxyExample {
    public static void main(String[] args) {
        // Criando proxies para imagens
        Image image1 = new ImageProxy("photo1.jpg");
        Image image2 = new ImageProxy("photo2.jpg");

        // A imagem só é carregada quando display() é chamado
        System.out.println("First call to image1:");
        image1.display();

        System.out.println("\nSecond call to image1 (no reloading):");
        image1.display();

        System.out.println("\nFirst call to image2:");
        image2.display();
    }
}