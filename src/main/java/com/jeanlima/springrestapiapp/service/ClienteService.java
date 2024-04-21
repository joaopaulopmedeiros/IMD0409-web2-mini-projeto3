package com.jeanlima.springrestapiapp.service;

import java.util.List;

import com.jeanlima.springrestapiapp.model.Cliente;

public interface ClienteService 
{
    Cliente getById(Integer id);

    Cliente save(Cliente cliente);

    void delete(Integer id);

    void update(Integer id, Cliente cliente);

    List<Cliente> find(Cliente filtro);

    void patch(Integer id, Cliente cliente);
}
