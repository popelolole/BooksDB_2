package se.kth.pellebe.booksdb_2_java.model;

import java.util.List;

public class BooksDbImpl implements BooksDbInterface{

    @Override
    public boolean connect(String database, String user, String pwd) throws BooksDbException {
        return false;
    }

    @Override
    public void disconnect() throws BooksDbException {

    }

    @Override
    public List<Book> searchBooksByTitle(String title) throws BooksDbException {
        return null;
    }

    @Override
    public List<Book> searchBooksByISBN(String title) throws BooksDbException {
        return null;
    }

    @Override
    public List<Book> searchBooksByAuthor(String searchAuthor) throws BooksDbException {
        return null;
    }

    @Override
    public List<Book> searchBooksByGenre(String searchGenre) throws BooksDbException {
        return null;
    }

    @Override
    public List<Book> searchBooksByRating(String searchRating) throws BooksDbException {
        return null;
    }

    @Override
    public void insertBook(Book book) throws BooksDbException {

    }

    @Override
    public void updateBook(Book book) throws BooksDbException {

    }

    @Override
    public void removeBook(Book book) throws BooksDbException {

    }
}
