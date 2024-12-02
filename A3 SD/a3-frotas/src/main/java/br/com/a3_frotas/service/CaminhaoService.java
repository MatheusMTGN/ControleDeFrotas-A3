package br.com.a3_frotas.service;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.repository.CaminhaoRepository;
import br.com.a3_frotas.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepository;

    private final MotoristaRepository motoristaRepository;

    @Autowired
    public CaminhaoService(CaminhaoRepository caminhaoRepository, MotoristaRepository motoristaRepository) {
        this.caminhaoRepository = caminhaoRepository;
        this.motoristaRepository = motoristaRepository;
    }

    public Caminhao adicionarCaminhao(Caminhao caminhao) {
        if (caminhao.getPlaca() == null || caminhao.getPlaca().isEmpty() ||
                caminhao.getModel() == null || caminhao.getModel().isEmpty() ||
                caminhao.getAno() == 0) {
            throw new IllegalArgumentException("Placa, modelo e ano são obrigatórios");
        }

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


    public List<Caminhao> listarCaminhoesDisponiveis() {
        // Lista todos os caminhões
        List<Caminhao> todosCaminhoes = caminhaoRepository.findAll();

        // Lista os IDs dos caminhões já vinculados a motoristas
        List<Long> caminhaoIdsVinculados = motoristaRepository.findAll()
                .stream()
                .filter(motorista -> motorista.getCaminhao() != null)
                .map(motorista -> motorista.getCaminhao().getId())
                .collect(Collectors.toList());

        // Retorna apenas os caminhões que não estão vinculados
        return todosCaminhoes.stream()
                .filter(caminhao -> !caminhaoIdsVinculados.contains(caminhao.getId()))
                .collect(Collectors.toList());
    }

    public List<Caminhao> listarTodosComMotoristas() {
        return caminhaoRepository.findAll();
    }

}
