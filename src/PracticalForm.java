import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//Bongumusa Mthembu
public class PracticalForm extends JFrame {
    private JButton homeButton;
    private JLabel moduleNameLabel;
    private JLabel usernameLabel;
    private JPanel practicalForm;
    private JPanel practicalSubPanel;
    private JPanel uploadPanel;
    private JPanel viewPanel;
    private JPanel footerPanel;
    private JPanel navPanel;
    private JPanel practicalFormPanel;
    private JButton bookLab1;
    private JButton bookLab2;
    private JButton chooseAFileButton;
    private JLabel feedBackLabel;
    private JButton uploadButton;
    private JLabel studentNumber1;
    private JTextField lab1Textfield;
    private JTextField lab2Textfield;

    public void setLabel(String text) {
        moduleNameLabel.setText(text);
    }

    public String getLabel() {
        return moduleNameLabel.getText();
    }

    public void setUsernameLabel(String txt) {
        usernameLabel.setText(txt);
    }

    public String getNameLabel() {
        return usernameLabel.getText();
    }

    public void setStudentNumber1(String txt) {
        studentNumber1.setText(txt);
    }
    public String pracNumber=null;

    public PracticalForm() {
        setContentPane(practicalFormPanel);
        setVisible(true);
        setSize(800, 440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setUsernameLabel();
        //moduleNameLabel.setText(getLabel()+);

        chooseAFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int response = fileChooser.showSaveDialog(null);
                String studentNumber = studentNumber1.getText();  // Replace with the actual student number
                String moduleName = moduleNameLabel.getText();  // Replace with the actual module name

                if (response == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    if (selectedDirectory.isDirectory()) {
                        String url = "jdbc:mysql://localhost:3306/ukznce";
                        String username = "root";
                        String password = "student";

                        try (Connection connection = DriverManager.getConnection(url, username, password)) {
                            // Assuming your table structure: modules (student_number, modulename, BLOBcode, Filename, Feedback)
                            String selectQuery = "SELECT BLOBcode ,Modules, Status, Filename FROM modules WHERE StudentNumber = ? AND Modules = ?";
                            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                                preparedStatement.setString(1, studentNumber);
                                preparedStatement.setString(2, moduleNameLabel.getText());
                                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                    if (resultSet.next()) {
                                        String filename = resultSet.getString("Filename");
                                        Blob blob = resultSet.getBlob("BLOBcode");
                                        String feedback = resultSet.getString("Status");

                                        // Save the BLOB data as a file in the selected directory
                                        InputStream inputStream = blob.getBinaryStream();
                                        File file = new File(selectedDirectory, filename);
                                        try (FileOutputStream outputStream = new FileOutputStream(file)) {
                                            byte[] buffer = new byte[1024];
                                            int bytesRead;
                                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                                outputStream.write(buffer, 0, bytesRead);
                                            }
                                        }

                                        // Set the feedbackLabel text
                                        feedBackLabel.setText(filename + " - " + feedback);
                                    }
                                }
                            }
                        } catch (SQLException | IOException ex) {
                            ex.printStackTrace();
                            // Handle the database or file I/O exception
                        }
                    }
                }
            }
        });
        // Create a JTextField for entering the date
        JTextField dateTextField = new JTextField(10); // You can set the preferred size as needed

        bookLab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "student";

                // Get user details
                String studentNumber = studentNumber1.getText();
                String module = moduleNameLabel.getText();
                String pracNumber = "1"; // Set the practical number as needed

                // Get the date entered in the JTextField
                String dateText = lab1Textfield.getText();

                if (!dateText.isEmpty()) {
                    // Assuming you have already established a database connection
                    try (Connection connection = DriverManager.getConnection(url, username, password)) {
                        // Check if the student has already submitted for the given practical number
                        String query = "SELECT * FROM practicals WHERE StudentNumber = ? AND pracNumber = ?";
                        try (PreparedStatement queryStatement = connection.prepareStatement(query)) {
                            queryStatement.setString(1, studentNumber);
                            queryStatement.setString(2, pracNumber);
                            ResultSet resultSet = queryStatement.executeQuery();

                            if (resultSet.next()) {
                                // The student has already submitted; show a dialogue
                                JOptionPane.showMessageDialog(null, "Practical Already Submitted");
                            } else {
                                // The student has not submitted; insert the new record
                                String insertQuery = "INSERT INTO practicals (StudentNumber, Modules, Date, pracNumber) VALUES (?, ?, ?, ?)";
                                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                                    preparedStatement.setString(1, studentNumber);
                                    preparedStatement.setString(2, module);
                                    preparedStatement.setString(3, dateText); // Store the date as a string
                                    preparedStatement.setString(4, pracNumber);
                                    preparedStatement.executeUpdate();
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Handle the database exception
                    }
                } else {
                    // Handle the case where the date field is empty
                }
            }
        });
        bookLab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "student";

                String studentNumber = studentNumber1.getText();
                String module = moduleNameLabel.getText(); // Get the module from the label
                String pracNumber = "1"; // Set the practical number as needed

                String dateText = lab1Textfield.getText();

                if (!dateText.isEmpty()) {
                    try (Connection connection = DriverManager.getConnection(url, username, password)) {
                        String query = "SELECT * FROM practicals WHERE StudentNumber = ? AND Modules = ?";
                        try (PreparedStatement queryStatement = connection.prepareStatement(query)) {
                            queryStatement.setString(1, studentNumber);
                            queryStatement.setString(2, module);
                            ResultSet resultSet = queryStatement.executeQuery();

                            if (resultSet.next()) {

                                JOptionPane.showMessageDialog(null, "Practical Booking Failed");
                            } else {
                                String insertQuery = "INSERT INTO practicals (StudentNumber, Modules, Date, pracNumber) VALUES (?, ?, ?, ?)";
                                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                                    preparedStatement.setString(1, studentNumber);
                                    preparedStatement.setString(2, module);
                                    preparedStatement.setString(3, dateText); // Store the date as a string
                                    preparedStatement.setString(4, pracNumber);
                                    int rowsAffected = preparedStatement.executeUpdate();

                                    if (rowsAffected > 0) {
                                        JOptionPane.showMessageDialog(null, "Practical Booked Successfully - " + dateText);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Practical Booking Failed");
                                    }
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                }
            }
        });


        bookLab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "student";

                String studentNumber = studentNumber1.getText();
                String module = moduleNameLabel.getText(); // Get the module from the label
                String pracNumber = "2"; // Set the practical number as needed

                String dateText = lab1Textfield.getText();

                if (!dateText.isEmpty()) {
                    try (Connection connection = DriverManager.getConnection(url, username, password)) {
                        String query = "SELECT * FROM practicals WHERE StudentNumber = ? AND Modules = ?";
                        try (PreparedStatement queryStatement = connection.prepareStatement(query)) {
                            queryStatement.setString(1, studentNumber);
                            queryStatement.setString(2, module);
                            ResultSet resultSet = queryStatement.executeQuery();

                            if (resultSet.next()) {
                                JOptionPane.showMessageDialog(null, "Practical Booking Failed");
                            } else {
                                String insertQuery = "INSERT INTO practicals (StudentNumber, Modules, Date, pracNumber) VALUES (?, ?, ?, ?)";
                                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                                    preparedStatement.setString(1, studentNumber);
                                    preparedStatement.setString(2, module);
                                    preparedStatement.setString(3, dateText); // Store the date as a string
                                    preparedStatement.setString(4, pracNumber);
                                    int rowsAffected = preparedStatement.executeUpdate();

                                    if (rowsAffected > 0) {
                                        JOptionPane.showMessageDialog(null, "Practical Booked Successfully - " + dateText);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Practical Booking Failed");
                                    }
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();

                    }
                } else {
                }
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "student";

                String student = null;  // Initialize the student variable
                String moduleName = moduleNameLabel.getText();
                String status = "Not Graded";
                String uniqueIdentifier = studentNumber1.getText();

                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    String selectStudentQuery = "SELECT StudentNumber FROM modules WHERE StudentNumber = ?";
                    try (PreparedStatement selectStudentStatement = connection.prepareStatement(selectStudentQuery)) {
                        selectStudentStatement.setString(1, uniqueIdentifier);

                        try (ResultSet studentResultSet = selectStudentStatement.executeQuery()) {
                            if (studentResultSet.next()) {
                                student = studentResultSet.getString("StudentNumber");
                            }
                        }
                    }

                    JFileChooser fileChooser = new JFileChooser();
                    int response = fileChooser.showOpenDialog(null);

                    if (response == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        String filename = file.getName();  // Get the selected file's name

                        byte[] fileContent = new byte[(int) file.length()];
                        try (FileInputStream inputStream = new FileInputStream(file)) {
                            inputStream.read(fileContent);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            // Handle the file reading exception
                        }

                        String insertQuery = "INSERT INTO modules (StudentNumber, Modules, Status, BLOBcode, Filename) VALUES (?, ?, ?, ?, ?)";

                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                            preparedStatement.setString(1, student);
                            preparedStatement.setString(2, moduleName);
                            preparedStatement.setString(3, status);
                            preparedStatement.setBytes(4, fileContent);
                            preparedStatement.setString(5, filename);
                            preparedStatement.executeUpdate();
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentPage main = new studentPage();
                main.setUserStatusLabel(getNameLabel());
                dispose();
            }
        });
    }
    private String formatDate (Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    private String generatePracNumber () {

        return pracNumber;
    }
}
