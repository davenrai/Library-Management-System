import java.util.*;

public class Library {
    private List<Book> inventory;
    private List<Member> users;

    public Library() {
        this.inventory = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addBook(Book b) {
        this.inventory.add(b);
    }

    public void addUser(Member u) {
        if (!(this.users.contains(u))) {
            this.users.add(u);
        }
    }

    public Book locateBookByTitle(String title) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getTitle().equals(title)) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public Member locateUserByUsername(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    public void borrowBook(User u, Book b) {
        if (((Integer) b.getCopiesAvailable()) > 0) {
            if (((Integer) b.getWaitlist().size()).equals(0)) {
                u.borrowBook(b);
                b.changeQuantity(-1);
                System.out.println("You have borrowed this book. Check-out is in 14 days.");
            } else if (b.getWaitlist().get(0).equals(u)) { // there is a waitlist and user is next
                u.borrowBook(b);
                b.changeQuantity(-1);
                b.getWaitlist().remove(0);
                System.out.println("We have taken you off the waitlist. You have borrowed this book. " +
                        "Check-out is in 14 days.");
            } else {
                // user is not next up in waitlist
                b.getWaitlist().add(u);
                System.out.println("Sorry, no more copies are available. We've added you onto the waitlist. ");
            }
        } else { // if copies available is 0.
            b.getWaitlist().add(u);
            System.out.println("Sorry, no more copies are available. We've added you onto the waitlist.");
        }
    }

    public void returnBook(User u, Book b) {
        if(u.getBorrowedBooks().containsKey(b)) {
            u.returnBook(b);
            b.changeQuantity(1);
            System.out.println("\nYour book has been returned. Thank you.\n");
        }
        else{
            System.out.println("You have not checked out this book.");
        }
    }
}
