package com.br.teste;

import java.util.ArrayList;
import java.util.List;

interface Figura{
    void desenha(double x, double y);
}

class Retangulo{

    public void desenhaRetangulo(double x, double y) {
        System.out.println("Retangulo desenhado" + x + " > " + y);
    }
}


public class ExemploThread {
    public static void main(String[] args) {
        Figura figura = (l, a) -> System.out.println("Figura desenhado " + l + " > " + a);
        figura.desenha(5, 10);

        Retangulo retangulo = new Retangulo();
        Figura fig2 = retangulo::desenhaRetangulo;
        fig2.desenha(5.5, 10.6);
    }
}
