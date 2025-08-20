package com.br.shared;

import com.br.entity.Fornecedor;

public class FornecedorCbx {
    private Fornecedor fornecedor;

    public FornecedorCbx(Fornecedor fornecedor){
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return fornecedor.getNome();
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }
}
