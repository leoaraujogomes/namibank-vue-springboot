package com.project.transffinanceiraspringboot.dao;

import com.project.transffinanceiraspringboot.model.TransferenciaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<TransferenciaModel, Long> {
}
