package com.jpa.visao;

import com.jpa.dao.ProdutoDAO;
import com.jpa.modelo.Produto;

public class App {

    public static void main(String[] args) {

        Produto produto = new Produto();

        //produto.setId(2);
        //produto.setNome("Arroz");
        //produto.setValor("7,00");

        ProdutoDAO pro = new ProdutoDAO();

        //produto = pro.Salvar(produto);

        //produto = pro.Atualizar(produto);

        //produto = pro.Buscar(2);

        //produto = pro.Remover(2);

    }
}
