package br.com.thisantos.screendata.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(
        @JsonAlias("Season") Integer number,
        @JsonAlias("Episodes") List<EpisodeData> episodeData,
        @JsonAlias("Released") String released
) {
}
