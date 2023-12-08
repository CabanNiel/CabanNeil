package system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

class Email {
    private String sender;
    private List<String> receivers;
    private String subject;
    private String body;
    private Date date;
    private List<String> tags;

    public Email(String sender, List<String> receivers, String subject, String body, Date date) {
        this.sender = sender;
        this.receivers = receivers;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.tags = new ArrayList<>();
    }

    public String getSender() {
        return sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }
}

class EmailSystem {
    private List<User> users;
    private List<Email> inbox;
    private List<Email> sent;
    private List<Email> archives;
    private List<Email> trash;

    public EmailSystem() {
        this.users = new ArrayList<>();
        this.inbox = new ArrayList<>();
        this.sent = new ArrayList<>();
        this.archives = new ArrayList<>();
        this.trash = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void sendMessage(Email email) {
        if (users.stream().anyMatch(user -> user.getUsername().equals(email.getSender()) && user.getRole().equals("admin"))) {
            System.out.println("Admin cannot send messages.");
            return;
        }

        sent.add(email);
        inbox.addAll(email.getReceivers().stream()
                .map(receiver -> new Email(email.getSender(), List.of(receiver), email.getSubject(), email.getBody(), new Date(email.getDate().getTime())))
                .collect(Collectors.toList()));
    }

    public void deleteMessage(Email email) {
        inbox.remove(email);
        sent.remove(email);
        archives.remove(email);
        trash.add(email);
    }

    public void archiveMessage(Email email) {
        inbox.remove(email);
        sent.remove(email);
        archives.add(email);
    }

    public List<Email> getInbox() {
        return inbox;
    }

    public List<Email> getSent() {
        return sent;
    }

    public List<Email> getTrash() {
        return trash;
    }
}

public class EmailSystemGUI extends JFrame {

    private EmailSystem emailSystem;

    private JTextArea inboxTextArea;
    private JTextArea sentTextArea;
    private JTextArea trashTextArea;

    public EmailSystemGUI() {
        emailSystem = new EmailSystem();

        initializeUI();

        User admin = new User("admin", "admin");
        User student = new User("student1", "student");
        User businessAccount = new User("business1", "business");

        emailSystem.addUser(admin);
        emailSystem.addUser(student);
        emailSystem.addUser(businessAccount);

        Email email1 = new Email("student1", Arrays.asList("admin"), "Hello", "How are you?", new Date());
        Email email2 = new Email("admin", Arrays.asList("student1"), "Hi", "I'm good, thank you!", new Date());

        emailSystem.sendMessage(email1);
        emailSystem.sendMessage(email2);

        updateDisplay();
    }

    private void initializeUI() {
        setTitle("Email System GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(1, 3));

        inboxTextArea = new JTextArea();
        sentTextArea = new JTextArea();
        trashTextArea = new JTextArea();

        mainPanel.add(createPanel("Inbox", inboxTextArea));
        mainPanel.add(createPanel("Sent Messages", sentTextArea));
        mainPanel.add(createPanel("Trash", trashTextArea));

        add(mainPanel);

        setVisible(true);
    }

    private JScrollPane createPanel(String title, JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(title));
        return scrollPane;
    }

    private void updateDisplay() {
        updateTextArea(inboxTextArea, emailSystem.getInbox());
        updateTextArea(sentTextArea, emailSystem.getSent());
        updateTextArea(trashTextArea, emailSystem.getTrash());
    }

    private void updateTextArea(JTextArea textArea, List<Email> emails) {
        textArea.setText("");
        for (Email email : emails) {
            textArea.append(email.getSubject() + " - " + email.getSender() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmailSystemGUI());
    }
}
