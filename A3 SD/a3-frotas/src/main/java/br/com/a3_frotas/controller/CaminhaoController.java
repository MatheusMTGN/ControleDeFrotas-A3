package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.service.CaminhaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    @PostMapping("/{placa}")
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
}
