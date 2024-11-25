package br.com.a3_frotas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "motoristas")
@Getter
@Setter
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

    @OneToOne
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

    public void setDataNascimento(LocalDate dataNascimento) {
        if(dataNascimento == null) {
            throw new IllegalArgumentException("A data de nascimento não pode ser nula.");
        }

        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if(idade < 18) {
            throw new IllegalArgumentException("O motorista deve ser maior de 18 anos.");
        }else{
            this.dataNascimento = dataNascimento;
        }
    }


    public void setCpf(String cpf) {
        if(cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("O CPF deve contar 11 dígitos númericos");
        }else{
            this.cpf = cpf;
        }
    }

    public void setCnh(String cnh) {
        if(cnh == null || cnh.length() != 11) {
            throw new IllegalArgumentException("A CNH deve conter 11 dígitos númericos");
        }else{
            this.cnh = cnh;
        }
    }



    public Motorista() {
    }
}
