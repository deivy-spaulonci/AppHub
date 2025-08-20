package com.br.dao.generic;

import java.util.List;

public interface GenericDAO<T> {
    void inserir(T obj);
    void atualizar(T obj);
    void deletar(int id);
    T buscarPorId(int id);
    List<T> listarTodos();
}
