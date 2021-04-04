package main.java.service;

import main.java.dao.ReviewDao;
import main.java.enums.Genre;
import main.java.enums.UserType;
import main.java.exceptions.MovieHasNoReviews;
import main.java.exceptions.MovieYetNotReleasedException;
import main.java.exceptions.MultipleReviewPerMovieException;
import main.java.model.Movie;
import main.java.model.Review;
import main.java.model.User;
import main.java.model.MovieReview;
import main.java.util.ValidationUtil;

import java.util.*;


public class ReviewService {

    private ReviewDao reviewDao;
    private UserService userService;
    private MovieService movieService;

    public ReviewService(UserService userService, MovieService movieService, ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
        this.userService = userService;
        this.movieService = movieService;
    }

    public void addReview(String email, String title, int rating) throws MovieYetNotReleasedException, MultipleReviewPerMovieException {
        Movie movie = movieService.getMovie(title);
        if(movie == null)
            throw new IllegalArgumentException("Invalid Movie");
        User user = userService.getUser(email);
        if(user == null)
            throw new IllegalArgumentException("Invalid user");

        ValidationUtil.parseRating(rating);

        if(movie.getReleaseDate().after(new Date()))
            throw new MovieYetNotReleasedException();

        List<String> moviesReviewedByUser = moviesReviewedByUser(user.getEmail());
        if(moviesReviewedByUser.contains(movie.getTitle()))
            throw new MultipleReviewPerMovieException();

        if(userService.isUserUpgradable(user, moviesReviewedByUser.size()))
            userService.upgradeUser(user);

        rating *= user.getRole().getReviewFactor();

        UserType userType = user.getRole().getUserType();

        Review review = new Review(movie.getTitle(), user.getEmail(), rating, userType);
        reviewDao.addReview(review);

    }

    public Integer getReview(String movieId) throws MovieHasNoReviews {
        for(Review review : reviewDao.getReviews()) {
            if(review.getMovieId().equalsIgnoreCase(movieId))
                return review.getRating();
        }
        throw new MovieHasNoReviews();
    }

    public List<String> moviesReviewedByUser(String userId) {
        List<String> movies = new ArrayList<>();
        for(Review review : reviewDao.getReviews()) {
            if(review.getUserId().equals(userId))
                movies.add(review.getMovieId());
        }
        return movies;
    }

    public Double getAvgMovieScore(String movieId) {
        int sum = 0;
        int count = 0;
        for(Review review : reviewDao.getReviews()) {
            if(review.getMovieId().equalsIgnoreCase(movieId)) {
                sum += review.getRating();
                count++;
            }
        }
        Double res = (double)sum/count;
        res = Math.round(res*100.0)/100.0;
        return res;
    }

    public Double getAvgReviewScoreInParticularYear(int year) {
        int sum = 0;
        int count = 0;
        Calendar calendar = new GregorianCalendar();
        for(Review review : reviewDao.getReviews()) {
            Movie movie = movieService.getMovie(review.getMovieId());
            calendar.setTime(movie.getReleaseDate());
            if(calendar.get(Calendar.YEAR) == year) {
                sum += review.getRating();
                count++;
            }
        }
        Double res = (double)sum/count;
        res = Math.round(res*100.0)/100.0;
        return res;
    }

    public List<MovieReview> getTopRatedMoviesByCriticInAGenre(String genre, int n) {
        Genre parsedGenre = ValidationUtil.parseEnum(Genre.class, genre);
        PriorityQueue<MovieReview> pQ = new PriorityQueue(n, MovieReview.getComparator());
        Map<Movie, Integer> movieReviewMap = new HashMap<>();
        List<MovieReview> res = new ArrayList<>();

        for(Review review : reviewDao.getReviews()) {
            Movie movie = movieService.getMovie(review.getMovieId());
            if(movie.getGenres().contains(parsedGenre) && review.getUserType().equals(UserType.CRITIC)) {
                if(movieReviewMap.containsKey(movie)){
                    movieReviewMap.put(movie, movieReviewMap.get(movie)+review.getRating());
                } else {
                    movieReviewMap.put(movie, review.getRating());
                }
            }
        }

        for(Map.Entry<Movie, Integer> entry : movieReviewMap.entrySet()) {
            pQ.add(new MovieReview(entry.getKey(), entry.getValue()));
        }

        while(pQ.size() > 0 && n > 0) {
            res.add(pQ.poll());
            n--;
        }

        return res;
    }


}
