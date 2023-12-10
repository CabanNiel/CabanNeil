package systems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class EmailLoginView extends JFrame {
    private static Map<String, String> userDatabase = new HashMap<>();
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public EmailLoginView() {
        super("Email Login System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(411, 100, 400, 500);
        setLocationRelativeTo(null);

        // Create CardLayout and JPanel to hold cards
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add the initial card (welcome panel)
        JPanel welcomePanel = createWelcomePanel();
        cardPanel.add(welcomePanel, "Welcome");

        // Create and add the sign-in panel
        JPanel signInPanel = createSignInPanel();
        cardPanel.add(signInPanel, "SignIn");

        // Create and add the signup panel
        JPanel signUpPanel = createSignUpPanel();
        cardPanel.add(signUpPanel, "SignUp");

        add(cardPanel);
        setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel();

        JLabel welcomeLabel = new JLabel("WELCOME");
        welcomeLabel.setBounds(108, 21, 181, 40);
        welcomeLabel.setFont(new Font("Kadwa", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.BLACK);

        JPanel greetingsPanel = new JPanel();
        greetingsPanel.setLayout(null);
        greetingsPanel.setBounds(108, 120, 192, 127);
        greetingsPanel.setBackground(Color.decode("#F4F27E"));

        JLabel firstSentence = new JLabel("Greetings my dear user,");
        firstSentence.setBounds(0, 30, 192, 30);
        firstSentence.setHorizontalAlignment(SwingConstants.CENTER);
        greetingsPanel.add(firstSentence);

        JLabel additionalText = new JLabel("Welcome to our system!");
        additionalText.setBounds(0, 50, 192, 30);
        additionalText.setHorizontalAlignment(SwingConstants.CENTER);
        greetingsPanel.add(additionalText);

        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds(111, 269, 194, 27);
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "SignIn");
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(111, 318, 194, 27);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "SignUp");
            }
        });

        welcomePanel.setLayout(null);
        welcomePanel.setBackground(Color.decode("#FFEED9"));
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(greetingsPanel);
        welcomePanel.add(signInButton);
        welcomePanel.add(signUpButton);

        return welcomePanel;
    }

    private JPanel createSignInPanel() {
        JPanel signInPanel = new JPanel();

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 30, 80, 25);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(140, 30, 150, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 70, 80, 25);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(140, 70, 150, 25);

        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds(111, 120, 194, 27);

        JButton backToWelcomeButton = new JButton("Back to Welcome");
        backToWelcomeButton.setBounds(111, 160, 194, 27);
        backToWelcomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Welcome");
            }
        });

        signInPanel.setLayout(null);
        signInPanel.setBackground(Color.decode("#FFEED9"));
        signInPanel.add(usernameLabel);
        signInPanel.add(usernameField);
        signInPanel.add(passwordLabel);
        signInPanel.add(passwordField);
        signInPanel.add(signInButton);
        signInPanel.add(backToWelcomeButton);

        return signInPanel;
    }

    private JPanel createSignUpPanel() {
        JPanel signUpPanel = new JPanel();

        JLabel signUpLabel = new JLabel("SIGN UP");
        signUpLabel.setBounds(142, 21, 181, 40);
        signUpLabel.setFont(new Font("Kadwa", Font.BOLD, 32));
        signUpLabel.setForeground(Color.BLACK);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 80, 25);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(140, 70, 150, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 80, 25);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(140, 110, 150, 25);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(111, 160, 194, 27);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement signup logic here
                // You may want to add the user to the userDatabase map
                // For simplicity, let's just print the entered username and password
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println("New user signed up: " + username + ", Password: " + password);

                // After signup, you might want to switch to the sign-in panel or perform other actions
                cardLayout.show(cardPanel, "SignIn");
            }
        });

        JButton backToWelcomeButton = new JButton("Back to Welcome");
        backToWelcomeButton.setBounds(111, 200, 194, 27);
        backToWelcomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Welcome");
            }
        });

        signUpPanel.setLayout(null);
        signUpPanel.setBackground(Color.decode("#FFEED9"));
        signUpPanel.add(signUpLabel);
        signUpPanel.add(usernameLabel);
        signUpPanel.add(usernameField);
        signUpPanel.add(passwordLabel);
        signUpPanel.add(passwordField);
        signUpPanel.add(signUpButton);
        signUpPanel.add(backToWelcomeButton);

        return signUpPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmailLoginView();
        });
    }
}
