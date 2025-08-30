package com.br.padroes.comportamentais;

import java.util.ArrayList;
import java.util.List;

// Interface do Observador
interface Observer {
    void update(String news);
}

// Interface do Sujeito
interface NewsAgency {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// Sujeito Concreto
class ConcreteNewsAgency implements NewsAgency {
    private List<Observer> observers = new ArrayList<>();
    private String news;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Observer added");
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer removed");
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }

    public void setNews(String news) {
        this.news = news;
        System.out.println("NewsAgency: New news published - " + news);
        notifyObservers();
    }
}

// Observador Concreto
class NewsSubscriber implements Observer {
    private String name;

    public NewsSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String news) {
        System.out.println(name + " received news: " + news);
    }
}

// Exemplo de uso
public class ObserverExample {
    public static void main(String[] args) {
        // Criando o sujeito
        ConcreteNewsAgency newsAgency = new ConcreteNewsAgency();

        // Criando observadores
        NewsSubscriber subscriber1 = new NewsSubscriber("Alice");
        NewsSubscriber subscriber2 = new NewsSubscriber("Bob");
        NewsSubscriber subscriber3 = new NewsSubscriber("Charlie");

        // Adicionando observadores
        newsAgency.addObserver(subscriber1);
        newsAgency.addObserver(subscriber2);

        // Publicando uma notícia
        newsAgency.setNews("Breaking: Major event reported!");

        // Adicionando e removendo observadores
        newsAgency.addObserver(subscriber3);
        newsAgency.removeObserver(subscriber1);

        // Publicando outra notícia
        newsAgency.setNews("Update: Event details released!");
    }
}