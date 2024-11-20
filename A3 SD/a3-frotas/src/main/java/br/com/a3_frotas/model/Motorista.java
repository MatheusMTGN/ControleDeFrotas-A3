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

    @OneToOne
    @JoinColumn(name = "placa_caminhao", nullable = false, unique = true)
    private Caminhao caminhao;

    // Construtor com argumentos
    public Motorista(Caminhao caminhao, Boolean ativo, String telefone, String cnh, LocalDate dataNascimento, String cpf, String nome, Long id, String email) {
        this.caminhao = caminhao;
        this.ativo = ativo;
        this.telefone = telefone;
        this.cnh = cnh;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.nome = nome;
        this.id = id;
        this.email = email;
    }

    // Construtor padrão
    public Motorista() {
    }
}
