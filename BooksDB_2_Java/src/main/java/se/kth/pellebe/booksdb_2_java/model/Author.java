package se.kth.pellebe.booksdb_2_java.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents an author in a book database.
 * An author has an id, a name and a date of birth.
 *
 * @author pellebe@kth.se
 * @author eabraham@kth.se
 */

public class Author {

    private String name;
    private String dob;
    private ArrayList<Book> books;

    public Author(String name, String dob) {
        this.name = name;
        this.dob = dob;
        books = new ArrayList<>();
    }

    public Author(String name) {
        this(name, null);
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getBooks() {
        return (ArrayList<Book>) books.clone();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString() {
        return name;
    }
}
