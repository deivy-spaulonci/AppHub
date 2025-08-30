package com.br.padroes.comportamentais;

// Interface do Elemento
interface Element {
    void accept(Visitor visitor);
}

// Interface do Visitante
interface Visitor {
    void visit(ConcreteElementA element);
    void visit(ConcreteElementB element);
}

// Elemento Concreto A
class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationA() {
        return "Resultado da operação A";
    }
}

// Elemento Concreto B
class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationB() {
        return "Resultado da operação B";
    }
}

// Visitante Concreto
class ConcreteVisitor implements Visitor {
    @Override
    public void visit(ConcreteElementA element) {
        System.out.println("Visitando " + element.operationA());
    }

    @Override
    public void visit(ConcreteElementB element) {
        System.out.println("Visitando " + element.operationB());
    }
}

// Exemplo de uso
public class VisitorPatternExample {
    public static void main(String[] args) {
        Element elementA = new ConcreteElementA();
        Element elementB = new ConcreteElementB();

        Visitor visitor = new ConcreteVisitor();

        elementA.accept(visitor); // Visitando Resultado da operação A
        elementB.accept(visitor); // Visitando Resultado da operação B
    }
}
