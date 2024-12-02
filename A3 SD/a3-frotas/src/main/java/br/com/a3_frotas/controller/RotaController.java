package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.service.MotoristaService;
import br.com.a3_frotas.service.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@RequestMapping("/rotas")
public class RotaController {

    private final RotaService rotaService;
    private final MotoristaService motoristaService;

    @Autowired
    public RotaController(RotaService rotaService, MotoristaService motoristaService) {
        this.rotaService = rotaService;
        this.motoristaService = motoristaService;
    }

    //Todos os métodos estão funcionando.

    /*Método atualizado com a regra de negócio: uma rota só pode ser cadastrada com um motorista (pelo ID) e o mesmo deve ter
    um caminhão vinculado.
    * */
    @PostMapping
    public ResponseEntity<Rota> cadastrarRota(@RequestBody Rota novaRota) {
        if (novaRota.getMotorista() == null || novaRota.getMotorista().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (novaRota.getPontoDePartida() == null || novaRota.getPontoDePartida().isEmpty() ||
                novaRota.getPontoDeChegada() == null || novaRota.getPontoDeChegada().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Motorista motorista = motoristaService.filtrarPorId(novaRota.getMotorista().getId());
        if (motorista == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Rota rotaCadastrada = rotaService.cadastrarRota(novaRota);

        return ResponseEntity.status(HttpStatus.CREATED).body(rotaCadastrada);
    }


    //Funcionando
    @GetMapping
    public ResponseEntity<List<Rota>> listarRotas() {
        List<Rota> rotas = rotaService.listarRotas();
        return ResponseEntity.status(HttpStatus.OK).body(rotas);
    }


    //Funcionando
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarRota(@PathVariable Long id, @RequestBody Rota rotaAtualizada) {
        try {
            Rota rotaAtualizadaResponse = rotaService.atualizarRota(id, rotaAtualizada);
            return ResponseEntity.ok(rotaAtualizadaResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rota inexistente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a rota.");
        }
    }
    //Funcionando
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerRota(@PathVariable Long id) {
        try{
            rotaService.excluirRota(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Funcionando
    @GetMapping("/motorista/{motoristaId}")
    public ResponseEntity<List<Rota>> filtrarPorMotorista(@PathVariable Long motoristaId) {

        List<Rota> rotas = rotaService.filtrarPorMotorista(motoristaId);
        if (rotas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(rotas);

    }


    //Thymeleaf metodos

    @GetMapping("/add")
    public ModelAndView adicionarRotaHtml() {
        ModelAndView mv = new ModelAndView("cadastrar_rota");
        List<Motorista> motoristas = motoristaService.listarTodosOsMotoristas();  // Lista motoristas disponíveis
        mv.addObject("motoristas", motoristas);
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView adicionarRotaHTMLPost(@RequestParam Long motoristaId, @RequestParam String pontoDePartida, @RequestParam String pontoDeChegada) {
        ModelAndView mv = new ModelAndView();

        try {
            Rota novaRota = new Rota();
            novaRota.setMotorista(motoristaService.filtrarPorId(motoristaId));
            novaRota.setPontoDePartida(pontoDePartida);
            novaRota.setPontoDeChegada(pontoDeChegada);

            if (novaRota.getMotorista() == null || novaRota.getMotorista().getId() == null) {
                throw new IllegalArgumentException("Motorista inválido");
            }

            rotaService.cadastrarRota(novaRota);
            mv.setViewName("redirect:/rotas/rotas");  // Redireciona para a lista de rotas
        } catch (Exception e) {
            mv.setViewName("cadastrar_rota");  // Retorna para o formulário em caso de erro
            mv.addObject("errorMessage", "Erro ao cadastrar rota. Verifique os dados.");
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

    @GetMapping("/editar/{id}")
    public ModelAndView editarRotaHtml(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("editar_rota");

        // Chame o método do serviço para buscar a rota
        Optional<Rota> optionalRota = rotaService.buscarRotaPorId(id);

        if (optionalRota.isPresent()) {
            Rota rota = optionalRota.get();
            mv.addObject("rota", rota);

            // Lista de motoristas
            List<Motorista> motoristas = motoristaService.listarTodosOsMotoristas();
            mv.addObject("motoristas", motoristas);
        } else {
            mv.addObject("errorMessage", "Rota não encontrada.");
        }

        return mv;
    }

    @PostMapping("/editar/{id}")
    public ModelAndView editarRotaPost(@PathVariable Long id, @RequestParam Long motoristaId, @RequestParam String pontoDePartida, @RequestParam String pontoDeChegada) {
        ModelAndView mv = new ModelAndView();

        try {
            Rota rotaAtualizada = new Rota();
            rotaAtualizada.setMotorista(motoristaService.filtrarPorId(motoristaId));
            rotaAtualizada.setPontoDePartida(pontoDePartida);
            rotaAtualizada.setPontoDeChegada(pontoDeChegada);

            rotaService.atualizarRota(id, rotaAtualizada);
            mv.setViewName("redirect:/rotas/rotas"); // Redireciona para a lista de rotas
        } catch (Exception e) {
            mv.setViewName("editar_rota");
            mv.addObject("errorMessage", "Erro ao atualizar a rota.");
        }

        return mv;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView removerRota(@PathVariable Long id, Model model) {
        ModelAndView mv = new ModelAndView("redirect:/rotas/rotas");

        try {
            Optional<Rota> rotaOptional = rotaService.buscarRotaPorId(id);

            if (rotaOptional.isPresent()) {
                Rota rota = rotaOptional.get();
                Motorista motorista = rota.getMotorista();

                if (motorista != null) {
                    motorista.getRotas().remove(rota);  // Remover a rota da lista de rotas do motorista
                    motoristaService.atualizarMotorista(motorista);  // Atualiza o motorista
                }

                rotaService.excluirRota(id);  // Agora, você pode excluir a rota sem problemas
                model.addAttribute("successMessage", "Rota removida com sucesso.");
            } else {
                model.addAttribute("errorMessage", "Rota não encontrada.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ocorreu um erro ao remover a rota.");
        }

        return mv;
    }

}






