package main.java.model;

import main.java.enums.Genre;
import main.java.enums.Language;

import java.util.Date;
import java.util.List;

public class Movie {
    private String title;
    private List<Genre> genres;
    private Language language;
    private Date releaseDate;
    private String desc;
    private List<String> actors;
    private List<String> directors;

    private Movie(MovieBuilder movieBuilder) {
        this.title = movieBuilder.title;
        this.genres = movieBuilder.genres;
        this.language = movieBuilder.language;
        this.releaseDate = movieBuilder.releaseDate;
        this.desc = movieBuilder.desc;
        this.actors = movieBuilder.actors;
        this.directors = movieBuilder.directors;
    }

    public String getTitle() {
        return title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Language getLanguage() {
        return language;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getActors() {
        return actors;
    }

    public List<String> getDirectors() {
        return directors;
    }

    @Override
    public boolean equals(Object o) {
        Movie movie = null;
        if(o instanceof Movie)
            movie = (Movie) o;
        else
            return false;
        return (this.getTitle().equalsIgnoreCase(movie.getTitle()));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static class MovieBuilder {

        private String title;
        private List<Genre> genres;
        private Language language;
        private Date releaseDate;
        private String desc;
        private List<String> actors;
        private List<String> directors;

        public MovieBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder withGenre(List<Genre> genres) {
            this.genres = genres;
            return this;
        }

        public MovieBuilder withLanguage(Language language) {
            this.language = language;
            return this;
        }

        public MovieBuilder withReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public MovieBuilder withDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public MovieBuilder withActors(List<String> actors) {
            this.actors = actors;
            return this;
        }

        public MovieBuilder withDirectors(List<String> directors) {
            this.directors = directors;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }


}
