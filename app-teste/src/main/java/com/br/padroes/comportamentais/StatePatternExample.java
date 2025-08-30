package com.br.padroes.comportamentais;

// Exemplo de uso
public class StatePatternExample {
    public static void main(String[] args) {
        Context context = new Context(new ConcreteStateA());

        context.request(); // Processando no Estado A

        context.setState(new ConcreteStateB());
        context.request(); // Processando no Estado B
    }
}


// Interface do Estado
interface State {
    void handle();
}

// Classe Contexto
class Context {
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void request() {
        state.handle();
    }
}

// Estado Concreto A
class ConcreteStateA implements State {
    @Override
    public void handle() {
        System.out.println("Estado A: Processando...");
    }
}

// Estado Concreto B
class ConcreteStateB implements State {
    @Override
    public void handle() {
        System.out.println("Estado B: Processando...");
    }
}