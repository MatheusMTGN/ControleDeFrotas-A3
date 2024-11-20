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
    public MotoristaService (MotoristaRepository motoristaRepository, CaminhaoRepository caminhaoRepository, RotaRepository rotaRepository) {

        this.motoristaRepository = motoristaRepository;
        this.caminhaoRepository = caminhaoRepository;
        this.rotaRepository = rotaRepository;
    }

    public Motorista cadastrarMotorista(Motorista motorista, String placaCaminhao, String modelCaminhao, int anoCaminhao) {

        motoristaRepository.findByCpf(motorista.getCpf()).ifPresent(existing -> {
            throw new IllegalArgumentException("Motorista já cadastrado com este CPF.");
        });


        motoristaRepository.findByEmail(motorista.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Já existe um motorista com este e-mail.");
        });


        if (placaCaminhao == null || placaCaminhao.isBlank()) {
            throw new IllegalArgumentException("É necessário informar a placa do caminhão.");
        }


        Optional<Caminhao> caminhaoExistente = caminhaoRepository.findByPlaca(placaCaminhao);

        Caminhao caminhao;
        if (caminhaoExistente.isPresent()) {

            caminhao = caminhaoExistente.get();
        } else {

            caminhao = new Caminhao(placaCaminhao, anoCaminhao, modelCaminhao);
            caminhao = caminhaoRepository.save(caminhao);
        }


        motorista.setCaminhao(caminhao);


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

    //Deverá ser mostrado as informações pessoais do motorista, bem como a associação dos mesmos com caminhões e corridas
    public Motorista detalharMotorista(Long id){
        Optional<Motorista> motoristaOptional = motoristaRepository.findById(id);

        if(motoristaOptional.isEmpty()){
            throw new IllegalArgumentException("Nenhum motorista foi encontrado com este ID.");
        }

        Motorista motorista = motoristaOptional.get();
        List<Rota> rotas = rotaRepository.findByMotoristaId(id);
        motorista.setRotas(rotas);
        return motorista;
    }
}
