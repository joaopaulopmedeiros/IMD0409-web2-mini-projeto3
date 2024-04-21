package com.jeanlima.springrestapiapp.rest.dto.resumo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumoPedidoDTO 
{
    private BigDecimal total;
    private List<ResumoItemDTO> items;
}
