package com.jeanlima.springrestapiapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jeanlima.springrestapiapp.exception.RegraNegocioException;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.repository.EstoqueRepository;
import com.jeanlima.springrestapiapp.rest.dto.AlteracaoQuantidadeEstoqueDTO;
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
    public List<Estoque> filterbyDescricao(String descricao) {
        return repository.findbyDescricao(descricao);
    }

    @Override
    public void alterarQuantidadeEstoque(Integer id, AlteracaoQuantidadeEstoqueDTO dto) {
        var estoque = repository.findById(id);

        if (!estoque.isPresent()) throw new RegraNegocioException("Id não existente para estoque");

        if (dto.getQuantidade() < 0) throw new RegraNegocioException("Quantidade não pode ser negativa");

        estoque.get().setQuantidade(dto.getQuantidade());

        repository.save(estoque.get());
    }

    @Override
    public boolean verificarDisponibilidade(Integer produto, Integer quantidade) 
    {
        var estoque = repository.findbyIdProduto(produto);
        return estoque != null && estoque.getQuantidade() >= quantidade;
    }

    @Override
    public void alterarQuantidadeEstoquePorIdProduto(Integer produto, Integer quantidade) 
    {
        var estoque = repository.findbyIdProduto(produto);

        if (estoque != null) 
        {
            var quantidadeAtual = estoque.getQuantidade() - quantidade;
            estoque.setQuantidade(quantidadeAtual);
            repository.save(estoque);
        }
    }
}
