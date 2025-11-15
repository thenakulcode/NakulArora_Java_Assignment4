import java.io.*;
import java.util.*;

class Book implements Comparable<Book> {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean isIssued;

    public Book(int id, String t, String a, String c, boolean issued) {
        this.bookId = id;
        this.title = t;
        this.author = a;
        this.category = c;
        this.isIssued = issued;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isIssued() { return isIssued; }

    public void markAsIssued() { this.isIssued = true; }
    public void markAsReturned() { this.isIssued = false; }

    public void displayBookDetails() {
        System.out.println("ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Issued: " + (isIssued ? "Yes" : "No"));
    }

    @Override
    public int compareTo(Book b) {
        return this.title.compareToIgnoreCase(b.title);
    }

    @Override
    public String toString() {
        return bookId + "," + title + "," + author + "," + category + "," + isIssued;
    }
}

class Member {
    private int memberId;
    private String name;
    private String email;
    private List<Integer> issuedBooks = new ArrayList<>();

    public Member(int id, String n, String e) {
        this.memberId = id;
        this.name = n;
        this.email = e;
    }

    public int getMemberId() { return memberId; }
    public List<Integer> getIssuedBooks() { return issuedBooks; }

    public void addIssuedBook(int bookId) {
        issuedBooks.add(bookId);
    }

    public void returnIssuedBook(int bookId) {
        issuedBooks.remove(Integer.valueOf(bookId));
    }

    public void displayMemberDetails() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Books Issued: " + issuedBooks);
    }

    @Override
    public String toString() {
        return memberId + "," + name + "," + email + "," + issuedBooks.toString();
    }
}

class LibraryManager {
    Map<Integer, Book> books = new HashMap<>();
    Map<Integer, Member> members = new HashMap<>();

    File bookFile = new File("books.txt");
    File memberFile = new File("members.txt");

    public LibraryManager() {
        loadFromFile();
    }

    // ------------ FILE LOAD --------------
    public void loadFromFile() {
        try {
            if (bookFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(bookFile));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.split(",");
                    Book b = new Book(
                            Integer.parseInt(p[0]),
                            p[1], p[2], p[3],
                            Boolean.parseBoolean(p[4])
                    );
                    books.put(b.getBookId(), b);
                }
                br.close();
            }

            if (memberFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(memberFile));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.split(",");
                    Member m = new Member(
                            Integer.parseInt(p[0]), p[1], p[2]
                    );

                    if (p.length > 3 && p[3].length() > 2) {
                        String arr = p[3].replace("[", "").replace("]", "");
                        for (String s : arr.split(" ")) {
                            if (!s.isEmpty()) {
                                m.addIssuedBook(Integer.parseInt(s.trim()));
                            }
                        }
                    }
                    members.put(m.getMemberId(), m);
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Error loading file data.");
        }
    }

    // ------------ SAVE FILE --------------
    public void saveToFile() {
        try {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(bookFile));
            for (Book b : books.values()) bw1.write(b.toString() + "\n");
            bw1.close();

            BufferedWriter bw2 = new BufferedWriter(new FileWriter(memberFile));
            for (Member m : members.values()) bw2.write(m.toString() + "\n");
            bw2.close();

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    // ------------- ADD BOOK --------------
    public void addBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt(); sc.nextLine();

        if (books.containsKey(id)) {
            System.out.println("Book ID already exists.");
            return;
        }

        System.out.print("Enter Title: ");
        String t = sc.nextLine();
        System.out.print("Enter Author: ");
        String a = sc.nextLine();
        System.out.print("Enter Category: ");
        String c = sc.nextLine();

        Book b = new Book(id, t, a, c, false);
        books.put(id, b);
        saveToFile();

        System.out.println("Book added successfully.");
    }

    // ------------- ADD MEMBER --------------
    public void addMember(Scanner sc) {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt(); sc.nextLine();

        if (members.containsKey(id)) {
            System.out.println("Member ID already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String n = sc.nextLine();
        System.out.print("Enter Email: ");
        String e = sc.nextLine();

        Member m = new Member(id, n, e);
        members.put(id, m);
        saveToFile();

        System.out.println("Member added.");
    }

    // ------------- ISSUE BOOK --------------
    public void issueBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int bId = sc.nextInt();

        System.out.print("Enter Member ID: ");
        int mId = sc.nextInt();

        if (!books.containsKey(bId)) { System.out.println("Book not found."); return; }
        if (!members.containsKey(mId)) { System.out.println("Member not found."); return; }

        Book b = books.get(bId);
        Member m = members.get(mId);

        if (b.isIssued()) {
            System.out.println("Book already issued.");
            return;
        }

        b.markAsIssued();
        m.addIssuedBook(bId);
        saveToFile();
        System.out.println("Book issued successfully.");
    }

    // ------------- RETURN BOOK --------------
    public void returnBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int bId = sc.nextInt();

        if (!books.containsKey(bId)) {
            System.out.println("Invalid book ID.");
            return;
        }

        Book b = books.get(bId);
        if (!b.isIssued()) {
            System.out.println("Book is not issued.");
            return;
        }

        b.markAsReturned();

        for (Member m : members.values()) {
            if (m.getIssuedBooks().contains(bId)) {
                m.returnIssuedBook(bId);
                break;
            }
        }

        saveToFile();
        System.out.println("Book returned.");
    }

    // ------------- SEARCH BOOKS --------------
    public void searchBooks(Scanner sc) {
        sc.nextLine();
        System.out.print("Search text: ");
        String q = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(q)
                    || b.getAuthor().toLowerCase().contains(q)
                    || b.getCategory().toLowerCase().contains(q)) {
                b.displayBookDetails();
                System.out.println();
            }
        }
    }

    // ------------- SORT BOOKS --------------
    public void sortBooks(Scanner sc) {
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");
        int ch = sc.nextInt();

        List<Book> list = new ArrayList<>(books.values());

        if (ch == 1) {
            Collections.sort(list);
        } else {
            list.sort(Comparator.comparing(Book::getAuthor));
        }

        for (Book b : list) {
            b.displayBookDetails();
            System.out.println();
        }
    }
}


public class LibrarySystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryManager lm = new LibraryManager();

        while (true) {
            System.out.println("\n--- City Library Digital Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> lm.addBook(sc);
                case 2 -> lm.addMember(sc);
                case 3 -> lm.issueBook(sc);
                case 4 -> lm.returnBook(sc);
                case 5 -> lm.searchBooks(sc);
                case 6 -> lm.sortBooks(sc);
                case 7 -> {
                    System.out.println("Saving data... Goodbye!");
                    lm.saveToFile();
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
