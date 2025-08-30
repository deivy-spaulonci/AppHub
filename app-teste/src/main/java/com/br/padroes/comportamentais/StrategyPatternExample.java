package com.br.padroes.comportamentais;

// Interface da Estratégia
interface Strategy {
    int execute(int a, int b);
}

// Estratégia Concreta para adição
class AddStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
}

// Estratégia Concreta para subtração
class SubtractStrategy implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a - b;
    }
}

// Classe Contexto
class ContextStrategy {
    private Strategy strategy;

    public ContextStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int a, int b) {
        return strategy.execute(a, b);
    }
}

// Exemplo de uso
public class StrategyPatternExample {
    public static void main(String[] args) {
        ContextStrategy contextStrategy = new ContextStrategy(new AddStrategy());

        System.out.println("Adição: " + contextStrategy.executeStrategy(5, 3)); // 8

        contextStrategy.setStrategy(new SubtractStrategy());
        System.out.println("Subtração: " + contextStrategy.executeStrategy(5, 3)); // 2
    }
}
