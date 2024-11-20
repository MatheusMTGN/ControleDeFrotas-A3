package br.com.a3_frotas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="rota")
@Getter
@Setter
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pontoDePartida;
    private String pontoDeChegada;

    @ManyToOne //Uma rota está associada a um motorista, mas um motorista está associada a várias rotas.
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "placa_caminhao", nullable = false)
    private Caminhao caminhao;


    public Rota(Long id, String pontoDePartida, String pontoDeChegada, Motorista motorista) {
        this.id = id;
        this.pontoDePartida = pontoDePartida;
        this.pontoDeChegada = pontoDeChegada;
        this.motorista = motorista;

    }

    public Rota() {

    }
}
