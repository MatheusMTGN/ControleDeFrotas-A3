package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.service.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rota")
public class RotaController {

    private final RotaService rotaService;

    @Autowired
    public RotaController(RotaService rotaService) {
        this.rotaService = rotaService;
    }

    @PostMapping
    public ResponseEntity<Rota> cadastrarRota(@RequestBody Rota rota) {
        Rota rotaCadastrada = rotaService.CadastrarRota(rota);
        return ResponseEntity.status(HttpStatus.CREATED).body(rotaCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Rota>> listarRotas(){
        List<Rota> rotas = rotaService.listarRotas();
        return ResponseEntity.ok(rotas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rota> atualizarRota(@PathVariable Long id, @RequestBody Rota rotaAtualizada) {
        Rota rotaAtualizadaResponse = rotaService.atualizarRota(id, rotaAtualizada);
        if(rotaAtualizadaResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(rotaAtualizadaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerRota(@PathVariable Long id) {
        rotaService.excluirRota(id);
        return ResponseEntity.noContent().build();
    }
}
