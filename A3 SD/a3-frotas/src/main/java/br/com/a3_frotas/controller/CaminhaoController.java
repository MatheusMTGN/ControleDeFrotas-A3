package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.service.CaminhaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RestController
@RequestMapping("/caminhoes")
public class CaminhaoController {

    private final CaminhaoService caminhaoService;

    @Autowired
    public CaminhaoController(CaminhaoService caminhaoService) {
        this.caminhaoService = caminhaoService;
    }

    //Todos os métodos estão funcionando.

    @PostMapping
    public ResponseEntity<?> adicionarCaminhao(@RequestBody Caminhao caminhao) {
        if (caminhao.getPlaca() == null || caminhao.getPlaca().isEmpty() ||
                caminhao.getModel() == null || caminhao.getModel().isEmpty() ||
                caminhao.getAno() == 0) {
            return ResponseEntity.badRequest().body("Placa, modelo e ano são obrigatórios");
        }

        try {
            Caminhao caminhaoAdicionado = caminhaoService.adicionarCaminhao(caminhao);
            return ResponseEntity.status(HttpStatus.CREATED).body(caminhaoAdicionado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Caminhão já existente.");
        }
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Caminhao> buscarCaminhao(@PathVariable String placa) {
        Caminhao caminhao = caminhaoService.filtrarPorPlaca(placa);
        if (caminhao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(caminhao);
    }


    @PutMapping("/{placa}")
    public ResponseEntity<Caminhao> atualizarCaminhao(@PathVariable String placa, @RequestBody Caminhao caminhaoAtualizado) {
        Caminhao caminhaoAtualizadoResponse = caminhaoService.atualizarCaminhao(placa, caminhaoAtualizado);
        if (caminhaoAtualizadoResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(caminhaoAtualizadoResponse);
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> removerCaminhao(@PathVariable String placa) {
        try {
            caminhaoService.removerCaminhao(placa);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Caminhao>> listarCaminhoes() {
        List<Caminhao> caminhaos = caminhaoService.listarTodosOsCaminhoes();
        return ResponseEntity.status(HttpStatus.OK).body(caminhaos);
    }

    //thymeleaf

    //Cadastrar_caminhao
    @GetMapping("/add")
    public ModelAndView adicionarCaminhaoHTML(@ModelAttribute Caminhao caminhao, Model model) {
        ModelAndView adicionar = new ModelAndView("cadastrar_caminhao");
        return adicionar;
    }

    @PostMapping("/add")
    public ModelAndView adicionarCaminhaoHTMLPost(@RequestParam String placa, @RequestParam String model, @RequestParam int ano) {
        Caminhao caminhao = new Caminhao();
        caminhao.setAno(ano);
        caminhao.setPlaca(placa);
        caminhao.setModel(model);
        ModelAndView mv = new ModelAndView();

        try {
            caminhaoService.adicionarCaminhao(caminhao);
            // Redireciona para "home.html" em caso de sucesso
            mv.setViewName("redirect:/caminhoes/caminhao");
        } catch (IllegalArgumentException e) {
            // Redireciona de volta para "cadastrar_caminhao.html" com uma mensagem de erro
            mv.setViewName("redirect:/caminhoes/caminhao");
            mv.addObject("errorMessage", "Erro ao cadastrar caminhão. Verifique os dados.");
        }
        return mv;
    }

    // Página Caminhoes.html

    @GetMapping("/caminhao")
    public ModelAndView listarCaminhoesCaminhao() {
        List<Caminhao> caminhaos = caminhaoService.listarTodosOsCaminhoes();
        ModelAndView listarcaminhaocaminhoes = new ModelAndView("caminhoes");
        listarcaminhaocaminhoes.addObject("caminhoes", caminhaos);
        return listarcaminhaocaminhoes;
    }

    @GetMapping("/editar/{placa}")
    public ModelAndView atualizarCaminhaoPagina(@PathVariable String placa) {
        ModelAndView mv = new ModelAndView("editar_caminhao");
        mv.addObject("placa", placa);
        return mv;
    }

    @PostMapping("/editar/{placa}")
    public ModelAndView atualizarCaminhaoPagina(@PathVariable String placa, @RequestParam String model, @RequestParam int ano) {
        ModelAndView mv = new ModelAndView();
        Caminhao caminhaoAtualizado = new Caminhao();
        caminhaoAtualizado.setAno(ano);
        caminhaoAtualizado.setModel(model);
        System.out.println(caminhaoAtualizado.toString());
        try {
            Caminhao caminhaoAtualizadoResponse = caminhaoService.atualizarCaminhao(placa, caminhaoAtualizado);
            if (caminhaoAtualizadoResponse == null) {
                mv.setViewName("editar_caminhao"); // Volta para a página de edição
                mv.addObject("errorMessage", "Caminhão com placa " + placa + " não encontrado.");
                System.out.println("Erro1");
            } else {
                mv.setViewName("redirect:/caminhoes/caminhao"); // Redireciona para a lista de caminhões
                mv.addObject("successMessage", "Caminhão atualizado com sucesso!");
            }
        } catch (Exception e) {
            mv.setViewName("editar_caminhao");
            mv.addObject("errorMessage", "Erro ao atualizar o caminhão. Por favor, tente novamente.");
            System.out.println("Erro2");
        }

        return mv;
    }

    // Excluir um caminhão
    @GetMapping("/remover/{placa}")
    public ModelAndView removerCaminhaoHTML(@PathVariable String placa, Model model) {
            ModelAndView mv = new ModelAndView();
        try {
            caminhaoService.removerCaminhao(placa);
            mv.setViewName("redirect:/caminhoes/caminhao");
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", "Erro ao remover o caminhão.");
            mv.setViewName("redirect:/caminhoes/caminhao");
        }
        return mv;
    }
}

