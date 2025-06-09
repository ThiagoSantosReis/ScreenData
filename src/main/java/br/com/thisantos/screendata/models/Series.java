package br.com.thisantos.screendata.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Series(
        @JsonAlias({"Title", "Titulo", "title"}) String title,
        @JsonAlias("totalSeasons") Integer totalSeasons,
        @JsonAlias("imdbRating") Double rating,
        @JsonAlias("Released") String released, // Somente ler json
        @JsonProperty("Genre") String genre //Serve tanto na serialização quanto deserialização
) {
}
