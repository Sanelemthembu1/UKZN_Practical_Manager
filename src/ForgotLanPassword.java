import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Amile Buthelezi

public class ForgotLanPassword extends JFrame{
    private JTextField textField1;
    private JButton submitButton;
    private JPanel forgotPanel;
    private JButton backButton;

    ForgotLanPassword(){
        setVisible(true);
        setSize(800,440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(forgotPanel);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new mainPanel();
                dispose();
            }
        });
    }

}
