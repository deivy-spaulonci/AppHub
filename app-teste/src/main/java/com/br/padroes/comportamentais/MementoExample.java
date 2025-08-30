package com.br.padroes.comportamentais;

import java.util.Stack;

// Classe Memento
class TextMemento {
    private final String text;

    public TextMemento(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

// Originator
class TextEditor {
    private String text;

    public TextEditor() {
        this.text = "";
    }

    public void write(String text) {
        this.text = text;
        System.out.println("TextEditor: Current text = " + text);
    }

    public TextMemento save() {
        return new TextMemento(text);
    }

    public void restore(TextMemento memento) {
        this.text = memento.getText();
        System.out.println("TextEditor: Restored text = " + text);
    }
}

// Caretaker
class History {
    private Stack<TextMemento> mementos = new Stack<>();

    public void save(TextMemento memento) {
        mementos.push(memento);
    }

    public TextMemento undo() {
        if (!mementos.isEmpty()) {
            return mementos.pop();
        }
        return null;
    }
}

// Exemplo de uso
public class MementoExample {
    public static void main(String[] args) {
        // Criando o originator e o caretaker
        TextEditor editor = new TextEditor();
        History history = new History();

        // Escrevendo e salvando estados
        editor.write("Hello");
        history.save(editor.save());

        editor.write("Hello, World!");
        history.save(editor.save());

        editor.write("Hello, World! How are you?");
        history.save(editor.save());

        // Desfazendo alterações
        System.out.println("\nUndoing changes:");
        TextMemento memento = history.undo();
        if (memento != null) {
            editor.restore(memento);
        }

        memento = history.undo();
        if (memento != null) {
            editor.restore(memento);
        }

        memento = history.undo();
        if (memento != null) {
            editor.restore(memento);
        }

        // Tentando desfazer quando não há mais estados
        memento = history.undo();
        if (memento == null) {
            System.out.println("History: No more states to undo");
        }
    }
}