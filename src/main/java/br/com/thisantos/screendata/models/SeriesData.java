package br.com.thisantos.screendata.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(
        @JsonAlias({"Title", "Titulo", "title"}) String title,
        @JsonAlias("totalSeasons") Integer totalSeasons,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String released, // Somente ler json
        @JsonProperty("Genre") String genre, //Serve tanto na serialização quanto deserialização
        @JsonAlias("Actors") String actors,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Plot") String plot
) {
}
