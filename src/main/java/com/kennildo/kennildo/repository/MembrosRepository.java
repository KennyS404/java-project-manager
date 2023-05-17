package com.kennildo.kennildo.repository;

import com.kennildo.kennildo.model.Membros;
import com.kennildo.kennildo.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembrosRepository extends JpaRepository<Membros, Long> {
    List<Membros> findByProjeto(Projeto projeto);
}
