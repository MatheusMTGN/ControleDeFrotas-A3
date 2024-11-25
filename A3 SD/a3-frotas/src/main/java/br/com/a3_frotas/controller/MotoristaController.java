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

    //Todos os métodos estão funcionando.

    // Cadastrar motorista sem caminhão - funcionando
    @PostMapping
    public ResponseEntity<?> cadastrarMotorista(@RequestBody Motorista motorista) {
        try{
            Motorista novoMotorista = motoristaService.cadastrarMotorista(motorista);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoMotorista);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Motorista já cadastrado");
        }
    }

    // Vincular caminhão a um motorista existente - funcionando
    @PostMapping("/{motoristaId}/vincular-caminhao/{caminhaoId}")
    public ResponseEntity<?> vincularCaminhao(@PathVariable Long motoristaId, @PathVariable Long caminhaoId) {
        try{
            Motorista motoristaAtualizado = motoristaService.vincularCaminhao(motoristaId, caminhaoId);
            return ResponseEntity.status(HttpStatus.OK).body(motoristaAtualizado);
        }catch (Exception e){
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
        try{
            motoristaService.deletarMotorista(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    //Funcionando
    @GetMapping("/detalhes/{id}")
    public ResponseEntity<?> detalhesMotorista(@PathVariable Long id) {
        try {
            Motorista motoristaDetalhado = motoristaService.detalharMotorista(id);
            return ResponseEntity.status(HttpStatus.OK).body(motoristaDetalhado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorista não encontrado");
        }
    }



}
