package com.br.padroes.comportamentais;

import java.util.Stack;

// Interface do Command
interface Command {
    void execute();
    void undo();
}

// Receiver
class Light {
    public void turnOn() {
        System.out.println("Light: Turned ON");
    }

    public void turnOff() {
        System.out.println("Light: Turned OFF");
    }
}

// Comando concreto para ligar a luz
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}

// Comando concreto para desligar a luz
class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}

// Invoker
class RemoteControl {
    private Command command;
    private Stack<Command> history = new Stack<>();

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
        history.push(command);
    }

    public void pressUndo() {
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
        } else {
            System.out.println("RemoteControl: Nothing to undo");
        }
    }
}

// Exemplo de uso
public class CommandExample {
    public static void main(String[] args) {
        // Criando o receiver
        Light light = new Light();

        // Criando comandos
        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);

        // Criando o invoker
        RemoteControl remote = new RemoteControl();

        // Testando ligar a luz
        System.out.println("Turning light ON:");
        remote.setCommand(lightOn);
        remote.pressButton();

        // Desfazendo
        System.out.println("\nUndoing:");
        remote.pressUndo();

        // Testando desligar a luz
        System.out.println("\nTurning light OFF:");
        remote.setCommand(lightOff);
        remote.pressButton();

        // Desfazendo
        System.out.println("\nUndoing:");
        remote.pressUndo();

        // Tentando desfazer sem histórico
        System.out.println("\nTrying to undo again:");
        remote.pressUndo();
    }
}