import java.util.ArrayList;
import java.util.HashMap;

public class Member extends User {
    private String username;
    private HashMap<Book, Integer> borrowedBooks;
    private ArrayList<Book> userWaitlist;
    private HashMap<Book, Integer> bookExtend;
    protected double balance;


    public Member(String username) {
        super(username);
        this.balance = 0;
        this.userWaitlist = new ArrayList<>();
        this.bookExtend = new HashMap<>();
    }

    public void addBalance(double newBalance) {
        this.balance = this.balance + .5;
    }

    public void updateBalance(double newBalance) {
        this.balance = newBalance;
    }

    public ArrayList<Book> getWaitlist() {
        return this.userWaitlist;
    }

    public HashMap<Book, Integer> getBookExtend() {
        return this.bookExtend;
    }

    public void extendBook(Book b) {
        if (!(this.bookExtend.containsKey(b))) {
            this.bookExtend.put(b, 1);
            int newTime = this.getBorrowedBooks().get(b) + 14;
            this.getBorrowedBooks().put(b, 14);
            System.out.println("We've extended your book.");
        } else {
            int previous = this.bookExtend.get(b);
            if (previous >= 2) {
                System.out.println("Sorry. You cannot extend this book again.");
            } else {
                this.bookExtend.put(b, previous + 1);
                int newTime = this.getBorrowedBooks().get(b) + 14;
                getBorrowedBooks().put(b, newTime);
                System.out.println("We have extended your book.");
            }
        }
    }

}
