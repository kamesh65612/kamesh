package Libaray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryManagementSystem {
    public JFrame frame;
    public JTable table;
    public JTextField txtTitle, txtAuthor, txtQuantity;
    public JTextField txtSearchTitle;
    public JTextField txtMemberName, txtMemberAge, txtMemberMobile;
    public List<Book> books;
    public Connection conn;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        initialize();
        connectToDatabase();
        loadBooksFromDatabase();
    }

    public void connectToDatabase() {
        try {
            
            String url = "jdbc:mysql://localhost:3306/villain_kg";
            String username = "root";
            String password = "root";
            
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database connection failed!");
        }
    }

    public void loadBooksFromDatabase() {
        books.clear();
        String query = "SELECT * FROM books";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                books.add(new Book(title, author, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadBooks();
    }

    public void initialize() {
        frame = new JFrame("Library Management System");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new FlowLayout());

        JLabel lblTitle = new JLabel("Title:");
        panel.add(lblTitle);

        txtTitle = new JTextField();
        panel.add(txtTitle);
        txtTitle.setColumns(10);

        JLabel lblAuthor = new JLabel("Author:");
        panel.add(lblAuthor);

        txtAuthor = new JTextField();
        panel.add(txtAuthor);
        txtAuthor.setColumns(10);

        JLabel lblQuantity = new JLabel("Quantity:");
        panel.add(lblQuantity);

        txtQuantity = new JTextField();
        panel.add(txtQuantity);
        txtQuantity.setColumns(10);

        JButton btnAdd = new JButton("Add Book");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        panel.add(btnAdd);

        JButton btnUpdate = new JButton("Update Book");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });
        panel.add(btnUpdate);

        JButton btnDelete = new JButton("Delete Book");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });
        panel.add(btnDelete);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        frame.getContentPane().add(actionPanel, BorderLayout.SOUTH);

        JLabel lblSearch = new JLabel("Search by Title:");
        actionPanel.add(lblSearch);

        txtSearchTitle = new JTextField();
        actionPanel.add(txtSearchTitle);
        txtSearchTitle.setColumns(10);

        JLabel lblMemberName = new JLabel("Member Name:");
        actionPanel.add(lblMemberName);

        txtMemberName = new JTextField();
        actionPanel.add(txtMemberName);
        txtMemberName.setColumns(10);

        JLabel lblMemberAge = new JLabel("Age:");
        actionPanel.add(lblMemberAge);

        txtMemberAge = new JTextField();
        actionPanel.add(txtMemberAge);
        txtMemberAge.setColumns(5);

        JLabel lblMemberMobile = new JLabel("Mobile:");
        actionPanel.add(lblMemberMobile);

        txtMemberMobile = new JTextField();
        actionPanel.add(txtMemberMobile);
        txtMemberMobile.setColumns(10);

        JButton btnBuy = new JButton("Buy Book");
        btnBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buyBook();
            }
        });
        actionPanel.add(btnBuy);

        JButton btnReturn = new JButton("Return Book");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
        actionPanel.add(btnReturn);

        loadBooks();
    }

    public void loadBooks() {
        String[][] data = new String[books.size()][4];
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthor();
            data[i][2] = String.valueOf(book.getQuantity());
            data[i][3] = String.valueOf(book.getQuantity() == 0 ? "Out of Stock" : "Available");
        }

        String[] columnNames = {"Title", "Author", "Quantity", "Status"};
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public void addBook() {
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());

        String query = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearFields();
        loadBooksFromDatabase();
    }

    public void updateBook() {
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());

        String query = "UPDATE books SET author = ?, quantity = ? WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, author);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearFields();
        loadBooksFromDatabase();
    }

    public void deleteBook() {
        String title = txtTitle.getText();

        String query = "DELETE FROM books WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearFields();
        loadBooksFromDatabase();
    }

    public void buyBook() {
        String searchTitle = txtSearchTitle.getText();
        String memberName = txtMemberName.getText();
        String memberAge = txtMemberAge.getText();
        String memberMobile = txtMemberMobile.getText();

        if (searchTitle.isEmpty() || memberName.isEmpty() || memberAge.isEmpty() || memberMobile.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the fields.");
            return;
        }

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(searchTitle)) {
                if (book.getQuantity() > 0) {
                    book.setQuantity(book.getQuantity() - 1);
                    updateBookQuantityInDatabase(book);
                    loadBooks();
                    JOptionPane.showMessageDialog(frame, "Book bought successfully.\nMember Name: " + memberName + "\nAge: " + memberAge + "\nMobile: " + memberMobile);
                } else {
                    JOptionPane.showMessageDialog(frame, "Book is out of stock.");
                }
                break;
            }
        }
    }

    public void returnBook() {
        String searchTitle = txtSearchTitle.getText();

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(searchTitle)) {
                book.setQuantity(book.getQuantity() + 1);
                updateBookQuantityInDatabase(book);
                loadBooks();
                JOptionPane.showMessageDialog(frame, "Book returned successfully.");
                break;
            }
        }
    }

    public void updateBookQuantityInDatabase(Book book) {
        String query = "UPDATE books SET quantity = ? WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, book.getQuantity());
            pstmt.setString(2, book.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearFields() {
        txtTitle.setText("");
        txtAuthor.setText("");
        txtQuantity.setText("");
        txtSearchTitle.setText("");
        txtMemberName.setText("");
        txtMemberAge.setText("");
        txtMemberMobile.setText("");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LibraryManagementSystem window = new LibraryManagementSystem();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

class Book {
    private String title;
    private String author;
    private int quantity;

    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}