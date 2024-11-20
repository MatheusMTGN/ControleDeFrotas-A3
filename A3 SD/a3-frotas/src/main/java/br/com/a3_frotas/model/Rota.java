package br.com.a3_frotas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rotas")
@Getter
@Setter
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pontoDePartida;
    private String pontoDeChegada;

    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "placa_caminhao", nullable = false)
    private Caminhao caminhao;

    // Construtor com argumentos
    public Rota(Long id, String pontoDePartida, String pontoDeChegada, Motorista motorista, Caminhao caminhao) {
        this.id = id;
        this.pontoDePartida = pontoDePartida;
        this.pontoDeChegada = pontoDeChegada;
        this.motorista = motorista;
        this.caminhao = caminhao;
    }

    // Construtor padrão
    public Rota() {
    }
}
