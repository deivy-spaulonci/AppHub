package com.br.padroes.estruturais;

// Interface do componente
interface Beverage {
    String getDescription();
    double getCost();
}

// Componente concreto
class Coffee implements Beverage {
    @Override
    public String getDescription() {
        return "Coffee";
    }

    @Override
    public double getCost() {
        return 5.0;
    }
}

// Decorador abstrato
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription();
    }

    @Override
    public double getCost() {
        return beverage.getCost();
    }
}

// Decoradores concretos
class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return beverage.getCost() + 1.5;
    }
}

class ChocolateDecorator extends BeverageDecorator {
    public ChocolateDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Chocolate";
    }

    @Override
    public double getCost() {
        return beverage.getCost() + 2.0;
    }
}

// Exemplo de uso
public class DecoratorExample {
    public static void main(String[] args) {
        // Bebida base: café simples
        Beverage coffee = new Coffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        // Café com leite
        Beverage coffeeWithMilk = new MilkDecorator(coffee);
        System.out.println(coffeeWithMilk.getDescription() + " $" + coffeeWithMilk.getCost());

        // Café com leite e chocolate
        Beverage coffeeWithMilkAndChocolate = new ChocolateDecorator(coffeeWithMilk);
        System.out.println(coffeeWithMilkAndChocolate.getDescription() + " $" + coffeeWithMilkAndChocolate.getCost());
    }
}