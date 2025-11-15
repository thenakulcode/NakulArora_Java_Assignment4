City Library Digital Management System

    A simple Java-based library management system made as part of the Java Programming Assignment (Semester 3).
    The project handles basic library operations like adding books, managing members, issuing/returning books, and storing all data using file handling.

📌 Features

    Add new books

    Add new members

    Issue and return books

    Search books by title, author, or category

    Sort books by title or author

    File-based data storage using:

    BufferedReader

    BufferedWriter

    FileReader

    FileWriter

    Uses Java Collections Framework:

    Map for books & members

    List for issued books

    Comparator & Comparable for sorting

📁 Files Created

    The application automatically generates:

    books.txt — Stores all book records

    members.txt — Stores all member records

    Data is saved every time a change is made.

📂 Project Structure

    LibrarySystem.java     → Contains all classes (Book, Member, LibraryManager, Main)
    books.txt              → Auto-generated, stores book data
    members.txt            → Auto-generated, stores member data

🛠️ How to Run

    Clone the repository

    Open the project in any Java-supported IDE (VS Code / IntelliJ / Eclipse)

    Compile the file:

    javac LibrarySystem.java


    Run the program:

    java LibrarySystem

📸 Sample Menu

    --- City Library Digital Management System ---
    1. Add Book
    2. Add Member
    3. Issue Book
    4. Return Book
    5. Search Books
    6. Sort Books
    7. Exit

✔️ Requirements Covered

File Handling (text-based storage)

Collections Framework (Map, List)

Searching & sorting using Comparable/Comparator

Input validation basics

Persistent data loading and saving

👨‍💻 Author

    Nakul
    (Java Programming – Assignment 4)
