package br.com.a3_frotas.service;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.repository.CaminhaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaminhaoService {
    private final CaminhaoRepository caminhaoRepository;

    @Autowired
    public CaminhaoService(CaminhaoRepository caminhaoRepository) {
        this.caminhaoRepository = caminhaoRepository;
    }

    public Caminhao adicionarCaminhao(Caminhao caminhao) {
        Optional<Caminhao> caminhaoExistente = caminhaoRepository.findByPlaca(caminhao.getPlaca());
        if (caminhaoExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um veículo adicionado com esta placa");
        }
        return caminhaoRepository.save(caminhao);
    }

    public Caminhao filtrarPorPlaca(String placa) {
        return caminhaoRepository.findByPlaca(placa).orElse(null);
    }

    public Caminhao atualizarCaminhao(String placa, Caminhao caminhaoAtualizado) {
        Caminhao caminhao = filtrarPorPlaca(placa);

        if (caminhao == null) {
            throw new IllegalArgumentException("Não há nenhum caminhão cadastrado com esta placa.");
        }

        if (caminhaoAtualizado.getModel() != null) {
            caminhao.setModel(caminhaoAtualizado.getModel());
        }
        if (caminhaoAtualizado.getAno() != 0) {
            caminhao.setAno(caminhaoAtualizado.getAno());
        }

        return caminhaoRepository.save(caminhao);
    }
    public void removerCaminhao(String placa) {
        Caminhao caminhao = filtrarPorPlaca(placa);
        if (caminhao == null) {
            throw new IllegalArgumentException("Não há nenhum caminhão cadastrado com esta placa.");
        }

        caminhaoRepository.delete(caminhao);
    }

    public List<Caminhao> listarTodosOsCaminhoes() {
        return caminhaoRepository.findAll();
    }
}
