package com.jeanlima.springrestapiapp.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClienteDTO 
{
    private Integer id;
    private String nome;
}
