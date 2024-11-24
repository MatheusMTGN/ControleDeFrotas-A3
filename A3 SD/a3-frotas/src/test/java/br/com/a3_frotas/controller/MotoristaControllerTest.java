package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Motorista;
import java.time.LocalDate;
import java.util.List;

import br.com.a3_frotas.repository.MotoristaRepository;
import br.com.a3_frotas.service.MotoristaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class MotoristaControllerTest {
    @Mock
    private MotoristaService motoristaService;

    @Mock
    private MotoristaRepository motoristaRepository;

    @InjectMocks
    private MotoristaController motoristaController;

    private Motorista motorista;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        motorista = new Motorista();
        motorista.setId(1L);
        motorista.setNome("Matheus");
        motorista.setCnh("12345678901");
        motorista.setCpf("12345678901");
        motorista.setTelefone("987654321");
        motorista.setEmail("Matheus@exemplo.com");
        motorista.setDataNascimento(LocalDate.of(1990, 1, 1)); // Exemplo de data de nascimento
    }

    @Test
    void testarCadastrarMotorista() {

        Motorista motorista = new Motorista();
        motorista.setId(1L);
        motorista.setNome("Matheus");
        motorista.setCnh("12345678901");


        when(motoristaService.cadastrarMotorista(any(Motorista.class))).thenReturn(motorista);


        ResponseEntity<Motorista> response = (ResponseEntity<Motorista>) motoristaController.cadastrarMotorista(motorista);


        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(motorista, response.getBody());
    }

    @Test
    void testBuscarMotoristaPorId() {
        when(motoristaService.detalharMotorista(1L)).thenReturn(motorista);

        ResponseEntity<Motorista> response = motoristaController.buscarMotoristaPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Matheus", response.getBody().getNome());
    }

    @Test
    void testVincularCaminhao() {
        Long motoristaId = 1L;
        Long caminhaoId = 2L;
        when(motoristaService.vincularCaminhao(motoristaId, caminhaoId)).thenReturn(motorista);

        ResponseEntity<Motorista> response = motoristaController.vincularCaminhao(motoristaId, caminhaoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(motorista, response.getBody());
        verify(motoristaService, times(1)).vincularCaminhao(motoristaId, caminhaoId);
    }

    @Test
    void testListarMotoristas() {
        List<Motorista> motoristas = List.of(motorista);
        when(motoristaService.listarTodosOsMotoristas()).thenReturn(motoristas);

        ResponseEntity<List<Motorista>> response = motoristaController.listarMotoristas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(motoristaService, times(1)).listarTodosOsMotoristas();
    }

    @Test
    void testListarMotoristasAtivos() {
        List<Motorista> motoristasAtivos = List.of(motorista);
        when(motoristaService.listarMotoristasAtivos()).thenReturn(motoristasAtivos);

        ResponseEntity<List<Motorista>> response = motoristaController.listarMotoristasAtivos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(motoristaService, times(1)).listarMotoristasAtivos();
    }

    @Test
    void testAtualizarMotorista() {
        Motorista motoristaAtualizado = new Motorista();
        motoristaAtualizado.setId(1L);
        motoristaAtualizado.setNome("Matheus Atualizado");
        when(motoristaService.atualizarMotorista(1L, motoristaAtualizado)).thenReturn(motoristaAtualizado);

        ResponseEntity<Motorista> response = motoristaController.atualizarMotorista(1L, motoristaAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(motoristaAtualizado, response.getBody());
        verify(motoristaService, times(1)).atualizarMotorista(1L, motoristaAtualizado);
    }

    @Test
    void testDetalhesMotorista() {
        when(motoristaService.detalharMotorista(1L)).thenReturn(motorista);

        ResponseEntity<?> response = motoristaController.detalhesMotorista(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Matheus", ((Motorista) response.getBody()).getNome());
    }

    @Test
    void testDetalhesMotorista_NotFound() {
        when(motoristaService.detalharMotorista(1L)).thenReturn(null);

        ResponseEntity<?> response = motoristaController.detalhesMotorista(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testExcluirMotorista() {
        doNothing().when(motoristaService).deletarMotorista(1L);

        ResponseEntity<Void> response = motoristaController.deletarMotorista(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(motoristaService, times(1)).deletarMotorista(1L);
    }


}
