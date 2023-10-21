import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class studentPage extends JFrame{
    private JButton dashboardButton;
    private JComboBox comboBox1;
    private JPanel mainStudentPanel;
    private JLabel usernameLabel;
    private JLabel studentNumber;
    private JButton homeButton;

    public void setUserStudentNumber(String txt){
        studentNumber.setText(txt);
    }
    public String getUserStudentNumber(){
        return studentNumber.getText();
    }
    public studentPage(){
        setContentPane(mainStudentPanel);
        setVisible(true);
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
                        PracticalForm prac1 = new PracticalForm();
                        prac1.setLabel(selectedValue);
                        prac1.setStudentNumber1(studentNumber.getText());
                        prac1.setUsernameLabel(usernameLabel.getText());
                        dispose();

            }
        });


    }
    public void setUserStatusLabel(String text) {
        usernameLabel.setText(text);
    }

}
