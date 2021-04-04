package main.java.dao;

import main.java.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MovieDao {

    private Map<String, Movie> movieMap;

    public MovieDao() {
        this.movieMap = new ConcurrentHashMap<>();
    }

    public void addMovie(Movie movie) {
        movieMap.putIfAbsent(movie.getTitle(), movie);
    }

    public Movie getMovie(String movieId) {
        return movieMap.get(movieId);
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movieMap.values());
    }

}
