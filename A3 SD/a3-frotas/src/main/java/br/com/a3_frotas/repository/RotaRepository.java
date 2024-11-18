package br.com.a3_frotas.repository;

import br.com.a3_frotas.model.Rota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RotaRepository extends JpaRepository<Rota, Long> {

    List<Rota> findByMotoristaId(Long motoristaId);

    List<Rota> findByCaminhaoPlaca(Long caminhaoPlaca);
}
