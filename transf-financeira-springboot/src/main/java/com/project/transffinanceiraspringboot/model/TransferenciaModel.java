package com.project.transffinanceiraspringboot.model;
import com.project.transffinanceiraspringboot.TipoTransacaoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "transferencias")
public class TransferenciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "contaOrigem")
    public String contaOrigem;

    @Column(name = "contaDestino")
    public String contaDestino;

    @Column(name = "valorTransf")
    public Double valorTransf;

    @Column(name = "taxaTransf")
    public Double taxaTransf;

    @Column(name = "dataAgendamento")
    public String dataAgendamento;

    @Column(name = "dataEfetuado")
    public String dataEfetuado;
    @Column(name = "tipoTransacao", columnDefinition = "varchar(22)")
    public TipoTransacaoEnum tipoTransacao;

}
