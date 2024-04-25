package com.jeanlima.springrestapiapp.service;

import java.util.List;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;

public interface EstoqueService 
{
    Estoque save(EstoqueDTO estoque);

    List<Estoque> filterbyDescricao(String descricao);
}
