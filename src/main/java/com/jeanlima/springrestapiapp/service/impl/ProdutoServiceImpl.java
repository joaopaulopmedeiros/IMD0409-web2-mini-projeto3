package com.jeanlima.springrestapiapp.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.repository.ProdutoRepository;
import com.jeanlima.springrestapiapp.service.ProdutoService;
import com.jeanlima.springrestapiapp.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService 
{
    private final ProdutoRepository repository;
    private final Patcher<Produto> patcher;

    @Override
    public void patch(Integer id, Map<String, Object> fields) 
    {
        var produto = repository.findById(id);

        if (produto.isPresent())
        {
            patcher.patch(produto.get(), fields);
            repository.save(produto.get());
        }
    }
}
