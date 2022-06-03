package com.example.vzigo;

public class Feedbacks {
    Float ratings;
    String comment;

    public Feedbacks(Float ratings, String comment) {
        this.ratings = ratings;
        this.comment = comment;
    }

    public Float getRatings() {
        return ratings;
    }

    public void setRatings(Float ratings) {
        this.ratings = ratings;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
