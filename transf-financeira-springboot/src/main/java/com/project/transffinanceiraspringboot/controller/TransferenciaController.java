package com.project.transffinanceiraspringboot.controller;

import com.project.transffinanceiraspringboot.model.TransferenciaModel;
import com.project.transffinanceiraspringboot.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transferencia")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<TransferenciaModel> realizarTransferencia(@RequestBody TransferenciaModel transferencia) throws ParseException {
        transferenciaService.realizarTransferencia(transferencia);
        return ResponseEntity.ok(transferencia);
    }

    @GetMapping
    public ResponseEntity<List<TransferenciaModel>> buscarTransferencias(){
        var transferencias = transferenciaService.buscarTransferencias();
        return ResponseEntity.ok(transferencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TransferenciaModel>> buscarTransferencia(@PathVariable Long id){
        var transferencia = transferenciaService.buscarTransferencia(id);
        return ResponseEntity.ok(transferencia);
    }

}
