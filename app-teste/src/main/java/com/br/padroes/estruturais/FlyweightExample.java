package com.br.padroes.estruturais;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Interface Flyweight
interface Tree {
    void display(int x, int y);
}

// Flyweight concreto
class TreeType implements Tree {
    private String name; // Estado intrínseco (compartilhado)
    private String color;

    public TreeType(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public void display(int x, int y) { // Estado extrínseco (x, y)
        System.out.println("Displaying " + name + " tree with color " + color + " at position (" + x + "," + y + ")");
    }
}

// Fábrica de Flyweight
class TreeFactory {
    private static Map<String, TreeType> treeTypes = new HashMap<>();

    public static TreeType getTreeType(String name, String color) {
        String key = name + "-" + color;
        TreeType treeType = treeTypes.get(key);

        if (treeType == null) {
            treeType = new TreeType(name, color);
            treeTypes.put(key, treeType);
            System.out.println("Created new tree type: " + name + ", " + color);
        }

        return treeType;
    }
}

// Classe que gerencia o cenário (cliente)
class Forest {
    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color) {
        TreeType treeType = TreeFactory.getTreeType(name, color);
        trees.add(new Tree() {
            @Override
            public void display(int x, int y) {
                treeType.display(x, y);
            }
        });
    }

    public void display() {
        for (Tree tree : trees) {
            tree.display(trees.indexOf(tree) * 10, trees.indexOf(tree) * 10);
        }
    }
}

// Exemplo de uso
public class FlyweightExample {
    public static void main(String[] args) {
        Forest forest = new Forest();

        // Plantando árvores (compartilhando tipos de árvores)
        forest.plantTree(0, 0, "Oak", "Green");
        forest.plantTree(10, 10, "Oak", "Green"); // Reutiliza o mesmo TreeType
        forest.plantTree(20, 20, "Pine", "DarkGreen");
        forest.plantTree(30, 30, "Oak", "Green"); // Reutiliza o mesmo TreeType

        // Exibindo as árvores
        forest.display();
    }
}