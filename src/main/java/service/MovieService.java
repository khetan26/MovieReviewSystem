package main.java.service;

import main.java.dao.MovieDao;
import main.java.enums.Genre;
import main.java.enums.Language;
import main.java.model.Movie;
import main.java.util.ValidationUtil;

import java.util.Date;
import java.util.List;

public class MovieService {

    private MovieDao movieDao;

    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public void addMovie(String title, List<String> genres, String language, String desc, String releaseDate, List<String> actors, List<String> directors) throws IllegalArgumentException {
        if(ValidationUtil.isNullOrEmpty(title))
            throw new IllegalArgumentException("Movie title cannot be null or empty");
        List<Genre> parsedGenres = ValidationUtil.parseEnum(Genre.class, genres);
        Language parsedLanguage = null;
        if(language != null)
            parsedLanguage = ValidationUtil.parseEnum(Language.class, language);
        Date parsedReleaseDate = ValidationUtil.parseDate(releaseDate);

        Movie movie = new Movie.MovieBuilder()
                .withTitle(title)
                .withGenre(parsedGenres)
                .withDesc(desc)
                .withLanguage(parsedLanguage)
                .withReleaseDate(parsedReleaseDate)
                .withDirectors(directors)
                .withActors(actors)
                .build();

        movieDao.addMovie(movie);
    }


    /**Ideally a movie should be uniquely identified through a combination of title, genres, releaseDate and language
     *For sake of this code implementation I am considering just the movie name
     */
    public Movie getMovie(String title) {
        return movieDao.getMovie(title);
    }


    //Improvement
    public Movie getMovie(String title, String releaseDate, List<String> genres) {
        List<Genre> parsedGenres = ValidationUtil.parseEnum(Genre.class, genres);
        for(Movie movie : movieDao.getMovies()) {
            if(movie.getTitle().equalsIgnoreCase(title) && movie.getGenres().equals(parsedGenres) && movie.getReleaseDate().equals(ValidationUtil.parseDate(releaseDate)))
                return movie;
        }
        return null;
    }


}
