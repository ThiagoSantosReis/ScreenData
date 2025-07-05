package br.com.thisantos.screendata.principal;

import br.com.thisantos.screendata.models.*;
import br.com.thisantos.screendata.models.enums.Category;
import br.com.thisantos.screendata.repository.SeriesRepository;
import br.com.thisantos.screendata.services.DataConverter;
import br.com.thisantos.screendata.services.MediaAPI;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner reader = new Scanner(System.in);
    DataConverter converter = new DataConverter();
    private MediaAPI mediaApi = new MediaAPI();
    private final String ADRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=19b2c158";

    private List<SeriesData> seriesData = new ArrayList<>();
    private SeriesRepository seriesRepository;

    private List<Series> series = new ArrayList<>();

    public Principal(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public void showMenu(){
        int option = -1;
        while(option != 0) {
            var menu = """
                    0 - Exit
                    1 - Search a Series
                    2 - Search Episodes
                    3 - Show Added Series
                    4 - Search Series by Title
                    5 - Search Series Containing an actor
                    6 - Ranking TOP 3
                    7 - Search by Category
                    8 - Find Series by Total of seasons
                    >
                    """;

            System.out.println(menu);

            option = Integer.parseInt(reader.nextLine());

            if(option == 0){
                break;
            }

            switch (option) {
                case 1:
                    try {
                        searchSeries();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    searchEpisodePerSeries();
                    //streamOperations();
                    break;
                case 3:
                    printAddedSeries();
                    break;
                case 4:
                    searchByTitle();
                    break;
                case 5:
                    searchByActor();
                    break;
                case 6:
                    getTop3Series();
                    break;
                case 7: 
                    getSeriesByCategory();
                    break;
                case 8:
                    getSeriesBySeasonCount();
                    break;
                default:
                    System.out.println("Invalid option!");
            }

        }



    }

    private void getSeriesBySeasonCount() {
        System.out.println("Enter total of seasons: ");
        int totalSeasons = Integer.parseInt(reader.nextLine());
        System.out.println("Rating from: ");
        double rating = Double.parseDouble(reader.nextLine());
        List<Series> foundSeries = seriesRepository.findByTotalSeasonsAndRatingGreaterThanEqual(totalSeasons, rating);
        foundSeries.forEach(s -> System.out.println(s.getTitle() +" - "+
                s.getRating()+" - total seasons: "+s.getTotalSeasons()));


    }

    private void getSeriesByCategory() {
        System.out.println("Enter Category: ");
        String categoryName = reader.nextLine();
        Category category = Category.fromString(categoryName);
        List<Series> foundSeries = seriesRepository.findByCategory(category);
        foundSeries.forEach(System.out::println);
    }

    private void getTop3Series() {
        List<Series> rank =  seriesRepository.findTop3ByOrderByRatingDesc();
        rank.forEach(s -> System.out.println(s.getTitle() + " - "+ s.getRating()));
    }

    private void searchByActor() {
        System.out.println("Enter actor's name: ");
        var actorName = reader.nextLine();
        System.out.println("Ratings from: ");
        double rating = Double.parseDouble(reader.nextLine());
        List<Series> foundSeries = seriesRepository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actorName, rating);
        System.out.println("Series starring "+actorName);
        foundSeries.forEach(s -> System.out.println(s.getTitle() + " - "+ s.getRating()));
    }

    private void searchByTitle() {
        System.out.println("Enter series' title: ");
        var name = reader.nextLine();
        Optional<Series> seriesName = seriesRepository.findByTitleContainingIgnoreCase(name);

        if(seriesName.isPresent()){
            System.out.println(seriesName.get());
        }else{
            System.out.println("Series not found");
        }
    }

    private void printAddedSeries() {
        series = seriesRepository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Series::getCategory))
                .forEach(System.out::println);
    }

    private void searchSeries() throws ParseException {
        SeriesData data = getSeriesData();
        Series series = new Series(data);
        seriesRepository.save(series);
        //seriesData.add(data);
        System.out.println(data);
    }

    private SeriesData getSeriesData(){
        System.out.println("Enter Series' name: ");
        String seriesName = reader.nextLine();
        seriesName = seriesName.contains(" ") ? seriesName.replaceAll(" ", "+") : seriesName;
        var jsonData = mediaApi.getData(ADRESS + seriesName.replaceAll(" ", "+") + API_KEY);
        SeriesData data = converter.getData(jsonData, SeriesData.class);
        return data;
    }

    public void searchEpisodePerSeries(){
        printAddedSeries();
        System.out.println("Enter series' name: ");
        var seriesName = reader.nextLine();

        Optional<Series> seriesOpt = seriesRepository.findByTitleContainingIgnoreCase(seriesName);

        /*Optional<Series> seriesOpt = series.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(seriesName.toLowerCase()))
                .findFirst();

         */

        List<SeasonData> seasons = new ArrayList<>();


        if(seriesOpt.isPresent()){
            var foundSeries = seriesOpt.get();
            for(int i = 1; i <= foundSeries.getTotalSeasons(); i++){
                var jsonData = mediaApi.getData(ADRESS+foundSeries.getTitle().replaceAll(" ", "+")+"&season="+i+API_KEY);
                SeasonData seasonData = converter.getData(jsonData, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(data -> data.episodeData().stream()
                            .map(episode -> new Episode(data.number(), episode)))
                    .collect(Collectors.toList());
            foundSeries.setEpisodes(episodes);
            seriesRepository.save(foundSeries);

        }else {
            System.out.println("Series not found.");
        }


    }


}
