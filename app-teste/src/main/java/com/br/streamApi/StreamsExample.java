package com.br.streamApi;

public class StreamsExample {
    /**
     * 1. Programação Funcional
     * Estilo Declarativo: Permite que você escreva código de forma mais concisa e legível, utilizando expressões lambda e referências a métodos.
     * Menos Código Boilerplate: Reduz a quantidade de código necessário para manipulação de coleções.
     *
     * 2. Processamento em Paralelo
     * Paralelismo Facilitado: A API permite que você execute operações em paralelo de forma simples, utilizando o método parallelStream(), que pode melhorar o desempenho ao lidar com grandes conjuntos de dados.
     *
     * 3. Operações de Fluxo
     * Operações Intermediárias: Como filter(), map(), flatMap(), distinct(), sorted(), que transformam ou filtram os dados em uma sequência.
     * Operações Terminais: Como collect(), forEach(), reduce(), que consomem o fluxo e produzem um resultado final.
     *
     * 4. Lazy Evaluation
     * Avaliação Preguiçosa: As operações de fluxo são avaliadas apenas quando necessárias, o que pode resultar em melhorias de desempenho, especialmente em grandes coleções.
     *
     * 5. Composição de Operações
     * Pipeline de Processamento: Você pode encadear múltiplas operações em um único fluxo, criando um pipeline de processamento que é fácil de ler e entender.
     *
     * 6. Maior Flexibilidade
     * Suporte a Diferentes Fontes de Dados: Streams podem ser criados a partir de coleções, arrays, arquivos e muito mais, permitindo uma manipulação mais uniforme dos dados.
     *
     * 7. Facilidade de Agrupamento e Redução
     * Agrupamento e Estatísticas: Métodos como collect(Collectors.groupingBy()) e Collectors.partitioningBy() tornam fácil agrupar dados ou calcular estatísticas.
     */
}
