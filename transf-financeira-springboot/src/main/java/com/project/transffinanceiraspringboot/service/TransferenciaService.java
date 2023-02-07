package com.project.transffinanceiraspringboot.service;

import com.project.transffinanceiraspringboot.dao.TransferenciaRepository;
import com.project.transffinanceiraspringboot.exception.RequestException;
import com.project.transffinanceiraspringboot.model.TransferenciaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaService {

    @Autowired
    TransferenciaRepository transferenciaRepo;

    public TransferenciaModel realizarTransferencia(TransferenciaModel transferencia) throws ParseException {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String dtAgendamento = sdf2.format(sdf1.parse(transferencia.dataAgendamento));
        transferencia.dataAgendamento = dtAgendamento;

        switch (transferencia.tipoTransacao) {
            case A:
            case B:
                transferencia.taxaTransf = calculaTaxaTipoAeB(transferencia.valorTransf, String.valueOf(transferencia.tipoTransacao));
                transferencia.valorTransf = transferencia.valorTransf - transferencia.taxaTransf;
            break;

            case C:
                calculaTaxaTipoC(transferencia);
                break;

            case D:
                if (transferencia.valorTransf <= 2000.00){
                    transferencia.taxaTransf = calculaTaxaTipoAeB(transferencia.valorTransf, String.valueOf(transferencia.tipoTransacao));
                    transferencia.valorTransf = transferencia.valorTransf - transferencia.taxaTransf;
                }else if (transferencia.valorTransf > 2000.00) {
                    calculaTaxaTipoC(transferencia);
                }
            break;

        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        transferencia.dataEfetuado = dtf.format(now);

        return transferenciaRepo.save(transferencia);
    }

    public List<TransferenciaModel> buscarTransferencias(){
        return transferenciaRepo.findAll();
    }

    public Optional<TransferenciaModel> buscarTransferencia(Long id){
        return transferenciaRepo.findById(id);
    }

    private Double calculaTaxaTipoAeB(double valorTransf, String tipo) {
        double valorTaxa = 0;
        if (tipo=="A" || (tipo == "D" && valorTransf <= 1000)){
            valorTaxa = (valorTransf*0.03)+3;
        }else if (tipo=="B" || (tipo == "D" && (valorTransf > 1000))){
            valorTaxa = 12;
        }
        return valorTaxa;
    }

    private TransferenciaModel calculaTaxaTipoC(TransferenciaModel transferencia){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dataHoje = LocalDateTime.parse(dtf.format(LocalDateTime.now()), dtf);
        LocalDateTime dataTransferencia = LocalDateTime.parse(transferencia.dataAgendamento+" 23:59:59", dtf);
        long qtdDias = Duration.between(dataHoje, dataTransferencia).toDays();

        if (qtdDias > 10 && qtdDias <= 20){
            transferencia.taxaTransf = (transferencia.valorTransf*0.082);
        }else if (qtdDias > 20 && qtdDias <= 30){
            transferencia.taxaTransf = (transferencia.valorTransf*0.069);
        }else if (qtdDias > 30 && qtdDias <= 40){
            transferencia.taxaTransf = (transferencia.valorTransf*0.047);
        }else if (qtdDias > 40){
            transferencia.taxaTransf = (transferencia.valorTransf*0.017);
        }else{
            throw new RequestException("Não existe taxa aplicável para operações de tipo D acima de R$2000 para menos de 10 dias!!");
        }
        transferencia.valorTransf = transferencia.valorTransf-transferencia.taxaTransf;
        return transferencia;
    }
}
