package com.br.padroes.criacionais;

// Exemplo de uso
public class PrototypeExample {
    public static void main(String[] args) {
        // Cria o protótipo
        Car prototypeCar = new Car("Sedan", "Blue");
        System.out.println("Prototype: " + prototypeCar);

        // Clona o protótipo
        Car clonedCar1 = (Car) prototypeCar.clone();
        System.out.println("Cloned Car 1: " + clonedCar1);

        // Modifica o clone sem afetar o protótipo
        clonedCar1.setColor("Red");
        System.out.println("Cloned Car 1 (modified): " + clonedCar1);
        System.out.println("Prototype (unchanged): " + prototypeCar);

        // Outro clone com modificação
        Car clonedCar2 = (Car) prototypeCar.clone();
        clonedCar2.setColor("Green");
        System.out.println("Cloned Car 2: " + clonedCar2);
    }
}


// Interface Prototype
interface Prototype {
    Prototype clone();
}

// Classe concreta que implementa o Prototype
class Car implements Prototype {
    private String model;
    private String color;

    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    @Override
    public Prototype clone() {
        // Cria uma cópia do objeto atual
        return new Car(this.model, this.color);
    }

    @Override
    public String toString() {
        return "Car [model=" + model + ", color=" + color + "]";
    }
}