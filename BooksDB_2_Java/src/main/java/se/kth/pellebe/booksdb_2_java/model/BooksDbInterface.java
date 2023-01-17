package se.kth.pellebe.booksdb_2_java.model;

import java.util.List;

/**
 * This interface declares methods for querying a Books database.
 * Different implementations of this interface handles the connection and
 * queries to a specific DBMS and database, for example a MySQL or a MongoDB
 * database.
 *
 * NB! The methods in the implementation must catch the SQL/MongoDBExceptions thrown
 * by the underlying driver, wrap in a BooksDbException and then re-throw the latter
 * exception. This way the interface is the same for both implementations, because the
 * exception type in the method signatures is the same. More info in BooksDbException.java.
 * 
 * @author pellebe@kth.se
 * @author eabraham@kth.se
 */
public interface BooksDbInterface {
    
    /**
     * Connect to the database.
     * @param database
     * @return true on successful connection.
     */
    public boolean connect(String database, String user, String pwd) throws BooksDbException;
    
    public void disconnect() throws BooksDbException;
    
    public List<Book> searchBooksByTitle(String title) throws BooksDbException;

    public List<Book> searchBooksByISBN(String title) throws BooksDbException;

    public List<Book> searchBooksByAuthor(String searchAuthor) throws BooksDbException;
    public List<Book> searchBooksByGenre(String searchGenre) throws BooksDbException;
    public List<Book> searchBooksByRating(String searchRating) throws BooksDbException;

    public void insertBook(Book book) throws BooksDbException;

    public void insertAuthor(Author author) throws BooksDbException;

    public void updateBook(Book book) throws BooksDbException;

    public void removeBook(Book book) throws BooksDbException;
}
