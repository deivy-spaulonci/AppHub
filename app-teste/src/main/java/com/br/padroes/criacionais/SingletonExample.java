package com.br.padroes.criacionais;



// Exemplo de uso
public class SingletonExample {
    public static void main(String[] args) {
        // Obtendo a instância do Singleton
        Singleton singleton1 = Singleton.getInstance();
        singleton1.showMessage();
        
        // Obtendo a mesma instância novamente
        Singleton singleton2 = Singleton.getInstance();
        singleton2.showMessage();
        
        // Verificando se ambas as referências apontam para a mesma instância
        System.out.println("Same instance? " + (singleton1 == singleton2));
    }
}

// Classe Singleton
class Singleton {
    // Instância única, inicializada como null
    private static volatile Singleton instance = null;

    // Construtor privado para evitar instanciação direta
    private Singleton() {
        // Previne criação via reflexão
        if (instance != null) {
            throw new RuntimeException("Use getInstance() to get the singleton instance.");
        }
    }

    // Método estático para obter a instância única (thread-safe)
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    // Exemplo de método da classe
    public void showMessage() {
        System.out.println("Singleton instance: " + this);
    }
}