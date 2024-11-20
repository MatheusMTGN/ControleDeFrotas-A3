package br.com.a3_frotas.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="caminhao")
@Getter
@Setter
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String placa;

    private String model;

    private int ano;

    public Caminhao(String placa, int ano, String model) {
        this.placa = placa;
        this.ano = ano;
        this.model = model;
    }

    public Caminhao() {

    }
}
