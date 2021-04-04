package main.java.dao;

import main.java.model.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReviewDao {

    private Map<String, Review> reviewMap;

    public ReviewDao() {
        reviewMap = new ConcurrentHashMap<>();
    }

    public void addReview(Review review) {
        reviewMap.putIfAbsent(review.getId(), review);
    }

    public List<Review> getReviews() {
        return new ArrayList<>(reviewMap.values());
    }

}
