package com.br.repository;

import com.br.entity.Cidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CidadeRepositoryTest {
    @Mock
    private CidadeRepository cidadeRepository;

    // Dados para os testes
    private Cidade saoPaulo;
    private Cidade campinas;
    private Cidade rioDeJaneiro;

    @BeforeEach
    public void setUp() {
        // Inicializa objetos para simular os dados
        saoPaulo = new Cidade(BigInteger.valueOf(1), "3550308", "São Paulo", "SP");
        campinas = new Cidade(BigInteger.valueOf(2), "3509502", "Campinas", "SP");
        rioDeJaneiro = new Cidade(BigInteger.valueOf(3), "3304557", "Rio de Janeiro", "RJ");
    }

    @Test
    public void testSalvarCidade() {
        // Arrange
        Cidade novaCidade = new Cidade(BigInteger.valueOf(4), "3106200", "Belo Horizonte", "MG");
        when(cidadeRepository.save(novaCidade)).thenReturn(novaCidade);

        // Act
        Cidade cidadeSalva = cidadeRepository.save(novaCidade);

        // Assert
        assertNotNull(cidadeSalva);
        assertEquals("MG", cidadeSalva.getUf());
        assertEquals("Belo Horizonte", cidadeSalva.getNome());
        verify(cidadeRepository, times(1)).save(novaCidade);
    }

    @Test
    public void testBuscarCidadePorId() {
        // Arrange
        when(cidadeRepository.findById(BigInteger.valueOf(1))).thenReturn(Optional.of(saoPaulo));

        // Act
        Optional<Cidade> cidade = cidadeRepository.findById(BigInteger.valueOf(1));

        // Assert
        assertTrue(cidade.isPresent());
        assertEquals("São Paulo", cidade.get().getNome());
        verify(cidadeRepository, times(1)).findById(BigInteger.valueOf(1));
    }

    @Test
    public void testFindCidadeByUf() {
        // Arrange
        List<Cidade> cidadesSP = Arrays.asList(saoPaulo, campinas);
        when(cidadeRepository.findCidadeByUf("SP")).thenReturn(cidadesSP);

        // Act
        List<Cidade> resultado = cidadeRepository.findCidadeByUf("SP");

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("São Paulo", resultado.get(0).getNome());
        assertEquals("Campinas", resultado.get(1).getNome());
        verify(cidadeRepository, times(1)).findCidadeByUf("SP");
    }

    @Test
    public void testFindCidadeByUfAndNomeContainingIgnoreCaseOrderByNome() {
        // Arrange
        List<Cidade> cidades = Arrays.asList(campinas, saoPaulo); // Ordem alfabética
        when(cidadeRepository.findCidadeByUfAndNomeContainingIgnoreCaseOrderByNome("SP", "pa"))
                .thenReturn(cidades);

        // Act
        List<Cidade> resultado = cidadeRepository.findCidadeByUfAndNomeContainingIgnoreCaseOrderByNome("SP", "pa");

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Campinas", resultado.get(0).getNome()); // Ordem alfabética
        assertEquals("São Paulo", resultado.get(1).getNome());
        verify(cidadeRepository, times(1))
                .findCidadeByUfAndNomeContainingIgnoreCaseOrderByNome("SP", "pa");
    }

    @Test
    public void testFindCidadeByIbgeCod() {
        // Arrange
        when(cidadeRepository.findCidadeByIbgeCod("3550308")).thenReturn(saoPaulo);

        // Act
        Cidade cidade = cidadeRepository.findCidadeByIbgeCod("3550308");

        // Assert
        assertNotNull(cidade);
        assertEquals("São Paulo", cidade.getNome());
        assertEquals("SP", cidade.getUf());
        verify(cidadeRepository, times(1)).findCidadeByIbgeCod("3550308");
    }

    @Test
    public void testDeletarCidade() {
        // Arrange
        doNothing().when(cidadeRepository).deleteById(BigInteger.valueOf(1));
        when(cidadeRepository.findById(BigInteger.valueOf(1))).thenReturn(Optional.empty());

        // Act
        cidadeRepository.deleteById(BigInteger.valueOf(1));
        Optional<Cidade> cidadeDeletada = cidadeRepository.findById(BigInteger.valueOf(1));

        // Assert
        assertFalse(cidadeDeletada.isPresent());
        verify(cidadeRepository, times(1)).deleteById(BigInteger.valueOf(1));
    }

}
