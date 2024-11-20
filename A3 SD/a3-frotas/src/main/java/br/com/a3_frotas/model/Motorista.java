package br.com.a3_frotas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "motoristas")
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String cnh;
    private String telefone;
    private Boolean ativo = true;
    private String email;

    @OneToMany(mappedBy = "motorista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rota> rotas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caminhao_id", referencedColumnName = "id", nullable = false)
    private Caminhao caminhao;


    public Motorista(Caminhao caminhao, Boolean ativo, String telefone, String cnh, LocalDate dataNascimento, String cpf, String nome, String email) {
        this.caminhao = caminhao;
        this.ativo = ativo;
        this.telefone = telefone;
        this.cnh = cnh;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }


    public Motorista() {
    }
}
