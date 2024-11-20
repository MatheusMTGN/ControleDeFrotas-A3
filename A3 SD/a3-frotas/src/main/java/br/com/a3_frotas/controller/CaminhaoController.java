package br.com.a3_frotas.controller;


import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.service.CaminhaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caminhoes")
public class CaminhaoController {

    private final CaminhaoService caminhaoService;

    @Autowired
    public CaminhaoController(CaminhaoService caminhaoService) {
        this.caminhaoService = caminhaoService;
    }

    @PostMapping
    public ResponseEntity<Caminhao> adicionarCaminhao(@RequestBody Caminhao caminhao) {
        Caminhao caminhaoAdicionado = caminhaoService.adicionarCaminhao(caminhao);
        return ResponseEntity.status(HttpStatus.CREATED).body(caminhaoAdicionado);
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Caminhao> buscarCaminhao(@PathVariable String placa) {
        Caminhao caminhao = caminhaoService.filtrarPorPlaca(placa);
        if (caminhao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(caminhao);
    }

    @PutMapping("/{placa}")
    public ResponseEntity<Caminhao> atualizarCaminhao(@PathVariable String placa, @RequestBody Caminhao caminhaoAtualizado) {
        Caminhao caminhaoAtualizadoResponse = caminhaoService.atualizarCaminhao(placa, caminhaoAtualizado);
        if(caminhaoAtualizadoResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(caminhaoAtualizadoResponse);
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> removerCaminhao(@PathVariable String placa) {
        caminhaoService.removerCaminhao(placa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
