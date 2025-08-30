package com.br.padroes.estruturais;

// Interface da implementação (Implementor)
interface RemoteControl {
    void powerOn();
    void powerOff();
    void setVolume(int volume);
}

// Implementações concretas do controle remoto
class BasicRemote implements RemoteControl {
    @Override
    public void powerOn() {
        System.out.println("Basic Remote: Power ON");
    }

    @Override
    public void powerOff() {
        System.out.println("Basic Remote: Power OFF");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("Basic Remote: Set volume to " + volume);
    }
}

class AdvancedRemote implements RemoteControl {
    @Override
    public void powerOn() {
        System.out.println("Advanced Remote: Power ON with advanced features");
    }

    @Override
    public void powerOff() {
        System.out.println("Advanced Remote: Power OFF with advanced features");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("Advanced Remote: Set volume to " + volume + " with equalizer");
    }
}

// Abstração
abstract class Device {
    protected RemoteControl remote;

    public Device(RemoteControl remote) {
        this.remote = remote;
    }

    abstract void turnOn();
    abstract void turnOff();
    abstract void adjustVolume(int volume);
}

// Abstrações refinadas
class TV extends Device {
    public TV(RemoteControl remote) {
        super(remote);
    }

    @Override
    public void turnOn() {
        System.out.print("TV: ");
        remote.powerOn();
    }

    @Override
    public void turnOff() {
        System.out.print("TV: ");
        remote.powerOff();
    }

    @Override
    public void adjustVolume(int volume) {
        System.out.print("TV: ");
        remote.setVolume(volume);
    }
}

class Radio extends Device {
    public Radio(RemoteControl remote) {
        super(remote);
    }

    @Override
    public void turnOn() {
        System.out.print("Radio: ");
        remote.powerOn();
    }

    @Override
    public void turnOff() {
        System.out.print("Radio: ");
        remote.powerOff();
    }

    @Override
    public void adjustVolume(int volume) {
        System.out.print("Radio: ");
        remote.setVolume(volume);
    }
}

// Exemplo de uso
public class BridgeExample {
    public static void main(String[] args) {
        // TV com controle básico
        Device tv = new TV(new BasicRemote());
        tv.turnOn();
        tv.adjustVolume(20);
        tv.turnOff();

        System.out.println();

        // TV com controle avançado
        Device tvAdvanced = new TV(new AdvancedRemote());
        tvAdvanced.turnOn();
        tvAdvanced.adjustVolume(30);
        tvAdvanced.turnOff();

        System.out.println();

        // Rádio com controle básico
        Device radio = new Radio(new BasicRemote());
        radio.turnOn();
        radio.adjustVolume(15);
        radio.turnOff();
    }
}