package com.br.padroes.estruturais;

// Interface alvo esperada pelo cliente
interface PaymentProcessor {
    void processPayment(double amount);
}

// Classe existente (adaptee) com interface incompatível
class LegacyPaymentSystem {
    public void makePayment(String amountInCents) {
        System.out.println("Processing payment of " + amountInCents + " cents using Legacy System");
    }
}

// Adaptador que conecta a interface nova com o sistema legado
class PaymentAdapter implements PaymentProcessor {
    private LegacyPaymentSystem legacySystem;

    public PaymentAdapter(LegacyPaymentSystem legacySystem) {
        this.legacySystem = legacySystem;
    }

    @Override
    public void processPayment(double amount) {
        // Converte o valor de double (em reais) para string (em centavos)
        String amountInCents = String.valueOf((int) (amount * 100));
        legacySystem.makePayment(amountInCents);
    }
}

// Exemplo de uso
public class AdapterExample {
    public static void main(String[] args) {
        // Cria o sistema legado
        LegacyPaymentSystem legacySystem = new LegacyPaymentSystem();

        // Cria o adaptador
        PaymentProcessor processor = new PaymentAdapter(legacySystem);

        // Cliente usa a nova interface sem saber que está lidando com o sistema legado
        processor.processPayment(99.99); // Simula pagamento de R$99,99
        processor.processPayment(25.50); // Simula pagamento de R$25,50
    }
}