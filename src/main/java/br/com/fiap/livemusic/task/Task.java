package br.com.fiap.livemusic.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Task {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "o campo Nome da Musica é obrigatório")
    String title;

    @Size(min = 5, message = "a descrição da musica, para procura, deve ter pelo menos 5 caracteres")
    String description;

    @Min(1) @Max(100)
    Integer score;

    @Min(0) @Max(100)
    Integer status;

}
