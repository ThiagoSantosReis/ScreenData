package br.com.thisantos.screendata.repository;

import br.com.thisantos.screendata.models.Series;
import br.com.thisantos.screendata.models.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
   Optional<Series> findByTitleContainingIgnoreCase(String name);

    List<Series> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actorName, double ratings);

    List<Series> findTop3ByOrderByRatingDesc();

    List<Series> findByCategory(Category category);

    List<Series> findByTotalSeasonsAndRatingGreaterThanEqual(int totalSeasons, double rating);
}
