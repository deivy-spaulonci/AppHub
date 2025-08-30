package com.br.padroes.comportamentais;

import java.util.ArrayList;
import java.util.List;

// Interface do Mediator
interface ChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}

// Mediator concreto
class ChatRoom implements ChatMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
        System.out.println(user.getName() + " joined the chat room");
    }

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            // Envia a mensagem para todos os usuários, exceto o remetente
            if (user != sender) {
                user.receiveMessage(message, sender.getName());
            }
        }
    }
}

// Classe colega
class User {
    private String name;
    private ChatMediator mediator;

    public User(String name, ChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(String message) {
        System.out.println(name + " sends: " + message);
        mediator.sendMessage(message, this);
    }

    public void receiveMessage(String message, String sender) {
        System.out.println(name + " received from " + sender + ": " + message);
    }
}

// Exemplo de uso
public class MediatorExample {
    public static void main(String[] args) {
        // Criando o mediador
        ChatMediator chatRoom = new ChatRoom();

        // Criando os colegas
        User alice = new User("Alice", chatRoom);
        User bob = new User("Bob", chatRoom);
        User charlie = new User("Charlie", chatRoom);

        // Adicionando usuários à sala de chat
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);

        // Enviando mensagens
        alice.sendMessage("Hi everyone!");
        bob.sendMessage("Hey Alice, how's it going?");
        charlie.sendMessage("Hello all!");
    }
}