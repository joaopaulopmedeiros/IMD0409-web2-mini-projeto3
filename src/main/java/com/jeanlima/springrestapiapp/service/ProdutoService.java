package com.jeanlima.springrestapiapp.service;

import java.util.Map;

public interface ProdutoService 
{
    void patch(Integer id, Map<String, Object> fields);   
}