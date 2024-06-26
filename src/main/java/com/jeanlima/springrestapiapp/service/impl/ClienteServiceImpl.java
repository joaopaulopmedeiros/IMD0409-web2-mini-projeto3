package com.jeanlima.springrestapiapp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.repository.ClienteRepository;
import com.jeanlima.springrestapiapp.service.ClienteService;
import com.jeanlima.springrestapiapp.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final Patcher<Cliente> patcher;

    @Override
    public Cliente getById(Integer id) {
        return repository
        .findById(id)
        .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @Override
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
        .map(cliente -> {
            repository.delete(cliente );
            return cliente;
        })
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado") );
    }

    @Override
    public void update(Integer id, Cliente cliente) {
        repository
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    repository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cliente não encontrado") );
    }

    @Override
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        
        return repository.findAll(example);
    }

    @Override
    public void patch(Integer id, Map<String, Object> fields) {
        var cliente = repository.findById(id);

        if (cliente.isPresent())
        {
            patcher.patch(cliente.get(), fields);
            repository.save(cliente.get());
        }
    }
}
