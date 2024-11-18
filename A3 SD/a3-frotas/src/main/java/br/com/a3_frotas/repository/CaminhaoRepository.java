package br.com.a3_frotas.repository;

import br.com.a3_frotas.model.Caminhao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaminhaoRepository extends JpaRepository<Caminhao, Integer> {

    Optional<Caminhao> findByPlaca(String placa);
}