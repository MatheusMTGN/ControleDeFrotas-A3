package br.com.a3_frotas.controller;

import br.com.a3_frotas.model.Caminhao;
import br.com.a3_frotas.model.Rota;
import br.com.a3_frotas.service.CaminhaoService;
import br.com.a3_frotas.service.RotaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.a3_frotas.model.Motorista;
import br.com.a3_frotas.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/home")
public class DashboardController {

    private final MotoristaService motoristaService;
    private final CaminhaoService caminhaoService;
    private final RotaService rotaService;

    @Autowired
    public DashboardController(MotoristaService motoristaService, CaminhaoService caminhaoService, RotaService rotaService) {
        this.motoristaService = motoristaService;
        this.caminhaoService = caminhaoService;
        this.rotaService = rotaService;
    }

    @GetMapping
    public ModelAndView exibirHome() {
        List<Caminhao> caminhaos = caminhaoService.listarTodosOsCaminhoes();
        List<Motorista> motoristas = motoristaService.listarTodosOsMotoristas();
        List<Rota> rotas = rotaService.listarRotas();


        ModelAndView mv = new ModelAndView("home");
        mv.addObject("caminhoes", caminhaos);
        mv.addObject("motoristas", motoristas);
        mv.addObject("rotas", rotas);
        return mv;
    }

}
