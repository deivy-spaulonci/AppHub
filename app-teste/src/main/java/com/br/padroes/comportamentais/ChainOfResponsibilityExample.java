package com.br.padroes.comportamentais;

// Interface do Handler
abstract class Approver {
    protected Approver nextApprover;

    public void setNext(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    public abstract void approveRequest(double amount);
}

// Handlers concretos
class Manager extends Approver {
    @Override
    public void approveRequest(double amount) {
        if (amount <= 1000) {
            System.out.println("Manager: Approved request of $" + amount);
        } else if (nextApprover != null) {
            System.out.println("Manager: Passing request of $" + amount + " to next approver");
            nextApprover.approveRequest(amount);
        } else {
            System.out.println("Manager: No one can approve request of $" + amount);
        }
    }
}

class Director extends Approver {
    @Override
    public void approveRequest(double amount) {
        if (amount <= 5000) {
            System.out.println("Director: Approved request of $" + amount);
        } else if (nextApprover != null) {
            System.out.println("Director: Passing request of $" + amount + " to next approver");
            nextApprover.approveRequest(amount);
        } else {
            System.out.println("Director: No one can approve request of $" + amount);
        }
    }
}

class CEO extends Approver {
    @Override
    public void approveRequest(double amount) {
        if (amount <= 10000) {
            System.out.println("CEO: Approved request of $" + amount);
        } else {
            System.out.println("CEO: Request of $" + amount + " is too large to approve");
        }
    }
}

// Exemplo de uso
public class ChainOfResponsibilityExample {
    public static void main(String[] args) {
        // Criando os manipuladores
        Approver manager = new Manager();
        Approver director = new Director();
        Approver ceo = new CEO();

        // Configurando a cadeia
        manager.setNext(director);
        director.setNext(ceo);

        // Testando solicitações
        System.out.println("Requesting $500:");
        manager.approveRequest(500);

        System.out.println("\nRequesting $4500:");
        manager.approveRequest(4500);

        System.out.println("\nRequesting $8000:");
        manager.approveRequest(8000);

        System.out.println("\nRequesting $15000:");
        manager.approveRequest(15000);
    }
}