package com.jeanlima.springrestapiapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jeanlima.springrestapiapp.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque,Integer>
{
    @Query("SELECT e " +
    "FROM Estoque e " +
    "JOIN Produto p ON e.produtoId = p.id " +
    "WHERE p.descricao = :descricao " +
    "ORDER BY p.descricao ASC")
    List<Estoque> findbyDescricao(@Param("descricao") String descricao);    
}
