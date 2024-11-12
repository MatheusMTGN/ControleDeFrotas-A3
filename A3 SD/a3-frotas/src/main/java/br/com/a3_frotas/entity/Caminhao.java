package br.com.a3_frotas.entity;

import jakarta.persistence.*;

@Table(name="Caminhao")
@Entity (name = "Caminhao")
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String modelo;
    private int anoFabricacao;
}
