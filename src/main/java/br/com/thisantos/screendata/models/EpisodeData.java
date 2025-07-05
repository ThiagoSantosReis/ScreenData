package br.com.thisantos.screendata.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(
        @JsonAlias("Title") String title,
        @JsonAlias({"Episode", "Number"}) Integer episode,
        @JsonAlias({"Released", "releaseDate"}) String released,
        @JsonAlias({"Runtime", "runtime"}) String runtime,
        @JsonAlias("imdbRating") String rating
) {
}
