import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.formatDate;

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
                        String password = "sanele";

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
        /*bookLab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "sanele";

                // Get user details
                String studentNumber = studentNumber1.getText();
                String module = moduleNameLabel.getText();
                String pracNumber = "1"; // Set the practical number as needed

                // Get the selected date from the JDateChooser
                Date selectedDate = dateChooser.getDate();

                if (selectedDate != null) {
                    // Format the selected date to a string
                    String formattedDate = formatDate(selectedDate);

                    // Assuming you have already established a database connection
                    try (Connection connection = DriverManager.getConnection(url, username, password)) {
                        // Assuming your table structure: practicals (StudentNumber, Modules, Date, pracNumber)
                        String insertQuery = "INSERT INTO practicals (StudentNumber, Modules, Date, pracNumber) VALUES (?, ?, ?, ?)";

                        // Create a PreparedStatement to insert the data
                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                            preparedStatement.setString(1, studentNumber);
                            preparedStatement.setString(2, module);
                            preparedStatement.setString(3, formattedDate);
                            preparedStatement.setString(4, pracNumber);
                            preparedStatement.executeUpdate();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Handle the database exception
                    }
                } else {
                    // Handle the case where no date is selected
                }
            }
        });

        bookLab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "sanele";
                pracNumber="2";
                // Get user details
                String studentNumber = studentNumber1.getText();
                String module = moduleNameLabel.getText();
                String pracNumber = generatePracNumber(); // You need to implement this method

                // Get the selected date from the calendar
                Date selectedDate = calendar.getCalendar().getTime();

                // Format the selected date to a string
                String formattedDate = formatDate(selectedDate);

                // Assuming you have already established a database connection
                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    // Assuming your table structure: practicals (StudentNumber, Modules, Date, pracNumber)
                    String insertQuery = "INSERT INTO practicals (StudentNumber, Modules, Date, pracNumber) VALUES (?, ?, ?, ?)";

                    // Create a PreparedStatement to insert the data
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        preparedStatement.setString(1, studentNumber);
                        preparedStatement.setString(2, module);
                        preparedStatement.setString(3, formattedDate);
                        preparedStatement.setString(4, pracNumber);
                        preparedStatement.executeUpdate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle the database exception
                }
            }
        });*/

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "sanele";

                String student = null;  // Initialize the student variable
                String moduleName = moduleNameLabel.getText();
                String status = "Not Graded";  // Change this based on your logic

                // Define a unique identifier for fetching the student number from the database
                // For example, you might use the student's username or some other identifier
                String uniqueIdentifier = studentNumber1.getText();  // Replace with the actual identifier

                // Assuming you have already established a database connection
                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    // First, fetch the student number based on the unique identifier
                    String selectStudentQuery = "SELECT StudentNumber FROM modules WHERE StudentNumber = ?";
                    try (PreparedStatement selectStudentStatement = connection.prepareStatement(selectStudentQuery)) {
                        selectStudentStatement.setString(1, uniqueIdentifier);

                        try (ResultSet studentResultSet = selectStudentStatement.executeQuery()) {
                            if (studentResultSet.next()) {
                                // Retrieve the student number
                                student = studentResultSet.getString("StudentNumber");
                            }
                        }
                    }

                    // Now, proceed with the file upload
                    JFileChooser fileChooser = new JFileChooser();
                    int response = fileChooser.showOpenDialog(null);

                    if (response == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        String filename = file.getName();  // Get the selected file's name

                        // Read the file content into a byte array
                        byte[] fileContent = new byte[(int) file.length()];
                        try (FileInputStream inputStream = new FileInputStream(file)) {
                            inputStream.read(fileContent);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            // Handle the file reading exception
                        }

                        // Assuming your table structure: modules (StudentNumber, Modules, Status, BLOBcode, Filename)
                        String insertQuery = "INSERT INTO modules (StudentNumber, Modules, Status, BLOBcode, Filename) VALUES (?, ?, ?, ?, ?)";

                        // Create a PreparedStatement to insert the data
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
                    // Handle the database or file I/O exception
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
