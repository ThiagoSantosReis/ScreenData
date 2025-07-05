package br.com.thisantos.screendata.models;

import br.com.thisantos.screendata.models.enums.Category;
import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Entity
@Table(name="series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private Double rating;
    private Date released; // Somente ler json
    @Enumerated(EnumType.STRING)
    private Category category; //Serve tanto na serialização quanto deserialização
    private String actors;
    private String poster;
    private String plot;
    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodes;


    @Transient
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

    public Series(){}

    public Series(SeriesData data) throws ParseException {
        if(data.released().equalsIgnoreCase("null") || data.released().equalsIgnoreCase("N/A")){
            throw new ParseException("Error: Date cannot be invalid", 0);
        }
        this.title = data.title();
        this.totalSeasons = data.totalSeasons();
        this.released = Optional.of(sdf.parse(data.released())).orElse(new Date());
        this.rating = OptionalDouble.of(Double.parseDouble(data.rating())).orElse(0.0);
        this.category = Category.fromString(data.genre().split(",")[0].trim());
        this.actors = data.actors();
        this.poster = data.poster();
        this.plot = data.plot().trim();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getReleased() {
        return released;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.forEach(e -> e.setSeries(this));
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return  "category=" + category +
                ", title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
                ", released=" + released +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                ", plot='" + plot +
                ", episodes='" + episodes
                ;
    }
}
