package br.com.a3_frotas.model;


import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(nullable = false)
     private String placa;

    @JsonProperty("modelo")
    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int ano;

    @OneToOne(mappedBy = "caminhao")
    private Motorista motorista;


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
