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
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class RotaServiceTest {
    @Mock
    private RotaRepository rotaRepository;

    @Mock
    private MotoristaRepository motoristaRepository;

    @Mock
    private CaminhaoRepository caminhaoRepository;

    @InjectMocks
    private RotaService rotaService;

    private Rota rota;
    private Motorista motorista;
    private Caminhao caminhao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        motorista = new Motorista();
        motorista.setId(1L);
        motorista.setNome("João");
        motorista.setAtivo(true);

        caminhao = new Caminhao();
        caminhao.setId(1L);
        caminhao.setPlaca("ABC123");
        caminhao.setAno(2020);
        caminhao.setModel("Modelo A");

        motorista.setCaminhao(caminhao);

        rota = new Rota();
        rota.setId(1L);
        rota.setPontoDePartida("Cidade A");
        rota.setPontoDeChegada("Cidade B");
        rota.setMotorista(motorista);
    }

    @Test
    void testCadastrarRota_Success() {
        when(motoristaRepository.findById(1L)).thenReturn(Optional.of(motorista));
        when(rotaRepository.save(any(Rota.class))).thenReturn(rota);

        Rota novaRota = new Rota();
        novaRota.setPontoDePartida("Cidade A");
        novaRota.setPontoDeChegada("Cidade B");
        novaRota.setMotorista(motorista);

        Rota result = rotaService.cadastrarRota(novaRota);

        assertNotNull(result);
        assertEquals("Cidade A", result.getPontoDePartida());
        assertEquals("Cidade B", result.getPontoDeChegada());
        assertEquals(motorista, result.getMotorista());
    }

    @Test
    void testCadastrarRota_ParametroPontoDePartidaNull() {
        Rota novaRota = new Rota();
        novaRota.setPontoDePartida(null);
        novaRota.setPontoDeChegada("Cidade B");
        novaRota.setMotorista(motorista);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            rotaService.cadastrarRota(novaRota);
        });

        assertEquals("Os pontos de partida e chegada são obrigatórios.", thrown.getMessage());
    }

    @Test
    void testCadastrarRota_MotoristaInativo() {
        motorista.setAtivo(false);
        when(motoristaRepository.findById(1L)).thenReturn(Optional.of(motorista));

        Rota novaRota = new Rota();
        novaRota.setPontoDePartida("Cidade A");
        novaRota.setPontoDeChegada("Cidade B");
        novaRota.setMotorista(motorista);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            rotaService.cadastrarRota(novaRota);
        });

        assertEquals("O motorista não está ativo ou não tem um caminhão", thrown.getMessage());
    }

    @Test
    void testListarRotas() {
        when(rotaRepository.findAll()).thenReturn(List.of(rota));

        assertFalse(rotaService.listarRotas().isEmpty());
        assertEquals(1, rotaService.listarRotas().size());
    }

    @Test
    void testFiltrarPorMotorista_Success() {
        when(rotaRepository.findByMotoristaId(1L)).thenReturn(List.of(rota));

        List<Rota> result = rotaService.filtrarPorMotorista(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cidade A", result.get(0).getPontoDePartida());
    }

    @Test
    void testFiltrarPorMotorista_IdNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            rotaService.filtrarPorMotorista(null);
        });

        assertEquals("O ID do motorista não pode ser nulo", thrown.getMessage());
    }

    @Test
    void testExcluirRota_Success() {
        when(rotaRepository.findById(1L)).thenReturn(Optional.of(rota));

        doNothing().when(rotaRepository).delete(rota);

        rotaService.excluirRota(1L);

        verify(rotaRepository, times(1)).delete(rota);
    }

    @Test
    void testExcluirRota_RotaNaoEncontrada() {
        when(rotaRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            rotaService.excluirRota(1L);
        });

        assertEquals("Nenhuma rota foi encontrada com este ID.", thrown.getMessage());
    }

    @Test
    void testAtualizarRota_Success() {
        Rota rotaAtualizada = new Rota();
        rotaAtualizada.setPontoDePartida("Cidade X");
        rotaAtualizada.setPontoDeChegada("Cidade Y");

        when(rotaRepository.findById(1L)).thenReturn(Optional.of(rota));
        when(rotaRepository.save(rota)).thenReturn(rotaAtualizada);

        Rota result = rotaService.atualizarRota(1L, rotaAtualizada);

        assertNotNull(result);
        assertEquals("Cidade X", result.getPontoDePartida());
        assertEquals("Cidade Y", result.getPontoDeChegada());
    }

    @Test
    void testAtualizarRota_RotaNaoEncontrada() {
        Rota rotaAtualizada = new Rota();
        rotaAtualizada.setPontoDePartida("Cidade X");
        rotaAtualizada.setPontoDeChegada("Cidade Y");

        when(rotaRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            rotaService.atualizarRota(1L, rotaAtualizada);
        });

        assertEquals("Nenhuma rota foi encontrada com este ID.", thrown.getMessage());
    }
}
