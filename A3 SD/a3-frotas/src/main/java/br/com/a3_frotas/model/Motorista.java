package br.com.a3_frotas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name= "motorista")
public class Motorista {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String cnh;
    private String telefone;
    private Boolean ativo = true;

    @OneToOne
    @JoinColumn(name = "placa_caminhao", referencedColumnName = "placa", nullable = false, unique = true)
    private Caminhao caminhao;


    public Motorista(Caminhao caminhao, Boolean ativo, String telefone, String cnh, LocalDate dataNascimento, String cpf, String nome, Long id) {
        this.caminhao = caminhao;
        this.ativo = ativo;
        this.telefone = telefone;
        this.cnh = cnh;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.nome = nome;
        this.id = id;
    }

    public Motorista() {
    }
}
