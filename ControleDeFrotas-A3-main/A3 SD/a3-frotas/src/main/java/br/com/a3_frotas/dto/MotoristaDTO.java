package br.com.a3_frotas.dto;

import java.time.LocalDate;

public record MotoristaDTO(

        String nome,
        String cpf,
        String cnh,
        // LocalDate dataNascimento,
        String telefone
        ) {
}
