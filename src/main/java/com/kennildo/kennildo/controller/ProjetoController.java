package com.kennildo.kennildo.controller;

import com.kennildo.kennildo.model.Membros;
import com.kennildo.kennildo.model.Pessoa;
import com.kennildo.kennildo.model.Projeto;
import com.kennildo.kennildo.model.PessoaTestData;
import com.kennildo.kennildo.repository.MembrosRepository;
import com.kennildo.kennildo.repository.PessoaRepository;
import com.kennildo.kennildo.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projeto")
public class ProjetoController {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;


    @Autowired
    private MembrosRepository membrosRepository;

    @GetMapping("/")
    public String listarProjetos(Model model) {
        Pessoa pessoa = PessoaTestData.criarPessoa();
        pessoaRepository.save(pessoa); // aqui eu deixei pra criar um user manualmente para teste
        List<Projeto> projetos = projetoRepository.findAll();
        model.addAttribute("projetos", projetos);
        return "projeto/listar";
    }

    @GetMapping("/criar")
    public String exibirFormulario(Model model) {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        model.addAttribute("projeto", new Projeto());
        model.addAttribute("pessoas", pessoas);
        return "projeto/criar";
    }


    @PostMapping("/criar")
    public String salvarProjeto(@ModelAttribute Projeto projeto) {
        projetoRepository.save(projeto);
        return "redirect:/projeto/";
    }
//    @PostMapping("/addPessoa")
//    public String adicionarPessoa(@ModelAttribute Pessoa pessoa) {
//        pessoaRepository.save(pessoa);
//        return "redirect:/projeto/criar";
//    }


    @GetMapping("/editar/{id}")
    public String editarProjeto(@PathVariable("id") Long id, Model model) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto inválido com ID: " + id));
        List<Pessoa> pessoas = pessoaRepository.findAll();
        model.addAttribute("projeto", projeto);
        model.addAttribute("pessoas", pessoas);
        return "projeto/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarProjeto(@PathVariable("id") Long id, @ModelAttribute Projeto projeto) {
        projeto.setId(id);
        projetoRepository.save(projeto);
        return "redirect:/projeto/";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProjeto(@PathVariable("id") Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto inválido com ID: " + id));

        if (projeto.getStatus().equalsIgnoreCase("iniciado") ||
                projeto.getStatus().equalsIgnoreCase("em andamento") ||
                projeto.getStatus().equalsIgnoreCase("encerrado")) {
            return "redirect:/projeto/";
        }

        projetoRepository.deleteById(id);
        return "redirect:/projeto/";
    }

    @GetMapping("/{id}/membros")
    public String listarMembros(@PathVariable("id") Long id, Model model) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto inválido com ID: " + id));

        List<Membros> membros = membrosRepository.findByProjeto(projeto);
        model.addAttribute("projeto", projeto);
        model.addAttribute("membros", membros);
        return "projeto/listar_membros";
    }

    @PostMapping("/{id}/membros")
    public String adicionarMembro(@PathVariable("id") Long id, @RequestParam("pessoaId") Long pessoaId) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto inválido com ID: " + id));

        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa inválida com ID: " + pessoaId));

        Membros membro = new Membros();
        membro.setProjeto(projeto);
        membro.setPessoa(pessoa);
        membrosRepository.save(membro);
        return "redirect:/projeto/" + id + "/membros";
    }

    @GetMapping("/{id}/membros/remover/{membroId}")
    public String removerMembro(@PathVariable("id") Long id, @PathVariable("membroId") Long membroId) {
        membrosRepository.deleteById(membroId);
        return "redirect:/projeto/" + id + "/membros";
    }
}
