package com.challengeliteralura.challengeliteralura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Resposta(
        @JsonAlias("count")  int quantidade,
        @JsonAlias("next")  String proximo,
        @JsonAlias("previous")  String anterior,
        @JsonAlias("results") List<Livro> resultados
) {

}
