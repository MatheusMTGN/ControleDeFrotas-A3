package br.com.a3_frotas.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "rota")
@Entity(name = "rota")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pontoPartida;
    private String destino;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;
}
