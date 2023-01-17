package se.kth.pellebe.booksdb_2_java.model;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class BooksDbImpl implements BooksDbInterface{
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> booksCollection;
    private MongoCollection<Document> authorsCollection;
    private final List<Book> books;

    public BooksDbImpl(){
        books = new ArrayList<>();
    }

    @Override
    public boolean connect(String database, String user, String pwd) throws BooksDbException {
        MongoCredential credential = MongoCredential.createCredential(user, database, pwd.toCharArray());
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        builder.credential(credential);
        MongoClientSettings settings = builder.build();

        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase(database);
        booksCollection = mongoDatabase.getCollection("books");
        authorsCollection = mongoDatabase.getCollection("authors");

        return mongoClient != null;
    }

    @Override
    public void disconnect() throws BooksDbException {
        mongoClient.close();
    }

    @Override
    public List<Book> searchBooksByTitle(String title) throws BooksDbException {
        FindIterable find = booksCollection.find(eq("title", title));

        return transformBooks(find);
    }

    @Override
    public List<Book> searchBooksByISBN(String isbn) throws BooksDbException {
        FindIterable find = booksCollection.find(eq("isbn", isbn));

        return transformBooks(find);
    }

    @Override
    public List<Book> searchBooksByAuthor(String searchAuthor) throws BooksDbException {
        FindIterable find = booksCollection.find(eq("authors.name", searchAuthor));

        return transformBooks(find);
    }

    @Override
    public List<Book> searchBooksByGenre(String searchGenre) throws BooksDbException {
        FindIterable find = booksCollection.find(eq("genre", searchGenre));

        return transformBooks(find);
    }

    @Override
    public List<Book> searchBooksByRating(String searchRating) throws BooksDbException {
        Double rating = Double.parseDouble(searchRating);
        FindIterable find = booksCollection.find(eq("rating", rating));

        return transformBooks(find);
    }

    @Override
    public void insertBook(Book book) throws BooksDbException {
        Document document = new Document("isbn", book.getIsbn())
                .append("title", book.getTitle())
                .append("published", book.getPublished())
                .append("storyLine", book.getStoryLine())
                .append("rating", book.getRating())
                .append("genre", book.getGenre());


        List<Document> authors = new ArrayList<>();
        for(Author author : book.getAuthors()) {
            authors.add(new Document("name", author.getName()));
                    //.append("dob", author.getDob()));
        }
        document.append("authors", authors);

        booksCollection.insertOne(document);
    }

    @Override
    public void insertAuthor(Author author) throws BooksDbException{
        Document document = new Document("name", author.getName())
                .append("dob", author.getDob());

        List<Document> books = new ArrayList<>();
        for(Book book : author.getBooks()) {
            books.add(new Document("title", book.getTitle()));
        }
        document.append("books", books);

        authorsCollection.insertOne(document);
    }

    @Override
    public void updateBook(Book book) throws BooksDbException {
        booksCollection.updateOne(eq("isbn", book.getIsbn()),
                combine(set("rating", book.getRating())));
    }

    @Override
    public void removeBook(Book book) throws BooksDbException {
        booksCollection.deleteOne(eq("isbn", book.getIsbn()));
    }

    private List<Book> transformBooks(FindIterable find){
        books.clear();
        for (MongoCursor<Document> cursor = find.iterator(); cursor.hasNext();) {
            Document doc = cursor.next();
            Object[] values = doc.values().toArray();
            Book book = new Book(values[0].toString(), (String) values[1], (String) values[2],
                    (String) values[3], (Double) values[5],
                    Book.Genre.valueOf((String) values[6]));
            book.setStoryLine((String) values[4]);

            ArrayList<Document> authors = (ArrayList<Document>) values[7];
            for(int i = 0;i < authors.size();i++){
                Document authorDoc = authors.get(i);
                Object[] authorValues = authorDoc.values().toArray();
                Author author = new Author((String) authorValues[0]);
                book.addAuthor(author);
            }
            books.add(book);
        }

        return books;
    }
}
