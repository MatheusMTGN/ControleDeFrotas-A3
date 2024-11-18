package br.com.a3_frotas.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="caminhao")
@Getter
@Setter
public class Caminhao {

    @Id
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
