import java.io.*;
import java.util.*;

class Book implements Serializable {
    private String code;
    private String title;
    private String author;
    private int year;
    private int copies;

    public Book(String code, String title, String author, int year, int copies) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.year = year;
        this.copies = copies;
    }

    // Getters and setters

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getCopies() {
        return copies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", copies=" + copies +
                '}';
    }
}

class FileManager {
    private static final String FILENAME = "library_data.ser";

    public static void saveBooks(List<Book> books) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            books = (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }
}

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        books = FileManager.loadBooks();
        int choice;
        do {
            System.out.println("1. Add a new book");
            System.out.println("2. Display the list of books");
            System.out.println("3. Search for books by author or title");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    displayBooks();
                    break;
                case 3:
                    searchBooks();
                    break;
                case 0:
                    FileManager.saveBooks(books);
                    System.out.println("Program has finished.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 0);
    }

    private static void addBook() {
        System.out.print("Enter book code: ");
        String code = scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author's last name: ");
        String author = scanner.nextLine();
        System.out.print("Enter publication year: ");
        int year = scanner.nextInt();
        System.out.print("Enter number of copies: ");
        int copies = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        Book book = new Book(code, title, author, year, copies);
        books.add(book);
        System.out.println("Book has been successfully added to the library.");
    }

    private static void displayBooks() {
        System.out.println("List of books in the library:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void searchBooks() {
        System.out.print("Enter author's last name or part of the book title: ");
        String query = scanner.nextLine();
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().contains(query) || book.getTitle().contains(query)) {
                foundBooks.add(book);
            }
        }
        if (foundBooks.isEmpty()) {
            System.out.println("No books found for your query.");
        } else {
            System.out.println("Found books:");
            for (Book book : foundBooks) {
                System.out.println(book);
            }
        }
    }
}

