package com.br.padroes.criacionais;

// Exemplo de uso
public class FactoryMethodExample {
    public static void main(String[] args) {
        // Criando logística terrestre
        Logistics roadLogistics = new RoadLogistics();
        roadLogistics.planDelivery();

        // Criando logística marítima
        Logistics seaLogistics = new SeaLogistics();
        seaLogistics.planDelivery();
    }
}


// Interface do produto
interface Transport {
    void deliver();
}

// Produtos concretos
class Truck implements Transport {
    public void deliver() {
        System.out.println("Delivering by land with a Truck");
    }
}

class Ship implements Transport {
    public void deliver() {
        System.out.println("Delivering by sea with a Ship");
    }
}

// Classe abstrata com o Factory Method
abstract class Logistics {
    // Factory Method
    abstract Transport createTransport();

    public void planDelivery() {
        Transport transport = createTransport();
        transport.deliver();
    }
}

// Fábricas concretas
class RoadLogistics extends Logistics {
    Transport createTransport() {
        return new Truck();
    }
}

class SeaLogistics extends Logistics {
    Transport createTransport() {
        return new Ship();
    }
}