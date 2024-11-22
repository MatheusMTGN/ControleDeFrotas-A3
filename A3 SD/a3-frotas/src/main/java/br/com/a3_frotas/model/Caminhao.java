package br.com.a3_frotas.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="caminhoes")
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

    public void setPlaca(String placa) {
        if(placa.length()!=6){
            throw new IllegalArgumentException("A placa deve conter 6 dígitos");
        }else{
            this.placa = placa;
        }
    }

    public Caminhao() {

    }


}
