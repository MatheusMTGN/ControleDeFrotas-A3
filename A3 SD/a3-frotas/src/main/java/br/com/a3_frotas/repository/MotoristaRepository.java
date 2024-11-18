package br.com.a3_frotas.repository;

import br.com.a3_frotas.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    Optional<Motorista> findByNome(String nome);
    Optional<Motorista> findByEmail(String email);
    Optional<Motorista> findById(Long id);
    List<Motorista> findAll();
}
