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

    private int authorId;
    private String name;
    private Date dob;

    public Author(int authorId, String name, Date dob) {
        this.authorId = authorId;
        this.name = name;
        this.dob = dob;
    }

    public Author(int authorId, String name) {
        this(authorId, name, null);
    }

    public Author(String name){
        this(-1, name, null);
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
