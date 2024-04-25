package com.jeanlima.springrestapiapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.repository.EstoqueRepository;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.service.EstoqueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService 
{
    private final EstoqueRepository repository;

    @Override
    public Estoque save(EstoqueDTO input) 
    {
        Estoque entity = Estoque
        .builder()
        .produtoId(input.getProdutoId())
        .quantidade(input.getQuantidade())
        .build();

        return repository.save(entity);
    }

    @Override
    public List<Estoque> filterByProductName(String nomeProduto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterByProductName'");
    }
}
