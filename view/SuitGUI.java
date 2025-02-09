package view;

import model.SuperheroSuit;
import model.SuitDatabase;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SuitGUI {
    private JFrame frame;
    private JTextField suitIdField;
    private JTextArea resultArea;
    private List<SuperheroSuit> suits;
    private Map<String, Integer> repairCount;

    public SuitGUI() {
        suits = SuitDatabase.loadSuits();
        repairCount = new HashMap<>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("🔮 Superhero Suit Durability Checker 🔮");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(50, 25, 72));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(75, 35, 100));
        inputPanel.setLayout(new GridBagLayout());

        JLabel suitIdLabel = new JLabel("Enter Suit ID:");
        suitIdLabel.setForeground(Color.WHITE);
        suitIdLabel.setFont(new Font("Montserrat", Font.BOLD, 22));

        suitIdField = new JTextField(12);
        suitIdField.setFont(new Font("Montserrat", Font.PLAIN, 20));
        suitIdField.setBackground(new Color(90, 45, 120));
        suitIdField.setForeground(Color.WHITE);
        suitIdField.setBorder(BorderFactory.createLineBorder(new Color(140, 70, 180), 2));

        JButton checkButton = new JButton(" Check Suit ");
        checkButton.setBackground(new Color(150, 75, 200));
        checkButton.setForeground(Color.WHITE);
        checkButton.setFocusPainted(false);
        checkButton.setFont(new Font("Montserrat", Font.BOLD, 20));
        checkButton.setBorder(BorderFactory.createLineBorder(new Color(180, 90, 220), 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(suitIdLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(suitIdField, gbc);
        gbc.gridx = 2;
        inputPanel.add(checkButton, gbc);

        resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(240, 240, 255));
        resultArea.setForeground(Color.BLACK);
        resultArea.setFont(new Font("Montserrat", Font.PLAIN, 18));
        
        JLabel titleLabel = new JLabel("Suit Information");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(50, 25, 72));
        resultPanel.add(titleLabel, BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSuit();
            }
        });

        frame.setVisible(true);
    }

    private void checkSuit() {
        String suitId = suitIdField.getText().trim();
        if (!suitId.matches("[1-9][0-9]{5}")) {
            resultArea.setText("⚠️ Invalid Suit ID! Must be a 6-digit number where the first digit is not 0.");
            return;
        }

        Optional<SuperheroSuit> suitOpt = suits.stream()
                .filter(s -> s.getSuitId().equals(suitId))
                .findFirst();

        if (!suitOpt.isPresent()) {
            resultArea.setText("❌ Suit not found in database!");
            return;
        }

        SuperheroSuit suit = suitOpt.get();
        String result = "Suit ID: " + suit.getSuitId() +
                        "\nType: " + suit.getSuitType() +
                        "\nDurability: " + suit.getDurability();

        if (!suit.isValid()) {
            result += "\n\n⚠️ This suit does not meet durability conditions!";
            int option = JOptionPane.showConfirmDialog(frame, 
                    "Would you like to repair this suit?", 
                    "Repair Suit", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                suit.repair();
                SuitDatabase.saveSuits(suits);
                result += "\nSuit has been successfully repaired!";
                
                repairCount.put(suit.getSuitType(), repairCount.getOrDefault(suit.getSuitType(), 0) + 1);
                result += "\n\nTotal Repairs: " + repairCount;
            }
        }

        resultArea.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SuitGUI::new);
    }
}