package com.jeanlima.springrestapiapp.rest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.rest.dto.AlteracaoQuantidadeEstoqueDTO;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.service.EstoqueService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/estoques")
public class EstoqueController 
{
    private final EstoqueService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estoque save(@RequestBody EstoqueDTO estoque)
    {
        return service.save(estoque);
    }
    
    @GetMapping
    public List<Estoque> filterbyDescricao(@RequestParam String descricao) 
    {
        return service.filterbyDescricao(descricao);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody AlteracaoQuantidadeEstoqueDTO dto){
        service.alterarQuantidadeEstoque(id, dto);
    }    
}
