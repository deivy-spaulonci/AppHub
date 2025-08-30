package com.br.padroes.comportamentais;

// Classe Abstracta
abstract class AbstractClass {
    // Método Template
    public final void templateMethod() {
        stepOne();
        stepTwo();
        stepThree();
    }

    // Etapas do algoritmo
    protected abstract void stepOne();
    protected abstract void stepTwo();

    // Etapa comum
    private void stepThree() {
        System.out.println("Executando etapa comum...");
    }
}

// Subclasse Concreta A
class ConcreteClassA extends AbstractClass {
    @Override
    protected void stepOne() {
        System.out.println("Classe A: Executando etapa um...");
    }

    @Override
    protected void stepTwo() {
        System.out.println("Classe A: Executando etapa dois...");
    }
}

// Subclasse Concreta B
class ConcreteClassB extends AbstractClass {
    @Override
    protected void stepOne() {
        System.out.println("Classe B: Executando etapa um...");
    }

    @Override
    protected void stepTwo() {
        System.out.println("Classe B: Executando etapa dois...");
    }
}

// Exemplo de uso
public class TemplateMethodExample {
    public static void main(String[] args) {
        AbstractClass classA = new ConcreteClassA();
        classA.templateMethod(); // Executa o algoritmo da Classe A

        System.out.println();

        AbstractClass classB = new ConcreteClassB();
        classB.templateMethod(); // Executa o algoritmo da Classe B
    }
}
