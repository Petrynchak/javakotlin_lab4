import java.io.*
import java.util.*

data class Book(val code: String, val title: String, val author: String, val year: Int, val copies: Int) : java.io.Serializable

object FileManager {
    private const val FILENAME = "library_data.ser"

    fun saveBooks(books: List<Book>) {
        ObjectOutputStream(FileOutputStream(FILENAME)).use { oos ->
            oos.writeObject(books)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun loadBooks(): List<Book> {
        val books = mutableListOf<Book>()
        try {
            ObjectInputStream(FileInputStream(FILENAME)).use { ois ->
                books.addAll(ois.readObject() as List<Book>)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return books
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    var books = mutableListOf<Book>()
    books.addAll(FileManager.loadBooks())

    var choice: Int
    do {
        println("1. Add a new book")
        println("2. Display the list of books")
        println("3. Search for books by author or title")
        println("0. Exit")
        print("Your choice: ")
        choice = scanner.nextInt()
        scanner.nextLine() // Clear the buffer

        when (choice) {
            1 -> addBook(books, scanner)
            2 -> displayBooks(books)
            3 -> searchBooks(books, scanner)
            0 -> {
                FileManager.saveBooks(books)
                println("Program has finished.")
            }
            else -> println("Invalid choice, please try again.")
        }
    } while (choice != 0)
}


fun addBook(books: MutableList<Book>, scanner: Scanner) {
    print("Enter book code: ")
    val code = scanner.nextLine()
    print("Enter book title: ")
    val title = scanner.nextLine()
    print("Enter author's last name: ")
    val author = scanner.nextLine()
    print("Enter publication year: ")
    val year = scanner.nextInt()
    print("Enter number of copies: ")
    val copies = scanner.nextInt()
    scanner.nextLine() // Clear the buffer

    val book = Book(code, title, author, year, copies)
    books.add(book)
    println("Book has been successfully added to the library.")
}

fun displayBooks(books: List<Book>) {
    println("List of books in the library:")
    books.forEach { println(it) }
}

fun searchBooks(books: List<Book>, scanner: Scanner) {
    print("Enter author's last name or part of the book title: ")
    val query = scanner.nextLine()
    val foundBooks = books.filter { it.author.contains(query, ignoreCase = true) || it.title.contains(query, ignoreCase = true) }
    if (foundBooks.isEmpty()) {
        println("No books found for your query.")
    } else {
        println("Found books:")
        foundBooks.forEach { println(it) }
    }
}
