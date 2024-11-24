package br.com.a3_frotas.service;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.repository.CaminhaoRepository;
import br.com.a3_frotas.repository.MotoristaRepository;
import br.com.a3_frotas.repository.RotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MotoristaServiceTest {
    @Mock
    private MotoristaRepository motoristaRepository;

    @Mock
    private CaminhaoRepository caminhaoRepository;

    @Mock
    private RotaRepository rotaRepository;

    @InjectMocks
    private MotoristaService motoristaService;

    private Motorista motorista;
    private Caminhao caminhao;
    private Rota rota;

    @BeforeEach
    void setUp() {
        motorista = new Motorista();
        motorista.setId(1L);
        motorista.setCpf("12345678901");
        motorista.setNome("João");
        motorista.setEmail("joao@exemplo.com");
        motorista.setTelefone("999999999");
        motorista.setAtivo(true);

        caminhao = new Caminhao("ABC-1234", 2020, "Modelo A");

        rota = new Rota();
        rota.setId(1L);
        rota.setMotorista(motorista);
    }

    @Test
    void testCadastrarMotorista_Success() {
        when(motoristaRepository.findByCpf(motorista.getCpf())).thenReturn(Optional.empty());
        when(motoristaRepository.findByEmail(motorista.getEmail())).thenReturn(Optional.empty());
        when(motoristaRepository.save(motorista)).thenReturn(motorista);

        Motorista result = motoristaService.cadastrarMotorista(motorista);

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    void testCadastrarMotorista_CpfJaCadastrado() {
        when(motoristaRepository.findByCpf(motorista.getCpf())).thenReturn(Optional.of(motorista));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            motoristaService.cadastrarMotorista(motorista);
        });

        assertEquals("Motorista já cadastrado com este CPF.", thrown.getMessage());
    }

    @Test
    void testCadastrarMotorista_EmailJaCadastrado() {
        when(motoristaRepository.findByEmail(motorista.getEmail())).thenReturn(Optional.of(motorista));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            motoristaService.cadastrarMotorista(motorista);
        });

        assertEquals("Já existe um motorista com este e-mail.", thrown.getMessage());
    }

    @Test
    void testVincularCaminhao_Success() {
        when(motoristaRepository.findById(1L)).thenReturn(Optional.of(motorista));
        when(caminhaoRepository.findById(1L)).thenReturn(Optional.of(caminhao));
        when(motoristaRepository.save(motorista)).thenReturn(motorista);

        Motorista result = motoristaService.vincularCaminhao(1L, 1L);

        assertNotNull(result);
        assertEquals(caminhao, result.getCaminhao());
    }

    @Test
    void testVincularCaminhao_MotoristaNaoEncontrado() {
        when(motoristaRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            motoristaService.vincularCaminhao(1L, 1L);
        });

        assertEquals("Nenhum motorista foi encontrado com este ID.", thrown.getMessage());
    }

    @Test
    void testVincularCaminhao_CaminhaoNaoEncontrado() {
        when(motoristaRepository.findById(1L)).thenReturn(Optional.of(motorista));
        when(caminhaoRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            motoristaService.vincularCaminhao(1L, 1L);
        });

        assertEquals("Nenhum caminhão foi encontrado com este ID.", thrown.getMessage());
    }

    @Test
    void testListarTodosOsMotoristas() {
        when(motoristaRepository.findAll()).thenReturn(List.of(motorista));

        List<Motorista> motoristas = motoristaService.listarTodosOsMotoristas();

        assertNotNull(motoristas);
        assertEquals(1, motoristas.size());
    }

    @Test
    void testListarMotoristasAtivos() {
        when(motoristaRepository.findByAtivoTrue()).thenReturn(List.of(motorista));

        List<Motorista> motoristas = motoristaService.listarMotoristasAtivos();

        assertNotNull(motoristas);
        assertEquals(1, motoristas.size());
    }

    @Test
    void testFiltrarPorId() {
        when(motoristaRepository.findById(1L)).thenReturn(Optional.of(motorista));

        Motorista result = motoristaService.filtrarPorId(1L);

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    void testAtualizarMotorista_Success() {
        Motorista motoristaAtualizado = new Motorista();
        motoristaAtualizado.setNome("João Silva");
        motoristaAtualizado.setCpf("12345678901");
        motoristaAtualizado.setEmail("joao@exemplo.com");
        motoristaAtualizado.setTelefone("999999998");
        motoristaAtualizado.setAtivo(true);

        when(motoristaRepository.findById(1L)).thenReturn(Optional.of(motorista));
        when(motoristaRepository.save(motorista)).thenReturn(motoristaAtualizado);

        Motorista result = motoristaService.atualizarMotorista(1L, motoristaAtualizado);

        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
        assertEquals("999999998", result.getTelefone());
    }

    @Test
    void testDeletarMotorista() {
        when(motoristaRepository.findById(1L)).thenReturn(Optional.of(motorista));
        when(motoristaRepository.save(motorista)).thenReturn(motorista);

        motoristaService.deletarMotorista(1L);

        assertFalse(motorista.getAtivo());
    }

    @Test
    void testDetalharMotorista_Success() {
        when(motoristaRepository.findById(1L)).thenReturn(Optional.of(motorista));
        when(rotaRepository.findByMotoristaId(1L)).thenReturn(List.of(rota));

        Motorista result = motoristaService.detalharMotorista(1L);

        assertNotNull(result);
        assertEquals("João", result.getNome());
        assertEquals(1, result.getRotas().size());
    }

    @Test
    void testDetalharMotorista_MotoristaNaoEncontrado() {
        when(motoristaRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            motoristaService.detalharMotorista(1L);
        });

        assertEquals("Nenhum motorista foi encontrado com este ID.", thrown.getMessage());
    }
}
