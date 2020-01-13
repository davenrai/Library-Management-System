import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

public class Main {
    static Date d = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat("E MM/dd/yyyy HH:mm");
    static Library l = new Library();
    static Scanner obj = new Scanner(System.in);
    static Boolean running = true;
    static Member logged = null;

    public static void main(String[] args) {
        while (running) {
            intro();
        }

    }

    public static void intro() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Today's Date: " + sdf.format(d));
        System.out.println("What is your user type? (Please enter 's' for Staff or 'm' for Member)");
        String userType = myObj.nextLine();
        if (userType.equals("s")) {
            staffOptions();
        } else if (userType.equals("m")) {
            memberOptions();
        } else {
            System.out.println("You did not enter a correct response.");
            intro();
        }
    }

    public static void staffOptions() {
        System.out.println("***************\n  Staff Menu\n" + "***************" + "\n" +
                "Enter a number corresponding to your choice." + "\n 1.) Add a book." +
                "\n 2.) Update fields of Book \n 3.) Pay Penalties \n 4.) Exit to Login Screen");
        Integer input = obj.nextInt();
        switch (input) {
            case 1:
                // add book
                addBook();
                break;
            case 2:
                updateBook();
                break;
            case 3:
                // pay penalties
                payPenalties();
                break;
            case 4:
                // exit to staff options
                intro();
        }
    }

    public static void addBook() {
        Scanner input = new Scanner(System.in);
        Scanner intInput = new Scanner(System.in);
        System.out.println("\nPlease enter book title");
        String title = input.nextLine();
        System.out.println("\nPlease enter a book description");
        String des = input.nextLine();
        System.out.println("\nPlease enter the number of copies available.");
        Integer copies = intInput.nextInt();
        System.out.println("\nPlease enter the branch.");
        String branch = input.nextLine();
        Book b = new Book(title, des, copies, branch);
        l.addBook(b);
        System.out.println("Book successfully added.");
        staffOptions();
    }

    public static void updateBook() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the book title for which you want to update or 'exit' to EXIT the system.");
        String title = input.nextLine();
        if (title.equals("exit")) {
            staffOptions();
        }
        Book b = l.locateBookByTitle(title);
        if (b == null) {
            System.out.println("That Book does not exist in our System.");
            staffOptions();
        } else {
            System.out.println("We've located your book.\n Please indicate which field you want to update.\n" +
                    "1 for title, 2 for description, 3 for number of copies, 4 for branch, 5 to EXIT");
            switch (input.nextInt()) {
                case 1:
                    Scanner bTitle = new Scanner(System.in);
                    System.out.println("Enter the new book title");
                    String bookTitle = bTitle.nextLine();
                    b.updateTitle(bookTitle);
                    System.out.println("\nWe've happily updated your book title to: " + b.getTitle());
                    updateBook();
                    break;
                case 2:
                    Scanner bDes = new Scanner(System.in);
                    System.out.println("Enter the new book description");
                    String bookDescription = bDes.nextLine();
                    b.updateDescription(bookDescription);
                    System.out.println("\nWe've updated your book description to: ");
                    updateBook();
                    break;
                case 3: // change number of copies of book
                    Scanner userInput = new Scanner(System.in);
                    System.out.println("\nEnter the new number of copies");
                    Integer newCopies = userInput.nextInt();
                    b.updateCopies(newCopies);
                    System.out.println("We've updated your book copies to: " + b.getCopiesAvailable());
                    updateBook();
                    break;
                case 4:
                    // change branch location of book
                    Scanner branchInput = new Scanner(System.in);
                    System.out.println("\nEnter the new branch location");
                    String newBranch = branchInput.nextLine();
                    b.updateBranch(newBranch);
                    System.out.println("We;ve updated your book branch location to: " + b.getBranch());
                    updateBook();
                case 5:
                    System.out.println("Exiting to Main Menu...");
                    staffOptions();
                    break;
            }
        }
    }

    private static void payPenalties() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("\nTo pay penalties for a user, Please enter the member's username");
        String username = userInput.nextLine();
        User u1 = l.locateUserByUsername(username);
        if (u1 == null) {
            System.out.println("Sorry. That user is not in our system.");
            staffOptions();
        } else {
            ((Member) u1).updateBalance(0);
            System.out.println("The user's new balance is now 0.");
            staffOptions();
        }
    }


    private static void memberOptions() {
        Scanner input = new Scanner(System.in);
        System.out.println("************\n Member Menu\n************* \nEnter a number corresponding to your choice." +
                "\n1.) Sign Up/Sign In \n2.) Search Books In Library\n" +
                "3.) Return Book\n4.) Extend Book Return\n5.) Exit");
        int userInput = input.nextInt();
        switch (userInput) {
            case 1: // sign-in/up
                memberLogin();
                break;
            case 2: // search books in library
                searchBooks();
                break;
            case 3: // return book
                returnBook();
                break;
            case 4: // extend book return
                extendBook();
                break;
            case 5: // exit
                System.out.println("Returning to Main Menu...");
                intro();
                break;
        }
    }

    private static void memberLogin() {
        Scanner sn = new Scanner(System.in);
        System.out.println("Hello. To Login or Sign-up, please enter your library username.");
        String username = sn.nextLine();
        User u = l.locateUserByUsername(username);
        if (u == null) {
            System.out.println("We do not have you in our System...\nAdding you to the system.");
            Member m = new Member(username);
            l.addUser(m);
            System.out.println("\nMember added to system. Welcome " + username);

        } else {
            System.out.println("Welcome back " + u.getUsername());
        }
        logged = l.locateUserByUsername(username);
        // Check to see if user is on waitlist for a book then show a message to user if book is available.
        System.out.println("Scanning user waitlist....");
        scanWaitlist(logged);
        System.out.println("Scanning finished.\nChecking for late books..\n");
        addPenalty(logged);
        lateBooks(logged);
        memberOptions();
    }

    private static void scanWaitlist(Member u) {
        Scanner sn = new Scanner(System.in);
        for (int i = 0; i < u.getWaitlist().size(); i++) {
            Book b = u.getWaitlist().get(i);
            if (b.getWaitlist().get(0).equals(u) && b.getCopiesAvailable() > 0) {
                System.out.println("\nA book is available for you to check out!\nWould you like to check out:" +
                        b.getTitle() + "? Y/N");
                String input = sn.nextLine();
                if (input.equals("N") || input.equals("n")) {
                    System.out.println("We will remove you from the waitlist.");
                    b.getWaitlist().remove(i);
                    u.getWaitlist().remove(i);
                } else if (input.equals("Y") || input.equals("y")) {
                    System.out.println("\nChecking out book...");
                    b.changeQuantity(-1);
                    u.borrowBook(b);
                }
            }
            System.out.println("You have no more books in your waitlist.");
        }

    }

    private static void addPenalty(Member m) {
        for (Map.Entry<Book, Integer> entry : m.getBorrowedBooks().entrySet()) {
            Book b = entry.getKey();
            if (m.getBorrowedBooks().get(b) <= 0) {
                System.out.println("\nPlease return the following book: " + b.getTitle() +
                        "\n Added 50 cents penalty to your account!\n");
                m.addBalance(.5);
            }
        }
    }

    private static void lateBooks(Member m) {
        for (Map.Entry<Book, Integer> entry : m.getBorrowedBooks().entrySet()) {
            Book b = entry.getKey();
            if (m.getBorrowedBooks().get(b) <= 0) {
                System.out.println("Book: " + b.getTitle() + " is late. Please return it.");
            } else if (m.getBorrowedBooks().get(b) <= 2 && m.getBorrowedBooks().get(b) > 0) {
                System.out.println("Book: " + b.getTitle() + "is due soon.");
            }
        }
    }

    private static void searchBooks() {
        Scanner sn = new Scanner(System.in);
        System.out.println("Please enter the title of the book you would like to search for\nOr to exit, " +
                "please enter 'exit'");
        String title = sn.nextLine();
        if (title.equals("exit") || title.equals("EXIT") || title.equals("Exit")) {
            memberOptions();
        }
        Book b = l.locateBookByTitle(title);
        if (b == null) {
            System.out.println("Sorry. This book is not in our system.");
            memberOptions();
        } else {
            System.out.println("We've located your book. Would you like to request it from our library? Y/N");
            String response = sn.nextLine();
            if (response.equals("Y") || response.equals("y")) {
                requestBook(b);
            } else {
                memberOptions();
            }
        }
    }

    private static void requestBook(Book b) {
        l.borrowBook(logged, b);
        System.out.println("Returning to Member Menu...");
        memberOptions();
    }

    private static void returnBook() {
        Scanner sn = new Scanner(System.in);
        System.out.println("Please enter the book title you would like to return.");
        String title = sn.nextLine();
        Book b = l.locateBookByTitle(title);
        if (b == null) {
            System.out.println("You've entered the wrong book title.");
            memberOptions();
        } else {
            if (logged == null) {
                System.out.println("Please login first.");
                memberOptions();
            }
            l.returnBook(logged, b);
            memberOptions();
        }
    }

    private static void extendBook() {
        Scanner sn = new Scanner(System.in);
        System.out.println("Please enter the book title you would like to return.");
        String title = sn.nextLine();
        Book b = l.locateBookByTitle(title);
        if (b == null) {
            System.out.println("You've entered the wrong book title.");
            memberOptions();
        } else {
            logged.extendBook(b);
            memberOptions();
        }
    }
}
