package br.com.a3_frotas.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @JsonProperty("origem")
    @NotEmpty(message = "Ponto de partida não pode ser vazio")
    private String pontoDePartida;

    @JsonProperty("destino")
    @NotEmpty(message = "Ponto de chegada não pode ser vazio")
    private String pontoDeChegada;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    public Rota(Long id, String pontoDePartida, String pontoDeChegada, Motorista motorista) {
        this.id = id;
        this.pontoDePartida = pontoDePartida;
        this.pontoDeChegada = pontoDeChegada;
        this.motorista = motorista;
    }

    public Rota() {
    }
}
