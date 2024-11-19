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

    public Rota CadastrarRota(Rota rota) {
        Optional<Motorista> motorista = motoristaRepository.findById(rota.getMotorista().getId());
        if (motorista.isEmpty()) {
            throw new IllegalArgumentException("O motorista não foi encontrado");
        }

        Optional <Caminhao> caminhao = caminhaoRepository.findByPlaca(rota.getCaminhao().getPlaca());
        if (caminhao.isEmpty()) {
            throw new IllegalArgumentException("Caminhão não encontrado");
        }

        rota.setMotorista(motorista.get());
        rota.setCaminhao(caminhao.get());
        return rotaRepository.save(rota);
    }

    public List<Rota> listarRotas() {
        return rotaRepository.findAll();
    }

    public List <Rota> filtrarPorMotorista(Long motoristaId){
        return rotaRepository.findByMotoristaId(motoristaId);
    }

    public List<Rota> filtrarPorCaminhao(Long caminhaoPlaca){
        return rotaRepository.findByCaminhaoPlaca(caminhaoPlaca);
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
