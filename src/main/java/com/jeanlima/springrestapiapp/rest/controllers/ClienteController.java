package com.jeanlima.springrestapiapp.rest.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.service.ClienteService;

import lombok.RequiredArgsConstructor;



@RequestMapping("/api/clientes")
@RestController 
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping("{id}")
    public Cliente getById(@PathVariable Integer id){
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente){
        return service.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Cliente cliente ){
        service.update(id, cliente);
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro){
        return service.find(filtro);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody Map<String, Object> fields){
        service.patch(id, fields);
    }
}
