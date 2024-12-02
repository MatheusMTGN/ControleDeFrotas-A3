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
public class MotoristaService {

    @Autowired
    private final MotoristaRepository motoristaRepository;

    @Autowired
    private final CaminhaoRepository caminhaoRepository;

    @Autowired
    private final RotaRepository rotaRepository;

    @Autowired
    public MotoristaService(MotoristaRepository motoristaRepository, CaminhaoRepository caminhaoRepository, RotaRepository rotaRepository) {
        this.motoristaRepository = motoristaRepository;
        this.caminhaoRepository = caminhaoRepository;
        this.rotaRepository = rotaRepository;
    }

    // Método para cadastrar motorista (inicialmente sem caminhão)
    public Motorista cadastrarMotorista(Motorista motorista) {

        Optional<Motorista> motoristaCpf = motoristaRepository.findByCpf(motorista.getCpf());
        Optional<Motorista> motoristaEmail = motoristaRepository.findByEmail(motorista.getEmail());
        if(motoristaCpf.isPresent() || motoristaEmail.isPresent()) {
            throw new IllegalArgumentException("Já existe um motorista com este CPF e/ou e-mail");
        }
        return motoristaRepository.save(motorista);
    }


    public Motorista vincularCaminhao(Long motoristaId, Long caminhaoId) {
        Motorista motorista = motoristaRepository.findById(motoristaId)
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado"));

        Caminhao caminhao = caminhaoRepository.findById(caminhaoId)
                .orElseThrow(() -> new IllegalArgumentException("Caminhão não encontrado"));

        if (caminhao.getMotorista() != null) {
            Motorista motoristaAnterior = caminhao.getMotorista();
            motoristaAnterior.setCaminhao(null);
            motoristaRepository.save(motoristaAnterior);
        }

        caminhao.setMotorista(motorista);
        motorista.setCaminhao(caminhao);

        caminhaoRepository.save(caminhao);
        return motoristaRepository.save(motorista);
    }

    public List<Motorista> listarTodosOsMotoristas() {
        return motoristaRepository.findAll();
    }

    public List<Motorista> listarMotoristasAtivos() {
        return motoristaRepository.findByAtivoTrue();
    }

    public Motorista filtrarPorId(Long id) {
        return motoristaRepository.findById(id).orElse(null);
    }

    //Não usamos
    public Motorista filtrarPorCpf(String cpf) {
        return motoristaRepository.findByCpf(cpf).orElse(null);
    }

    //Não usamos
    public List<Motorista> filtrarPorNome(String nome) {
        return motoristaRepository.findByNomeContainingIgnoreCase(nome);
    }


    public Motorista atualizarMotorista(Long id, Motorista motoristaAtualizado) {
        Motorista motorista = filtrarPorId(id);

        if (motoristaAtualizado.getTelefone() != null) {
            motorista.setTelefone(motoristaAtualizado.getTelefone());
        }

        if (motoristaAtualizado.getEmail() != null) {
            motorista.setEmail(motoristaAtualizado.getEmail());
        }

        return motoristaRepository.save(motorista);
    }


    public void deletarMotorista(Long id) {
        Motorista motorista = filtrarPorId(id);
        motorista.setAtivo(false);
        motoristaRepository.save(motorista);
    }


    public Motorista detalharMotorista(Long id) {
        Optional<Motorista> motoristaOptional = motoristaRepository.findById(id);

        if (motoristaOptional.isEmpty()) {
            throw new IllegalArgumentException("Nenhum motorista foi encontrado com este ID.");
        }

        Motorista motorista = motoristaOptional.get();
        List<Rota> rotas = rotaRepository.findByMotoristaId(id);
        motorista.setRotas(rotas);
        return motorista;
    }

    public void atualizarMotorista(Motorista motorista) {
        motoristaRepository.save(motorista);
    }

}
