package br.com.thisantos.screendata.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer season;
    private String title;
    private Integer number;
    private Double rating;
    private LocalDate releaseDate;
    @ManyToOne
    private Series series;

    public Episode(){}

    public Episode(int season, EpisodeData epiData){
        this.season = season;
        this.title = epiData.title();
        this.number = epiData.episode();
        try{
            this.rating = Double.parseDouble(epiData.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        try{
            this.releaseDate = LocalDate.parse(epiData.released());
        }catch (DateTimeParseException e){
            this.releaseDate = null;
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString(){
        return """
                S%dE%d - %s
                Rating: %.2f
                Release Date: %s
                """.formatted(season, number, title, rating, releaseDate);
    }

}
