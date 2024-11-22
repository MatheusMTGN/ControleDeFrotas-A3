package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.service.MotoristaService;
import br.com.a3_frotas.service.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Rota rotaCadastrada = rotaService.cadastrarRota(novaRota);

        return ResponseEntity.status(HttpStatus.CREATED).body(rotaCadastrada);
    }


    //Funcionando
    @GetMapping
    public ResponseEntity<List<Rota>> listarRotas() {
        List<Rota> rotas = rotaService.listarRotas();
        return ResponseEntity.ok(rotas);
    }


    //Funcionando
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarRota(@PathVariable Long id, @RequestBody Rota rotaAtualizada) {
        try {
            Rota rotaAtualizadaResponse = rotaService.atualizarRota(id, rotaAtualizada);
            return ResponseEntity.ok(rotaAtualizadaResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a rota.");
        }
    }
    //Funcionando
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerRota(@PathVariable Long id) {
        rotaService.excluirRota(id);
        return ResponseEntity.noContent().build();
    }

    //Funcionando
    @GetMapping("/motorista/{motoristaId}")
    public ResponseEntity<List<Rota>> filtrarPorMotorista(@PathVariable Long motoristaId) {

        List<Rota> rotas = rotaService.filtrarPorMotorista(motoristaId);
        if (rotas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(rotas);

    }


}
