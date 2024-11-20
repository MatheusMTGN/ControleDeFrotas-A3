package br.com.a3_frotas.controller;


import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    private final MotoristaService motoristaService;

    @Autowired
    public MotoristaController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @PostMapping
    public ResponseEntity<Motorista> cadastrarMotorista(@RequestBody Motorista motorista){
        Motorista motoristaCadastro = motoristaService.cadastrarMotorista(motorista);
        return ResponseEntity.status(HttpStatus.CREATED).body(motoristaCadastro);
    }

    @GetMapping
    public ResponseEntity<List<Motorista>> listarMotoristas(){
        List<Motorista> motoristas = motoristaService.listarTodosOsMotoristas();
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Motorista>> listasMotoristasAtivos(){
        List<Motorista> motoristasAtivos = motoristaService.listarMotoristasAtivos();
        return ResponseEntity.ok(motoristasAtivos);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Motorista> buscarMotoristaPorId(@PathVariable Long id){
        Motorista motoristaDetalhado = motoristaService.detalharMotorista(id);

        if(motoristaDetalhado == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(motoristaDetalhado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorista> atualizarMotorista(@PathVariable Long id, @RequestBody Motorista motoristaAtualizado){
       Motorista motoristaAtualizadoResponse = motoristaService.atualizarMotorista(id,motoristaAtualizado);

       if(motoristaAtualizadoResponse == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.ok(motoristaAtualizadoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMotorista(@PathVariable Long id){
        motoristaService.deletarMotorista(id);
        return ResponseEntity.noContent().build();
    }
}
