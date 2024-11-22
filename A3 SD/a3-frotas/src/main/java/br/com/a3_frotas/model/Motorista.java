package br.com.a3_frotas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true)
    private String cnh;

    @Column(nullable = false)
    private String telefone;


    private Boolean ativo = true;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "motorista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rota> rotas;

    @ManyToOne
    @JoinColumn(name = "caminhao_id", nullable = true)
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
