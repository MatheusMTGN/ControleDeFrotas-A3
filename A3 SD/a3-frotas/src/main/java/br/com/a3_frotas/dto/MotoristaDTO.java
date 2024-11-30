package br.com.a3_frotas.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MotoristaDTO {
    private String nome;
    private String cpf;
    private String cnh;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;


    private Long caminhaoId;
}