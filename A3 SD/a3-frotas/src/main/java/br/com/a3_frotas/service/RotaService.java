package br.com.a3_frotas.service;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.repository.CaminhaoRepository;
import br.com.a3_frotas.repository.MotoristaRepository;
import br.com.a3_frotas.repository.RotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RotaService {
    private final RotaRepository rotaRepository;
    private final MotoristaRepository motoristaRepository;
    private final CaminhaoRepository caminhaoRepository;


    @Autowired
    public RotaService(RotaRepository rotaRepository, MotoristaRepository motoristaRepository, CaminhaoRepository caminhaoRepository) {
        this.rotaRepository = rotaRepository;
        this.motoristaRepository = motoristaRepository;
        this.caminhaoRepository = caminhaoRepository;
    }

    public Rota cadastrarRota(String origem, String destino, Long motoristaId, String placaCaminhao) {

        Motorista motorista = motoristaRepository.findById(motoristaId)
                .orElseThrow(() -> new IllegalArgumentException("Motorista com ID não encontrado."));

        Caminhao caminhao = caminhaoRepository.findByPlaca(placaCaminhao)
                .orElseThrow(() -> new IllegalArgumentException("Caminhão com placa não encontrado."));

        Rota rota = new Rota();
        rota.setPontoDePartida(origem);
        rota.setPontoDeChegada(destino);
        rota.setMotorista(motorista);
        rota.setCaminhao(caminhao);

        return rotaRepository.save(rota);
    }

    public List<Rota> listarRotas() {
        return rotaRepository.findAll();
    }

    public List <Rota> filtrarPorMotorista(Long motoristaId){
        return rotaRepository.findByMotoristaId(motoristaId);
    }


    public void excluirRota(Long id){
        Optional<Rota> rota = rotaRepository.findById(id);
        if(rota.isPresent()){
            rotaRepository.delete(rota.get());
        }else{
            throw new IllegalArgumentException("Nenhuma rota foi encontrada com este ID.");
        }
    }
    public Rota atualizarRota(Long id, Rota rotaAtualizada) {
        Optional<Rota> rota = rotaRepository.findById(id);
        if(rota.isEmpty()){
            throw new IllegalArgumentException("Nenhuma rota foi encontrada com este ID.");
        }

        Rota rotaExistente = rota.get();

        if(rotaAtualizada.getPontoDePartida() != null){
            rotaExistente.setPontoDePartida(rotaAtualizada.getPontoDePartida());
        }

        if(rotaAtualizada.getPontoDeChegada() != null){
            rotaExistente.setPontoDeChegada(rotaAtualizada.getPontoDeChegada());
        }

        return rotaRepository.save(rotaExistente);
    }


}
