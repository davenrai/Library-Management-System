import java.util.HashMap;

public abstract class User {
    private String username;
    private HashMap<Book, Integer> borrowedBooks;

    public User(String un) {
        this.username = un;
        this.borrowedBooks = new HashMap<>();
    }

    public String getUsername() {
        return this.username;
    }

    public void payPenalties(Member m) {
        m.balance = 0;
    }

    public HashMap<Book, Integer> getBorrowedBooks() {
        return this.borrowedBooks;
    }

    public void borrowBook(Book b) {
        this.borrowedBooks.put(b, 14); //14 days for borrowing books
    }

    public void returnBook(Book b) {
        this.borrowedBooks.remove(b);
    }
}
