package com.br.padroes.estruturais;

// Subsistema: TV
class TV2 {
    public void turnOn() {
        System.out.println("TV: Turning on");
    }

    public void turnOff() {
        System.out.println("TV: Turning off");
    }

    public void setInput(String input) {
        System.out.println("TV: Setting input to " + input);
    }
}

// Subsistema: Amplificador
class Amplifier {
    public void turnOn() {
        System.out.println("Amplifier: Turning on");
    }

    public void turnOff() {
        System.out.println("Amplifier: Turning off");
    }

    public void setVolume(int level) {
        System.out.println("Amplifier: Setting volume to " + level);
    }
}

// Subsistema: DVD Player
class DVDPlayer {
    public void turnOn() {
        System.out.println("DVD Player: Turning on");
    }

    public void turnOff() {
        System.out.println("DVD Player: Turning off");
    }

    public void playMovie(String movie) {
        System.out.println("DVD Player: Playing movie " + movie);
    }
}

// Fachada para o sistema de home theater
class HomeTheaterFacade {
    private TV2 tv;
    private Amplifier amplifier;
    private DVDPlayer dvdPlayer;

    public HomeTheaterFacade(TV2 tv, Amplifier amplifier, DVDPlayer dvdPlayer) {
        this.tv = tv;
        this.amplifier = amplifier;
        this.dvdPlayer = dvdPlayer;
    }

    public void watchMovie(String movie) {
        System.out.println("Home Theater: Preparing to watch movie...");
        tv.turnOn();
        tv.setInput("DVD");
        amplifier.turnOn();
        amplifier.setVolume(10);
        dvdPlayer.turnOn();
        dvdPlayer.playMovie(movie);
        System.out.println("Home Theater: Enjoy the movie!");
    }

    public void endMovie() {
        System.out.println("Home Theater: Shutting down...");
        dvdPlayer.turnOff();
        amplifier.turnOff();
        tv.turnOff();
    }
}

// Exemplo de uso
public class FacadeExample {
    public static void main(String[] args) {
        // Criando os subsistemas
        TV2 tv2 = new TV2();
        Amplifier amplifier = new Amplifier();
        DVDPlayer dvdPlayer = new DVDPlayer();

        // Criando a fachada
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv2, amplifier, dvdPlayer);

        // Usando a fachada para assistir a um filme
        homeTheater.watchMovie("Inception");

        System.out.println();

        // Usando a fachada para encerrar
        homeTheater.endMovie();
    }
}