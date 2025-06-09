package br.com.thisantos.screendata.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Series(
        @JsonAlias({"Title", "Titulo", "title"}) String title,
        @JsonAlias("totalSeasons") Integer totalSeasons,
        @JsonAlias("imdbRating") Double rating,
        @JsonAlias("Released") String released, // Somente ler json
        @JsonProperty("Genre") List<String> genre //Serve tanto na serialização quanto deserialização
) {
}
