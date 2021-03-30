package com.jpa.dao;

import com.google.gson.Gson;
import com.jpa.modelo.Produto;
import redis.clients.jedis.Jedis;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProdutoDAO {

    public EntityManager getEM(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("conexao");
        return factory.createEntityManager();
    }

    public Produto Salvar(Produto produto){

        EntityManager em = getEM();
        em.getTransaction().begin();
        em.persist(produto);
        em.getTransaction().commit();
        em.close();
        System.out.println(produto);
        return produto;
    }

    public Produto Atualizar(Produto produto){
        EntityManager em = getEM();
        em.getTransaction().begin();
        produto = em.merge(produto);
        em.getTransaction().commit();
        em.close();
            return produto;
    }

    public Produto Remover(int id){
        EntityManager em = getEM();
        Produto produto = em.find(Produto.class, id);
        em.getTransaction().begin();
        em.remove(produto);
        em.getTransaction().commit();
            return produto;
    }

    public Produto Buscar(int id){
        Jedis jedis = new Jedis("172.18.0.2");
        Gson gson = new Gson();

        Produto pro = gson.fromJson(jedis.get(String.valueOf(id)), Produto.class);

        if(pro != null){
            System.out.println(pro);
            return pro;
        }else {
            EntityManager em = getEM();
            Produto produto = null;
            produto = em.find(Produto.class, id);
            System.out.println(produto);

            produto = Inserir_no_Redis(produto);

            return produto;
        }
    }

    public Produto Inserir_no_Redis(Produto produto){
        Jedis jedis = new Jedis("172.18.0.2");
        Gson gson = new Gson();

        jedis.setex(String.valueOf(produto.getId()), 3600, gson.toJson(produto));
        return produto;
    }

}