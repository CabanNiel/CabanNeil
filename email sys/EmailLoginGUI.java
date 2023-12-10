import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailSystem extends JFrame {
    private static Map<String, User> userDatabase = new HashMap<>();

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private User currentUser;
    private JTextArea emailTextArea;

    // Constructor
    public EmailSystem() {
        super("Email System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(411, 100, 600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add panels to the cardPanel
        JPanel welcomePanel = createWelcomePanel();
        cardPanel.add(welcomePanel, "Welcome");

        JPanel signInPanel = createSignInPanel();
        cardPanel.add(signInPanel, "SignIn");

        JPanel signUpPanel = createSignUpPanel();
        cardPanel.add(signUpPanel, "SignUp");

        JPanel emailPanel = createEmailPanel();
        cardPanel.add(emailPanel, "Email");

        add(cardPanel);
        setVisible(true);
    }

    // Create panel for the welcome screen
    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel();

        JLabel welcomeLabel = new JLabel("WELCOME");
        welcomeLabel.setFont(new Font("Kadwa", Font.BOLD, 32));

        JPanel greetingsPanel = new JPanel();
        greetingsPanel.setLayout(new GridLayout(2, 1));
        greetingsPanel.setBackground(Color.decode("#F4F27E"));

        JLabel firstSentence = new JLabel("Greetings, dear user,");
        firstSentence.setHorizontalAlignment(SwingConstants.CENTER);
        greetingsPanel.add(firstSentence);

        JLabel additionalText = new JLabel("Welcome to our system!");
        additionalText.setHorizontalAlignment(SwingConstants.CENTER);
        greetingsPanel.add(additionalText);

        // Use a JPanel with FlowLayout for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#FFEED9"));

        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> cardLayout.show(cardPanel, "SignIn"));

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> cardLayout.show(cardPanel, "SignUp"));

        buttonPanel.add(signInButton);
        buttonPanel.add(signUpButton);

        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBackground(Color.decode("#FFEED9"));
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
        welcomePanel.add(greetingsPanel, BorderLayout.CENTER);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);

        return welcomePanel;
    }

    // Create panel for user sign-in
    private JPanel createSignInPanel() {
        JPanel signInPanel = new JPanel();

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticateUser(username, password)) {
                cardLayout.show(cardPanel, "Email");
                currentUser = userDatabase.get(username);
                refreshEmails(emailTextArea);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backToWelcomeButton = new JButton("Back to Welcome");
        backToWelcomeButton.addActionListener(e -> cardLayout.show(cardPanel, "Welcome"));

        signInPanel.setLayout(new GridLayout(4, 2));
        signInPanel.setBackground(Color.decode("#FFEED9"));
        signInPanel.add(usernameLabel);
        signInPanel.add(usernameField);
        signInPanel.add(passwordLabel);
        signInPanel.add(passwordField);
        signInPanel.add(signInButton);
        signInPanel.add(backToWelcomeButton);

        return signInPanel;
    }

    // Create panel for user sign-up
    private JPanel createSignUpPanel() {
        JPanel signUpPanel = new JPanel();

        JLabel newUsernameLabel = new JLabel("New Username:");
        JTextField newUsernameField = new JTextField();

        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField();

        JLabel birthDateLabel = new JLabel("Birth Date (MM/dd/yyyy):");
        JFormattedTextField birthDateField = new JFormattedTextField(createDateFormatter());
        birthDateField.setColumns(10);

        JLabel roleLabel = new JLabel("Role:");
        JTextField roleField = new JTextField();

        JLabel familyNameLabel = new JLabel("Family Name:");
        JTextField familyNameField = new JTextField();

        JLabel givenNameLabel = new JLabel("Given Name:");
        JTextField givenNameField = new JTextField();

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            String newUsername = newUsernameField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            String birthDateText = birthDateField.getText();
            String role = roleField.getText();

            if (newUsername.isEmpty() || newPassword.isEmpty() || birthDateText.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter valid information for all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (userDatabase.containsKey(newUsername)) {
                JOptionPane.showMessageDialog(null, "Username already exists, please choose a different one", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User newUser = new User(newUsername, newPassword, parseDate(birthDateText), role, familyNameField.getText(), givenNameField.getText());
                userDatabase.put(newUsername, newUser);
                JOptionPane.showMessageDialog(null, "Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                cardLayout.show(cardPanel, "Welcome");
            }
        });

        JButton backToWelcomeButton = new JButton("Back to Welcome");
        backToWelcomeButton.addActionListener(e -> cardLayout.show(cardPanel, "Welcome"));

        signUpPanel.setLayout(new GridLayout(10, 2));
        signUpPanel.setBackground(Color.decode("#FFEED9"));
        signUpPanel.add(newUsernameLabel);
        signUpPanel.add(newUsernameField);
        signUpPanel.add(newPasswordLabel);
        signUpPanel.add(newPasswordField);
        signUpPanel.add(birthDateLabel);
        signUpPanel.add(birthDateField);
        signUpPanel.add(roleLabel);
        signUpPanel.add(roleField);
        signUpPanel.add(familyNameLabel);
        signUpPanel.add(familyNameField);
        signUpPanel.add(givenNameLabel);
        signUpPanel.add(givenNameField);
        signUpPanel.add(signUpButton);
        signUpPanel.add(backToWelcomeButton);

        return signUpPanel;
    }

    // Create a SimpleDateFormat for date parsing
    private SimpleDateFormat createDateFormatter() {
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    // Parse date string into a Date object
    private Date parseDate(String dateString) {
        try {
            return createDateFormatter().parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    // Create panel for email functionality
    private JPanel createEmailPanel() {
        JPanel emailPanel = new JPanel();

        emailTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(emailTextArea);

        JButton composeButton = new JButton("Compose");
        composeButton.addActionListener(e -> {
            String message = JOptionPane.showInputDialog(null, "Enter your message:", "Compose Email", JOptionPane.PLAIN_MESSAGE);
            if (message != null && !message.isEmpty()) {
                currentUser.getMailbox().add(new Email(currentUser.getUsername(), "Subject", message));
                refreshEmails(emailTextArea);
            }
        });

        JButton inboxButton = new JButton("Inbox");
        inboxButton.addActionListener(e -> refreshEmails(emailTextArea));

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(cardPanel, "Welcome");
        });

        emailPanel.setLayout(new BorderLayout());
        emailPanel.setBackground(Color.decode("#FFEED9"));
        emailPanel.add(scrollPane, BorderLayout.CENTER);
        emailPanel.add(composeButton, BorderLayout.SOUTH);
        emailPanel.add(inboxButton, BorderLayout.SOUTH);
        emailPanel.add(signOutButton, BorderLayout.SOUTH);

        return emailPanel;
    }

    // Authenticate user based on username and password
    private boolean authenticateUser(String username, String password) {
        return userDatabase.containsKey(username) && userDatabase.get(username).getPassword().equals(password);
    }

    // Refresh the email text area with the contents of the user's inbox
    private void refreshEmails(JTextArea emailTextArea) {
        if (currentUser != null) {
            StringBuilder emails = new StringBuilder("Inbox:\n");
            List<Email> inbox = currentUser.getMailbox();
            for (Email email : inbox) {
                emails.append(email).append("\n");
            }
            emailTextArea.setText(emails.toString());
        }
    }

    // User class representing a user with various attributes
    private static class User {
        private String username;
        private String password;
        private Date birthDate;
        private String role;
        private String familyName;
        private String givenName;
        private List<Email> mailbox;

        public User(String username, String password, Date birthDate, String role, String familyName, String givenName) {
            this.username = username;
            this.password = password;
            this.birthDate = birthDate;
            this.role = role;
            this.familyName = familyName;
            this.givenName = givenName;
            this.mailbox = new ArrayList<>();
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public Date getBirthDate() {
            return birthDate;
        }

        public String getRole() {
            return role;
        }

        public String getFamilyName() {
            return familyName;
        }

        public String getGivenName() {
            return givenName;
        }

        public List<Email> getMailbox() {
            return mailbox;
        }
    }

    // Email class representing an email with sender, subject, and content
    private static class Email {
        private String sender;
        private String subject;
        private String content;

        public Email(String sender, String subject, String content) {
            this.sender = sender;
            this.subject = subject;
            this.content = content;
        }

        @Override
        public String toString() {
            return "From: " + sender + "\nSubject: " + subject + "\n" + content;
        }
    }

    // Main method to initialize the user database and launch the GUI
    public static void main(String[] args) {
        userDatabase.put("user1", new User("user1", "password1", new Date(), "student", "Doe", "John"));
        userDatabase.put("user2", new User("user2", "password2", new Date(), "business", "Smith", "Alice"));

        SwingUtilities.invokeLater(() -> {
            new EmailSystem();
        });
    }
}
