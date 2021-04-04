package test.java;

import main.java.dao.MovieDao;
import main.java.dao.ReviewDao;
import main.java.dao.UserDao;
import main.java.enums.UserType;
import main.java.exceptions.MovieYetNotReleasedException;
import main.java.exceptions.MultipleReviewPerMovieException;
import main.java.model.MovieReview;
import main.java.service.MovieService;
import main.java.service.ReviewService;
import main.java.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.spy;


public class MovieReviewSystemTest {

    private MovieService movieService;
    private UserService userService;
    private ReviewService reviewService;
    private UserDao userDao;
    private MovieDao movieDao;
    private ReviewDao reviewDao;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        userDao = spy(new UserDao());
        movieDao = spy(new MovieDao());
        reviewDao = spy(new ReviewDao());
        movieService = new MovieService(movieDao);
        userService = new UserService(userDao);
        reviewService = new ReviewService(userService, movieService, reviewDao);
        prepareTestData();
    }


    @Test
    public void testAddValidUser() {

        Assert.assertEquals(3, userDao.getUsers().size());
    }

    @Test
    public void testAddDuplicateUser() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(String.format("User with email %s already exists", "arianagrande@gmail.com"));

        userService.addUser("Ariana", null, "arianagrande@gmail.com");
    }

    @Test
    public void testAddUserWithNoName() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("User name cannot be null or empty");

        userService.addUser("", "01-01-1989", "arianagrande@gmail.com");
    }

    @Test
    public void testAddUserWithNoEmail() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("User email cannot be null or empty");

        userService.addUser("Ariana", "01-01-1989", null);
    }

    @Test
    public void testAddValidMovie() {
        Assert.assertEquals(6, movieDao.getMovies().size());

    }

    @Test
    public void testAddMovieWithNoTitle() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Movie title cannot be null or empty");

        movieService.addMovie(null, Arrays.asList("Comedy"), null, null,"20-06-2018", null, null);
    }

    @Test
    public void testAddValidReview() throws Exception {
        reviewService.addReview("arianagrande@gmail.com", "Stree", 5);
        reviewService.addReview("salmanbhai@yahoo.com", "Stree", 9);
        reviewService.addReview("salmanbhai@yahoo.com", "Newton", 5);
        reviewService.addReview("salmanbhai@yahoo.com", "BadhaaiHo", 8);
        reviewService.addReview("salmanbhai@yahoo.com", "LunchBox", 7);

        Assert.assertEquals(5, reviewDao.getReviews().size());
        Assert.assertEquals(Double.valueOf(7.00d), reviewService.getAvgMovieScore("Stree"));
        Assert.assertEquals(UserType.CRITIC, userService.getUser("salmanbhai@yahoo.com").getRole().getUserType());
        Assert.assertEquals(Integer.valueOf(14), reviewService.getReview("LunchBox"));
    }

    @Test
    public void testAddDuplicateReview() throws Exception {
        expectedException.expect(MultipleReviewPerMovieException.class);
        expectedException.expectMessage("A user cannot review same movie twice");

        reviewService.addReview("arianagrande@gmail.com", "Stree", 5);
        reviewService.addReview("salmanbhai@yahoo.com", "Stree", 9);
        reviewService.addReview("salmanbhai@yahoo.com", "Stree", 5);
    }

    @Test
    public void testAddUpcomingMovieReview() throws Exception {
        expectedException.expect(MovieYetNotReleasedException.class);
        expectedException.expectMessage("Cannot review an upcoming movie");

        reviewService.addReview("arianagrande@gmail.com", "Stree", 5);
        reviewService.addReview("salmanbhai@yahoo.com", "Stree", 9);
        reviewService.addReview("salmanbhai@yahoo.com", "Radhey", 10);
    }

    @Test
    public void testAvgReviewScoreInAYear() throws Exception {

        reviewService.addReview("arianagrande@gmail.com", "Newton", 8);
        reviewService.addReview("salmanbhai@yahoo.com", "Padmaavat", 9);
        reviewService.addReview("salmanbhai@yahoo.com", "Newton", 5);

        Double expectedRes = (double) (8+9+5)/3;
        expectedRes = Math.round(expectedRes*100.0)/100.0;
        Assert.assertEquals(expectedRes, reviewService.getAvgReviewScoreInParticularYear(2017));
    }

    @Test
    public void testTopRatedMoviesByCriticInAGenre() throws Exception {

        reviewService.addReview("arianagrande@gmail.com", "Newton", 8);
        reviewService.addReview("salmanbhai@yahoo.com", "Stree", 8);
        reviewService.addReview("salmanbhai@yahoo.com", "BadhaaiHo", 9);
        reviewService.addReview("salmanbhai@yahoo.com", "Padmaavat", 9);
        reviewService.addReview("salmanbhai@yahoo.com", "Newton", 6);
        reviewService.addReview("salmanbhai@yahoo.com", "LunchBox", 7);
        reviewService.addReview("shahrukh123@hotmail.com", "Stree", 9);
        reviewService.addReview("shahrukh123@hotmail.com", "BadhaaiHo", 10);
        reviewService.addReview("shahrukh123@hotmail.com", "Padmaavat", 8);
        reviewService.addReview("shahrukh123@hotmail.com", "Newton", 7);
        reviewService.addReview("shahrukh123@hotmail.com", "LunchBox", 8);

        List<String> res = reviewService.getTopRatedMoviesByCriticInAGenre("Drama", 5);
        Assert.assertEquals(2, res.size());
        Assert.assertEquals("Newton", res.get(0));
        Assert.assertEquals("LunchBox", res.get(1));
    }

    private void prepareTestData() {

        userService.addUser("Ariana", "01-01-1989", "arianagrande@gmail.com");
        userService.addUser("Salman", null, "salmanbhai@yahoo.com");
        userService.addUser("Shahrukh", null, "shahrukh123@hotmail.com");

        movieService.addMovie("Stree", Arrays.asList("Comedy"), null, null,"20-06-2018", null, null);
        movieService.addMovie("Newton", Arrays.asList("Drama"), null, null,"20-06-2017", null, null);
        movieService.addMovie("Padmaavat", Arrays.asList("Drama"), null, null,"20-06-2017", null, null);
        movieService.addMovie("BadhaaiHo", Arrays.asList("Comedy"), null, null,"20-06-2019", null, null);
        movieService.addMovie("LunchBox", Arrays.asList("Drama"), null, null,"20-06-2016", null, null);
        movieService.addMovie("Radhey", Arrays.asList("Drama"), null, null,"20-06-2021", null, null);
    }
}
