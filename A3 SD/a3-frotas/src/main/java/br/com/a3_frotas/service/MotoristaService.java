package br.com.a3_frotas.service;

import br.com.a3_frotas.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;


    @Autowired
    public MotoristaService (MotoristaRepository motoristaRepository){

        this.motoristaRepository = motoristaRepository;
    }
}
