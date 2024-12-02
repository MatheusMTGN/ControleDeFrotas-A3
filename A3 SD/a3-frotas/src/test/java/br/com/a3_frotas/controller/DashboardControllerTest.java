package br.com.a3_frotas.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.a3_frotas.controller.DashboardController;
import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.service.CaminhaoService;
import br.com.a3_frotas.service.MotoristaService;
import br.com.a3_frotas.service.RotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;


import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;

public class DashboardControllerTest {
    private DashboardController dashboardController;
    private CaminhaoService caminhaoService;
    private MotoristaService motoristaService;
    private RotaService rotaService;

    @BeforeEach
    void setUp() {
        caminhaoService = mock(CaminhaoService.class);
        motoristaService = mock(MotoristaService.class);
        rotaService = mock(RotaService.class);
        dashboardController = new DashboardController(motoristaService, caminhaoService, rotaService);
    }

    @Test
    void testExibirHome_ComDadosValidos() {
        // Preparando os dados mockados para as dependências
        Caminhao caminhao = new Caminhao("AAA-1234", 2020, "Modelo A");
        Motorista motorista = new Motorista(caminhao, true, "João", "12345", LocalDate.of(1990, 1, 1), "1234-5678", "Rua A", "joao@exemplo.com");
        Rota rota = new Rota(1L, "Rota A", "Descrição da Rota", motorista); // Usando o construtor correto

        List<Caminhao> caminhaos = Arrays.asList(caminhao);
        List<Motorista> motoristas = Arrays.asList(motorista);
        List<Rota> rotas = Arrays.asList(rota);

        when(caminhaoService.listarTodosOsCaminhoes()).thenReturn(caminhaos);
        when(motoristaService.listarTodosOsMotoristas()).thenReturn(motoristas);
        when(rotaService.listarRotas()).thenReturn(rotas);

        // Executando o método
        ModelAndView mv = dashboardController.exibirHome();

        // Verificando se os dados são passados corretamente para o ModelAndView
        assertNotNull(mv);
        assertEquals("home", mv.getViewName());
        assertTrue(mv.getModel().containsKey("caminhoes"));
        assertTrue(mv.getModel().containsKey("motoristas"));
        assertTrue(mv.getModel().containsKey("rotas"));
        assertEquals(caminhaos, mv.getModel().get("caminhoes"));
        assertEquals(motoristas, mv.getModel().get("motoristas"));
        assertEquals(rotas, mv.getModel().get("rotas"));
    }

    @Test
    void testExibirHome_ComFalhaNoCaminhaoService() {
        // Simulando a falha no serviço
        when(caminhaoService.listarTodosOsCaminhoes()).thenThrow(new RuntimeException("Erro ao listar caminhões"));

        // Executando o método
        ModelAndView mv = null;
        try {
            mv = dashboardController.exibirHome();
        } catch (Exception e) {
            // Aqui você pode tratar ou verificar se a exceção foi lançada corretamente
        }

        // Verificando se o ModelAndView é retornado com o erro adequado
        assertNull(mv); // O ModelAndView não deve ser retornado devido ao erro no serviço
    }
}
