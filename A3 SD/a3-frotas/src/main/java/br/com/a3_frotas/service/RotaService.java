package br.com.a3_frotas.service;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.repository.CaminhaoRepository;
import br.com.a3_frotas.repository.MotoristaRepository;
import br.com.a3_frotas.repository.RotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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


    public Rota cadastrarRota(Rota novaRota) {
        if (novaRota.getPontoDePartida() == null || novaRota.getPontoDeChegada() == null) {
            throw new IllegalArgumentException("Os pontos de partida e chegada são obrigatórios.");
        }
        Motorista motorista = motoristaRepository.findById(novaRota.getMotorista().getId())
                .orElseThrow(() -> new IllegalArgumentException("Motorista com ID não encontrado."));

        if(!motorista.getAtivo() || motorista.getCaminhao() == null){
                throw new IllegalArgumentException("O motorista não está ativo ou não tem um caminhão");
        }

        Rota rota = new Rota();
        rota.setPontoDePartida(novaRota.getPontoDePartida());
        rota.setPontoDeChegada(novaRota.getPontoDeChegada());
        rota.setMotorista(motorista);

        return rotaRepository.save(rota);
    }


    public List<Rota> listarRotas() {
        return rotaRepository.findAll();
    }

    public List <Rota> filtrarPorMotorista(Long motoristaId){
        if(motoristaId ==null){
            throw new IllegalArgumentException("O ID do motorista não pode ser nulo");
        }
        List<Rota> rotas = rotaRepository.findByMotoristaId(motoristaId);

        if(rotas.isEmpty()){
            return null;
        }
        return rotas;
    }


    public void excluirRota(Long id) {
        Optional<Rota> rotaOptional = rotaRepository.findById(id);
        if (rotaOptional.isPresent()) {
            rotaRepository.delete(rotaOptional.get());
        } else {
            throw new IllegalArgumentException("Rota não encontrada.");
        }
    }

    //OBS: esse método não atualiza o motorista.
    public Rota atualizarRota(Long id, Rota rotaAtualizada) {
        Rota rotaExistente = rotaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma rota foi encontrada com este ID."));

        if (rotaAtualizada.getPontoDePartida() != null && !rotaAtualizada.getPontoDePartida().isEmpty()) {
            rotaExistente.setPontoDePartida(rotaAtualizada.getPontoDePartida());
        }

        if (rotaAtualizada.getPontoDeChegada() != null && !rotaAtualizada.getPontoDeChegada().isEmpty()) {
            rotaExistente.setPontoDeChegada(rotaAtualizada.getPontoDeChegada());
        }


        return rotaRepository.save(rotaExistente);
    }

    public Optional<Rota> buscarRotaPorId(Long id) {
        return rotaRepository.findById(id);
    }

}
