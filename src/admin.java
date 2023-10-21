import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class admin extends JFrame{
    private JButton dashboardButton;
    private JComboBox comboBox1;
    private JPanel adminPanel;
    private JButton homeButton;
    private JLabel usernameLabel;


    public admin(){
        setVisible(true);
        setContentPane(adminPanel);
        setSize(800,440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel main = new mainPanel();
                dispose();
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) comboBox1.getSelectedItem(); // Get the selected item as a String
                adminPracSec prac1 = new adminPracSec();
                prac1.setLabel(selectedValue);
                prac1.setUsernameLabel(usernameLabel.getText());
                dispose();

            }
        });

    }

    public void setUserStatusLabel(String text) {
        usernameLabel.setText(text);
    }
}
