package com.jeanlima.springrestapiapp.rest.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResumoPedidosClienteDTO 
{
    private ClienteDTO cliente;
    private List<PedidoDTO> pedidos;
}
