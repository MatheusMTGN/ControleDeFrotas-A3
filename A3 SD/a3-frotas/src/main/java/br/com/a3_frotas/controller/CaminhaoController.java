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
        try{
            caminhaoService.removerCaminhao(placa);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Caminhao>> listarCaminhoes() {
        List<Caminhao> caminhaos = caminhaoService.listarTodosOsCaminhoes();
        return ResponseEntity.status(HttpStatus.OK).body(caminhaos);
    }

    //thymeleaf

    @GetMapping("/list")
    public ModelAndView listarCaminhoesHTML() {
        List<Caminhao> caminhaos = caminhaoService.listarTodosOsCaminhoes();
        ModelAndView listarcaminhao = new ModelAndView("home");
        listarcaminhao.addObject("caminhoes", caminhaos);
        return listarcaminhao;
    }

    // Página para adicionar um caminhão
    @GetMapping("/novo")
    public String novoCaminhaoForm(Model model) {
        model.addAttribute("caminhao", new Caminhao());
        return "caminhoes";
    }

    @PostMapping("/add")
    public String adicionarCaminhaoHTML(@ModelAttribute Caminhao caminhao, Model model) {
        try {
            caminhaoService.adicionarCaminhao(caminhao);
            return "redirect:/caminhoes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", "Caminhão já existente ou dados inválidos.");
            return "redirect:/caminhoes";
        }
    }

    // Página para editar um caminhão existente
    @GetMapping("/editar/{placa}")
    public String editarCaminhaoForm(@PathVariable String placa, Model model) {
        Caminhao caminhao = caminhaoService.filtrarPorPlaca(placa);
        if (caminhao == null) {
            model.addAttribute("erro", "Caminhão não encontrado.");
            return "redirect:/caminhoes";
        }
        model.addAttribute("caminhao", caminhao);
        return "redirect:/caminhoes"; // Reutiliza o formulário
    }

    @PostMapping("/editar/{placa}")
    public String atualizarCaminhaoHTML(@PathVariable String placa, @ModelAttribute Caminhao caminhao, Model model) {
        try {
            caminhaoService.atualizarCaminhao(placa, caminhao);
            return "redirect:/caminhoes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", "Erro ao atualizar o caminhão.");
            return "redirect:/caminhoes";
        }
    }

    // Excluir um caminhão
    @GetMapping("/remover/{placa}")
    public String removerCaminhaoHTML(@PathVariable String placa, Model model) {
        try {
            caminhaoService.removerCaminhao(placa);
            return "redirect:/caminhoes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", "Erro ao remover o caminhão.");
            return "redirect:/caminhoes";
        }
    }
}
