package br.com.a3_frotas.controller;


import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.repository.MotoristaRepository;
import br.com.a3_frotas.service.CaminhaoService;
import br.com.a3_frotas.service.MotoristaService;
import br.com.a3_frotas.service.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Controller
@RequestMapping("/motoristas")
public class MotoristaController {

    private final MotoristaService motoristaService;
    private final CaminhaoService caminhaoService;
    private final RotaService rotaService;

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    public MotoristaController(MotoristaService motoristaService, CaminhaoService caminhaoService, RotaService rotaService) {

        this.motoristaService = motoristaService;
        this.caminhaoService = caminhaoService;
        this.rotaService = rotaService;
    }

    //Todos os métodos estão funcionando.

    // Cadastrar motorista sem caminhão - funcionando
    @PostMapping
    public ResponseEntity<?> cadastrarMotorista(@RequestBody Motorista motorista) {
        try {
            Motorista novoMotorista = motoristaService.cadastrarMotorista(motorista);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoMotorista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Motorista já cadastrado");
        }
    }

    // Vincular caminhão a um motorista existente - funcionando
    @PostMapping("/{motoristaId}/vincular-caminhao/{caminhaoId}")
    public ResponseEntity<?> vincularCaminhao(@PathVariable Long motoristaId, @PathVariable Long caminhaoId) {
        try {
            Motorista motoristaAtualizado = motoristaService.vincularCaminhao(motoristaId, caminhaoId);
            return ResponseEntity.status(HttpStatus.OK).body(motoristaAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Funcionando
    @GetMapping
    public ResponseEntity<List<Motorista>> listarMotoristas() {
        List<Motorista> motoristas = motoristaService.listarTodosOsMotoristas();
        return ResponseEntity.status(HttpStatus.OK).body(motoristas);
    }

    //Funcionando
    @GetMapping("/ativos")
    public ResponseEntity<List<Motorista>> listarMotoristasAtivos() {
        List<Motorista> motoristasAtivos = motoristaService.listarMotoristasAtivos();
        return ResponseEntity.status(HttpStatus.OK).body(motoristasAtivos);
    }

    //Funcionando
    @GetMapping("/{id}")
    public ResponseEntity<Motorista> buscarMotoristaPorId(@PathVariable Long id) {
        Motorista motoristaDetalhado = motoristaService.detalharMotorista(id);

        if (motoristaDetalhado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(motoristaDetalhado);
    }

    //Funcionando
    @PutMapping("/{id}")
    public ResponseEntity<Motorista> atualizarMotorista(@PathVariable Long id, @RequestBody Motorista motoristaAtualizado) {
        Motorista motoristaAtualizadoResponse = motoristaService.atualizarMotorista(id, motoristaAtualizado);

        if (motoristaAtualizadoResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(motoristaAtualizadoResponse);
    }

    //Funcionando
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMotorista(@PathVariable Long id) {
        try {
            motoristaService.deletarMotorista(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    //Funcionando
    @GetMapping("/api/detalhes/{id}")
    public ResponseEntity<?> detalhesMotorista(@PathVariable Long id) {
        try {
            Motorista motoristaDetalhado = motoristaService.detalharMotorista(id);
            return ResponseEntity.status(HttpStatus.OK).body(motoristaDetalhado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorista não encontrado");
        }
    }

    //Metodos thymeleaf

    @GetMapping("/add")
    public ModelAndView adicionarMotoristaHtml(@ModelAttribute Motorista motorista) {
        ModelAndView mv = new ModelAndView("cadastrar_motorista");
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView adicionarMotoristaHTMLPost(
            @ModelAttribute Motorista motorista) {

        ModelAndView mv = new ModelAndView();

        try {
            motoristaService.cadastrarMotorista(motorista);
            mv.setViewName("redirect:/home"); // Redireciona para a página inicial dos motoristas
        } catch (IllegalArgumentException e) {
            mv.setViewName("cadastrar_motorista"); // Retorna para a página de cadastro em caso de erro
            mv.addObject("errorMessage", "Erro ao cadastrar motorista. Verifique os dados.");
        }

        return mv;
    }

    @GetMapping("/motorista")
    public ModelAndView listarMotoristasHTML() {
        List<Motorista> motoristas = motoristaService.listarTodosOsMotoristas();
        ModelAndView mv = new ModelAndView("motoristas");
        mv.addObject("motoristas", motoristas);
        return mv;
    }

    // Thymeleaf - Exibe a página para editar um motorista específico
    @GetMapping("/editar/{id}")
    public ModelAndView editarMotoristaHTML(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("editar_motorista");
        Motorista motorista = motoristaService.detalharMotorista(id);

        if (motorista != null) {
            mv.addObject("motorista", motorista);
        } else {
            mv.addObject("errorMessage", "Motorista não encontrado.");
        }

        return mv;
    }

    // Thymeleaf - Lida com o envio do formulário de edição de motorista
    @PostMapping("/editar/{id}")
    public ModelAndView editarMotoristaHTMLPost(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String cpf,
            @RequestParam String dataNascimento,
            @RequestParam String cnh,
            @RequestParam String telefone,
            @RequestParam String email) {

        Motorista motoristaAtualizado = new Motorista();
        motoristaAtualizado.setNome(nome);
        motoristaAtualizado.setCpf(cpf);
        motoristaAtualizado.setDataNascimento(LocalDate.parse(dataNascimento));
        motoristaAtualizado.setCnh(cnh);
        motoristaAtualizado.setTelefone(telefone);
        motoristaAtualizado.setEmail(email);

        ModelAndView mv = new ModelAndView();

        try {
            Motorista motorista = motoristaService.atualizarMotorista(id, motoristaAtualizado);
            if (motorista != null) {
                mv.setViewName("redirect:/motoristas/motorista"); // Redireciona para a lista de motoristas
            } else {
                mv.setViewName("editar_motorista");
                mv.addObject("errorMessage", "Motorista não encontrado.");
            }
        } catch (Exception e) {
            mv.setViewName("editar_motorista");
            mv.addObject("errorMessage", "Erro ao atualizar o motorista. Verifique os dados.");
        }

        return mv;
    }

    @GetMapping("/inativar/{id}")
    public ModelAndView inativarMotoristaHTML(@PathVariable Long id, Model model) {
        ModelAndView mv = new ModelAndView();

        try {
            motoristaService.deletarMotorista(id);
            mv.setViewName("redirect:/motoristas/motorista");
        } catch (Exception e) {
            mv.setViewName("redirect:/motoristas/motorista");
            model.addAttribute("errorMessage", "Erro ao inativar o motorista.");
        }

        return mv;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalharMotorista(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("detalhes_motorista");
        try {
            Motorista motoristaDetalhado = motoristaService.detalharMotorista(id);
            mv.addObject("motorista", motoristaDetalhado);
        } catch (IllegalArgumentException e) {
            mv.setViewName("redirect:/motoristas/motorista");
            mv.addObject("errorMessage", "Motorista não encontrado.");
        }
        return mv;
    }

    @GetMapping("/vincular-caminhao/{id}")
    public ModelAndView vincularCaminhaoPagina(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("vincular_caminhao");
        mv.addObject("motoristaId", id);

        List<Caminhao> caminhaos = caminhaoService.listarTodosComMotoristas();
        mv.addObject("caminhoes", caminhaos);

        return mv;
    }

    @PostMapping("/{motoristaId}/vincular-caminhao")
    public ModelAndView vincularCaminhao(@PathVariable Long motoristaId, @RequestParam Long caminhaoId, Model model) {
        ModelAndView mv = new ModelAndView();

        try {
            motoristaService.vincularCaminhao(motoristaId, caminhaoId);
            mv.setViewName("redirect:/motoristas/motorista");
            model.addAttribute("successMessage", "Caminhão vinculado com sucesso!");
        } catch (Exception e) {
            mv.setViewName("redirect:/motoristas/motorista");
            model.addAttribute("errorMessage", "Erro ao vincular caminhão: " + e.getMessage());
        }

        return mv;
    }

    @GetMapping("/rotas")
    public ModelAndView listarRotasHTML() {
        List<Rota> rotas = rotaService.listarRotas();
        ModelAndView mv = new ModelAndView("rotas");
        mv.addObject("rotas", rotas);
        return mv;
    }



}
