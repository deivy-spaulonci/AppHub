package com.br.padroes.criacionais;



public class AbstractFactoryExample {
    public static void main(String[] args) {
        // Cria aplicação com Windows factory
        GUIFactory windowsFactory = new WindowsFactory();
        Application windowsApp = new Application(windowsFactory);
        windowsApp.render();

        // Cria aplicação com MacOS factory
        GUIFactory macOSFactory = new MacOSFactory();
        Application macOSApp = new Application(macOSFactory);
        macOSApp.render();
    }
}


// Interface para produtos do tipo A
interface Button {
    void render();
}

// Produtos concretos do tipo A
class WindowsButton implements Button {
    public void render() {
        System.out.println("Rendering a Windows Button");
    }
}

class MacOSButton implements Button {
    public void render() {
        System.out.println("Rendering a MacOS Button");
    }
}


// Interface para produtos do tipo B
interface Checkbox {
    void check();
}

// Produtos concretos do tipo B
class WindowsCheckbox implements Checkbox {
    public void check() {
        System.out.println("Checking a Windows Checkbox");
    }
}

class MacOSCheckbox implements Checkbox {
    public void check() {
        System.out.println("Checking a MacOS Checkbox");
    }
}


// Interface da Abstract Factory
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Fábricas concretas
class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WindowsButton();
    }

    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacOSFactory implements GUIFactory {
    public Button createButton() {
        return new MacOSButton();
    }

    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}


// Classe cliente que usa a Abstract Factory
class Application {
    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void render() {
        button.render();
        checkbox.check();
    }
}