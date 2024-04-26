package com.jeanlima.springrestapiapp.service;

import java.util.Optional;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.resumo.ResumoPedidosClienteDTO;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto );
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
    ResumoPedidosClienteDTO getResumoPedidos(Integer idCliente);
    void delete(Integer id);
    void update(Integer id, PedidoDTO dto);   
}
