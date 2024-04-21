package com.jeanlima.springrestapiapp.rest.dto.resumo;

import java.util.List;

import com.jeanlima.springrestapiapp.rest.dto.ClienteDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ResumoPedidosClienteDTO 
{
    private ClienteDTO cliente;
    private List<ResumoPedidoDTO> pedidos;
}
