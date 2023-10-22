

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;

public class adminPracSec extends JFrame{
    private JButton homeButton;
    private JPanel adminPracPanel;
    private JLabel usernameLabel;
    private JLabel moduleNameLabel;
    private JLabel pracTitleLabel;
    private JButton labBooking1;
    private JButton labBooking2;
    private JTable showTable1;
    private JTable showTable2;
    private JButton downloadAllStudentSolutionsButton;
    private JButton uploadFeedbackButton;
    private JTextField studentNumberLabel;
    private JScrollPane scrollPane;
    public void setLabel(String text){
        moduleNameLabel.setText(text);
    }
    public void setUsernameLabel(String txt){
        usernameLabel.setText(txt);
    }
    public String getNameLabel(){
        return usernameLabel.getText();
    }
    public adminPracSec(){
        setContentPane(adminPracPanel);
        setVisible(true);
        setSize(800,440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin main = new admin();
                main.setUserStatusLabel(getNameLabel());
                dispose();
            }
        });

        uploadFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "sanele";

                String student = studentNumberLabel.getText();
                String moduleName = moduleNameLabel.getText();
                String status = "Graded";  // Change this based on your logic

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


                    try (Connection connection = DriverManager.getConnection(url, username, password)) {
                        String insertQuery = "INSERT INTO modules (StudentNumber, Modules, Status, BLOBcode, Filename) VALUES (?, ?, ?, ?, ?)";

                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                            preparedStatement.setString(1, student);
                            preparedStatement.setString(2, moduleName);
                            preparedStatement.setString(3, status);
                            preparedStatement.setBytes(4, fileContent);
                            preparedStatement.setString(5, filename);
                            preparedStatement.executeUpdate();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        labBooking1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "sanele";

                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    String query = "SELECT * FROM practicals";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                         ResultSet resultSet = preparedStatement.executeQuery()) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("practicals.txt"))) {
                            // Write column headers to the text file
                            writer.write("Student Number\tModule\tDate\tPractical Number");
                            writer.newLine();

                            while (resultSet.next()) {
                                String studentNumber = resultSet.getString("StudentNumber");
                                String module = resultSet.getString("Modules");
                                String date = resultSet.getString("Date");
                                String pracNumber = resultSet.getString("pracNumber");

                                // Write the data to the text file
                                writer.write(studentNumber + "\t" + module + "\t" + date + "\t" + pracNumber);
                                writer.newLine();
                            }

                            System.out.println("Data exported to practicals.txt on you Project folder src!");
                        }
                    }
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
                labBooking2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "sanele";

                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    String query = "SELECT * FROM practicals";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                         ResultSet resultSet = preparedStatement.executeQuery()) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("practicals.txt"))) {
                            // Write column headers to the text file
                            writer.write("Student Number\tModule\tDate\tPractical Number");
                            writer.newLine();

                            while (resultSet.next()) {
                                String studentNumber = resultSet.getString("StudentNumber");
                                String module = resultSet.getString("Modules");
                                String date = resultSet.getString("Date");
                                String pracNumber = resultSet.getString("pracNumber");

                                // Write the data to the text file
                                writer.write(studentNumber + "\t" + module + "\t" + date + "\t" + pracNumber);
                                writer.newLine();
                            }

                            System.out.println("Data exported to practicals.txt on you Project folder src!");
                        }
                    }
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }

    private void createTable(){
        Object[][] data =
                {{"220011315","Amile","Buthelezi"," "},
                {"220000000","Kim","Kay"," "},
                {"222000000","Will","Smith"," "}
        };
        showTable1.setModel(new DefaultTableModel(
                data,
                new String[]{"Student Number","Name","Surname","Status"}
        ));
        Object[][] data2 =
                {{"220011315","Amile","Buthelezi","Not Graded"},
                        {"220000000","Kim","Kay","Not Graded"},
                        {"222000000","Will","Smith","Not Graded"}
                };
        showTable2.setModel(new DefaultTableModel(
                data,
                new String[]{"Student Number","Name","Surname","Status"}
        ));
    }

});
    }
}
