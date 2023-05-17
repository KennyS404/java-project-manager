package com.kennildo.kennildo.model;

import java.time.LocalDate;

public class PessoaTestData {

    public static Pessoa criarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Hire Me dos Santos"); // no comments
        pessoa.setDataNascimento(LocalDate.of(1990, 5, 1));
        pessoa.setCpf("12345678900");
        pessoa.setFuncionario(true);
        return pessoa;
    }
}
