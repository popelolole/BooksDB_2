package se.kth.pellebe.booksdb_2_java.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Representation of a book. A book contains an id, genre, and more, and also a set of authors.
 * 
 * @author pellebe@kth.se
 * @author eabraham@kth.se
 */
public class Book {
    public enum Genre { Crime, Mystery, Romance, Drama, Memoir, Fantasy}
    private String bookId;
    private String isbn; // should check format
    private String title;
    private String published;
    private String storyLine = "";
    private double rating;
    private Genre genre;
    private ArrayList<Author> authors;
    private static final Pattern ISBN_PATTERN =
            Pattern.compile("^\\d{9}[\\d|X]$");

    public static boolean isValidIsbn(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    public Book(String bookId, String isbn, String title, String published, double rating, Genre genre) {
        if(!isValidIsbn(isbn))
            throw new IllegalArgumentException("not a valid isbn");
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        this.rating = rating;
        this.genre = genre;
        authors = new ArrayList<>();
    }

    public Book(String bookId, String isbn, String title, String published, double rating) {
        this(bookId, isbn, title, published, rating, null);
    }

    public Book(String isbn, String title, String published, double rating, Genre genre) {
        this(null, isbn, title, published, rating, genre);
    }

    public Book(String isbn, String title, String published, double rating) {
        this(null, isbn, title, published, rating, null);
    }

    public Book(String isbn, String title, String published) {
        this(null, isbn, title, published, -1, null);
    }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getPublished() { return published; }
    public String getStoryLine() { return storyLine; }

    public double getRating() {
        return rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) { this.genre = genre; }

    public ArrayList<Author> getAuthors() {
        return (ArrayList<Author>) authors.clone();
    }

    public void addAuthor(Author author){
        authors.add(author);
    }

    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }

    public void setRating(double rating) {
        if(rating>5) rating=5;
        else if(rating<1) rating=1;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return title + ", " + isbn + ", " + published;
    }
}
