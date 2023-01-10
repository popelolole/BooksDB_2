package se.kth.pellebe.booksdb_2_java.model;

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

    public Author(String name, String dob) {
        this.name = name;
        this.dob = dob;
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

    @Override
    public String toString() {
        return name;
    }
}
