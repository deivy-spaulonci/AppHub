package com.br.padroes.comportamentais;

import java.util.ArrayList;
import java.util.List;

// Interface do Iterator
interface Iterator<T> {
    boolean hasNext();
    T next();
}

// Interface da Coleção
interface BookCollection {
    Iterator<Book> createIterator();
}

// Classe que representa um livro
class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

// Coleção concreta
class Library implements BookCollection {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public Iterator<Book> createIterator() {
        return new LibraryIterator(books);
    }
}

// Iterator concreto
class LibraryIterator implements Iterator<Book> {
    private List<Book> books;
    private int index;

    public LibraryIterator(List<Book> books) {
        this.books = books;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < books.size();
    }

    @Override
    public Book next() {
        if (hasNext()) {
            return books.get(index++);
        }
        return null;
    }
}

// Exemplo de uso
public class IteratorExample {
    public static void main(String[] args) {
        // Criando a coleção
        Library library = new Library();
        library.addBook(new Book("Design Patterns"));
        library.addBook(new Book("Clean Code"));
        library.addBook(new Book("Refactoring"));

        // Obtendo o iterator
        Iterator<Book> iterator = library.createIterator();

        // Percorrendo a coleção
        System.out.println("Books in the library:");
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(" - " + book.getTitle());
        }
    }
}