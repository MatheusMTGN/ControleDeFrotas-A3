package br.com.a3_frotas.service;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.repository.CaminhaoRepository;
import br.com.a3_frotas.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final CaminhaoRepository caminhaoRepository;


    @Autowired
    public MotoristaService (MotoristaRepository motoristaRepository, CaminhaoRepository caminhaoRepository) {

        this.motoristaRepository = motoristaRepository;
        this.caminhaoRepository = caminhaoRepository;
    }

    public Motorista cadastrarMotorista(Motorista motorista) {
        Optional<Motorista> motoristaExistente = motoristaRepository.findByCpf(motorista.getCpf());
        if (motoristaExistente.isPresent()) {
            throw new IllegalArgumentException("Motorista já cadastrado");
        }

        Optional<Motorista> motoristaEmail = motoristaRepository.findByEmail(motorista.getEmail());
        if (motoristaEmail.isPresent()) {
            throw new IllegalArgumentException("Já existe um motorista com este e-mail");
        }

        // Verficia se há caminhão para cadastro. Se existir, ele cadastra (caso ainda não exista);
        if (motorista.getCaminhao() != null) {
            Caminhao caminhao = motorista.getCaminhao();
            Optional<Caminhao> caminhaoExistente = caminhaoRepository.findByPlaca(caminhao.getPlaca());
            if (caminhaoExistente.isEmpty()) {
                caminhaoRepository.save(caminhao); //Salva o caminhão, caso ainda não estiver cadastrado
            }else{
                motorista.setCaminhao(caminhaoExistente.get()); //Seta o caminhão ao motorista cadastrado
            }
        }
        return motoristaRepository.save(motorista);
    }

    public List<Motorista> listarTodosOsMotoristas() {
        return motoristaRepository.findAll();
    }

    public List<Motorista> listarMotoristasAtivos(){
        return motoristaRepository.findByAtivoTrue();
    }

    public Motorista filtrarPorId (Long id) {
        return motoristaRepository.findById(id).orElse(null);
    }

    public Motorista filtrarPorCpf(String cpf) {
        return motoristaRepository.findByCpf(cpf).orElse(null);
    }

    public List<Motorista> filtrarPorNome(String nome) {
        return motoristaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Motorista atualizarMotorista (Long id, Motorista motoristaAtualizado) {
        Motorista motorista = filtrarPorId(id);

        if(motoristaAtualizado.getTelefone() != null) {
            motorista.setTelefone(motoristaAtualizado.getTelefone());
        }

        if(motoristaAtualizado.getEmail() != null) {
            motorista.setEmail(motoristaAtualizado.getEmail());
        }

        return motoristaRepository.save(motorista);
    }

    public void deletarMotorista (Long id) {
        Motorista motorista = filtrarPorId(id);
        motorista.setAtivo(false);
        motoristaRepository.save(motorista);
    }
}
