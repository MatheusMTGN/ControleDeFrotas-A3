package br.com.a3_frotas.entity;

import jakarta.persistence.*;


@Table(name = "motorista")
@Entity(name = "motorista")
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnh;

    @OneToOne
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;
}
