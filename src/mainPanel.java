import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
//Luke Morgan
public class mainPanel extends JFrame{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton forgotYourLANPasswordButton;
    private JButton LOGINButton;
    private JPanel loginPanel;


    mainPanel(){

        setVisible(true);
        setSize(800,440);
        setContentPane(loginPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/ukznce";
                String username = "root";
                String password = "student";
                String query = "SELECT username, lanPassword, level, name, surname FROM users";
                String inputUsername = textField1.getText();
                char[] passwordChars = passwordField1.getPassword();
                String inputPassword = new String(passwordChars);
                String usernameFromDB = null;
                String surnameFromDB = null;
                String nameFromDB = null;

                try (Connection connection = DriverManager.getConnection(url, username, password);
                     Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {

                    boolean userExists = false;
                    String userLevel = null;

                    while (resultSet.next()) {
                        String usernameFromDBTemp = resultSet.getString("username");
                        String lanPassFromDB = resultSet.getString("lanPassword");
                        nameFromDB = resultSet.getString("name");
                        surnameFromDB = resultSet.getString("surname");

                        if (inputUsername.equals(usernameFromDBTemp) && inputPassword.equals(lanPassFromDB)) {
                            userExists = true;
                            usernameFromDB = usernameFromDBTemp;
                            userLevel = resultSet.getString("level");
                            break;  // Exit the loop when a matching user is found
                        }
                    }

                    if (userExists) {
                        if ("admin".equals(userLevel)) {
                            // Open the admin window
                            admin adminFrame = new admin();
                            adminFrame.setGreetingLabel(nameFromDB);
                            adminFrame.setUserStatusLabel(nameFromDB + " " + surnameFromDB);
                            dispose();
                        } else if ("student".equals(userLevel)) {
                            // Open the student window
                            studentPage studentFrame = new studentPage();
                            studentFrame.greetUser("Hello, " + nameFromDB);
                            studentFrame.setUserStatusLabel(nameFromDB + " " + surnameFromDB);
                            studentFrame.setUserStudentNumber(usernameFromDB);
                            dispose();
                        } else {
                            // Handle other user types if necessary
                        }
                    } else {
                        // Show a pop-up window to inform the user of incorrect credentials
                        JOptionPane.showMessageDialog(null, "Incorrect Username or LAN password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        forgotYourLANPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            ForgotLanPassword flp = new ForgotLanPassword();
            }
        });

    }
}
