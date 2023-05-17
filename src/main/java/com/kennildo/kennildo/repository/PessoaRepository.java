package com.kennildo.kennildo.repository;

import com.kennildo.kennildo.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
