package com.jeanlima.springrestapiapp.service.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import com.jeanlima.springrestapiapp.exception.PedidoNaoEncontradoException;
import com.jeanlima.springrestapiapp.exception.RegraNegocioException;
import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.model.ItemPedido;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.repository.ClienteRepository;
import com.jeanlima.springrestapiapp.repository.ItemPedidoRepository;
import com.jeanlima.springrestapiapp.repository.PedidoRepository;
import com.jeanlima.springrestapiapp.repository.ProdutoRepository;
import com.jeanlima.springrestapiapp.rest.dto.ClienteDTO;
import com.jeanlima.springrestapiapp.rest.dto.ItemPedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.resumo.ResumoItemDTO;
import com.jeanlima.springrestapiapp.rest.dto.resumo.ResumoPedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.resumo.ResumoPedidosClienteDTO;
import com.jeanlima.springrestapiapp.service.EstoqueService;
import com.jeanlima.springrestapiapp.service.PedidoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    
    private final PedidoRepository repository;
    private final ClienteRepository clientesRepository;
    private final ProdutoRepository produtosRepository;
    private final ItemPedidoRepository itemsPedidoRepository;
    private final EstoqueService estoqueService;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = obterCliente(idCliente);

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());

        pedido.setTotal(calcularTotal(itemsPedido));

        verificarDisponibilidadeEstoque(dto.getItems());
        atualizarEstoque(dto.getItems());

        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    private void verificarDisponibilidadeEstoque(List<ItemPedidoDTO> items) 
    {
        for (ItemPedidoDTO item : items) {
            if (!estoqueService.verificarDisponibilidade(item.getProduto(), item.getQuantidade())) {
                throw new RegraNegocioException("O estoque do produto " + item.getProduto() + " está esgotado.");
            }
        }
    }

    private void atualizarEstoque(List<ItemPedidoDTO> items) {
        for (ItemPedidoDTO item : items) 
        {
            estoqueService.alterarQuantidadeEstoquePorIdProduto(item.getProduto(), item.getQuantidade());
        }
    }

    private BigDecimal calcularTotal(List<ItemPedido> itemsPedido) 
    {
        BigDecimal total = BigDecimal.ZERO;
        
        for (ItemPedido item : itemsPedido) 
        {
            BigDecimal precoItem = item.getProduto().getPreco(); 
            int quantidadeItem = item.getQuantidade();
            BigDecimal subtotalItem = precoItem.multiply(BigDecimal.valueOf(quantidadeItem));
            total = total.add(subtotalItem);
        }
        
        return total;
    }
    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }
    @Override
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
        .findById(id)
        .map( pedido -> {
            pedido.setStatus(statusPedido);
            return repository.save(pedido);
        }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }

    @Override
    public ResumoPedidosClienteDTO getResumoPedidos(Integer idCliente) {
        Cliente cliente = obterCliente(idCliente);

        ClienteDTO clienteDTO = ClienteDTO.builder()
        .id(cliente.getId())
        .nome(cliente.getNome())
        .build();

        List<Pedido> pedidos = repository.findByCliente(cliente);        
                
        return ResumoPedidosClienteDTO.builder()
        .cliente(clienteDTO)
        .pedidos(converterPedidos(pedidos))
        .build();
    }

    private Cliente obterCliente(Integer idCliente) {
        return clientesRepository
        .findById(idCliente)
        .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));
    }

    private List<ResumoPedidoDTO> converterPedidos(List<Pedido> pedidos) {
        return pedidos
                .stream()
                .map(pedido -> {
                    var dto = new ResumoPedidoDTO();
                    dto.setItems(converterItems(pedido.getItens()));
                    dto.setTotal(pedido.getTotal());
                    return dto;
                }).collect(Collectors.toList());        
    }
    
    private List<ResumoItemDTO> converterItems(List<ItemPedido> itens) {
        return itens
                .stream()
                .map(item -> {
                    var dto = new ResumoItemDTO();
                    dto.setDescricao(item.getProduto().getDescricao());                    
                    return dto;
                })
                .collect(Collectors.toList());
    }
 
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Integer id, PedidoDTO dto) 
    {
        Cliente cliente = obterCliente(dto.getCliente());      
        
        Optional<Pedido> pedidoBuscado = repository.findById(id);

        if (pedidoBuscado.isPresent()) 
        {
            Pedido pedido = pedidoBuscado.get();

            pedido.setCliente(cliente);

            List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
            pedido.setTotal(calcularTotal(itemsPedido));

            repository.save(pedido);
        }
    }
}
