import java.util.ArrayList;

public class Book {
    private String title;
    private String description;
    private int copiesAvailable;
    private String branch;
    private ArrayList<User> waitlist;

    public Book() {
        this.waitlist = new ArrayList<>();
        this.title = null;
        this.description = null;
        this.copiesAvailable = 0;
        this.branch = null;
    }

    public Book(String bookTitle, String bookDescription, int copies, String branchLocation) {
        this.title = bookTitle;
        this.description = bookDescription;
        this.copiesAvailable = copies;
        this.branch = branchLocation;
        this.waitlist = new ArrayList<>();
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateCopies(Integer newCopies) {
        this.copiesAvailable = newCopies;
    }

    public void updateBranch(String newBranch) {
        this.branch = newBranch;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getTitle() {
        return this.title;
    }

    public int getCopiesAvailable() {
        return this.copiesAvailable;
    }

    public void changeQuantity(int change) {
        this.copiesAvailable = this.copiesAvailable + change;
    }

    public ArrayList<User> getWaitlist() {
        return this.waitlist;
    }

    public void addUserToWaitlist(User u) {
        this.waitlist.add(u);
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }
}
