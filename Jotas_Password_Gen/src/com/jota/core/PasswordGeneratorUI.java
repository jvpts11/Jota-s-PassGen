package com.jota.core;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class PasswordGeneratorUI extends JFrame {
    private JTextField passwordField;
    private JSpinner lengthSpinner;
    private JCheckBox uppercaseCheckBox, lowercaseCheckBox, numbersCheckBox, symbolsCheckBox, customSymbolsCheckBox;
    private JButton generateButton, copyButton;
    private JTextField customSymbolsField;
    private JLabel strengthLabel;

    public PasswordGeneratorUI() {
        setTitle("Advanced Password Generator");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Settings panel with card-like appearance
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(BorderFactory.createCompoundBorder(
        	    BorderFactory.createLineBorder(new Color(200, 200, 200)),
        	    BorderFactory.createEmptyBorder(15, 15, 15, 15)
        	));
        settingsPanel.setBackground(Color.WHITE);

        // Password Length
        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        lengthPanel.setBackground(Color.WHITE);
        lengthPanel.add(new JLabel("Password Length:"));
        lengthSpinner = new JSpinner(new SpinnerNumberModel(12, 4, 64, 1));
        lengthPanel.add(lengthSpinner);
        settingsPanel.add(lengthPanel);
        settingsPanel.add(Box.createVerticalStrut(15));

        // Character options with better grouping
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBackground(Color.WHITE);
        uppercaseCheckBox = createStyledCheckBox("Uppercase Letters (A-Z)");
        lowercaseCheckBox = createStyledCheckBox("Lowercase Letters (a-z)");
        numbersCheckBox = createStyledCheckBox("Numbers (0-9)");
        symbolsCheckBox = createStyledCheckBox("Special Symbols");
        customSymbolsCheckBox = createStyledCheckBox("Custom Symbols");
        
        optionsPanel.add(uppercaseCheckBox);
        optionsPanel.add(lowercaseCheckBox);
        optionsPanel.add(numbersCheckBox);
        optionsPanel.add(symbolsCheckBox);
        optionsPanel.add(customSymbolsCheckBox);
        settingsPanel.add(optionsPanel);
        settingsPanel.add(Box.createVerticalStrut(15));

        // Custom symbols with better label
        JPanel symbolsPanel = new JPanel(new BorderLayout(5, 5));
        symbolsPanel.setBackground(Color.WHITE);
        symbolsPanel.add(new JLabel("Custom Symbols:"), BorderLayout.NORTH);
        customSymbolsField = new JTextField("?+*º~^´`»«-.;,");
        customSymbolsField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        symbolsPanel.add(customSymbolsField);
        settingsPanel.add(symbolsPanel);

        mainPanel.add(settingsPanel, BorderLayout.CENTER);

        // Result panel with better visual hierarchy
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        resultPanel.setBackground(new Color(240, 240, 240));

        // Password display with better styling
        passwordField = new JTextField();
        passwordField.setEditable(false);
        passwordField.setFont(new Font("Monospaced", Font.BOLD, 16));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        passwordField.setBackground(new Color(250, 250, 250));
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        resultPanel.add(passwordField);
        resultPanel.add(Box.createVerticalStrut(10));

        // Strength indicator with icon
        JPanel strengthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        strengthPanel.setBackground(new Color(240, 240, 240));
        strengthLabel = new JLabel("Strength: -");
        strengthLabel.setFont(new Font("Arial", Font.BOLD, 14));
        strengthPanel.add(new JLabel(new ImageIcon("strength_icon.png"))); // Add your icon
        strengthPanel.add(strengthLabel);
        resultPanel.add(strengthPanel);
        resultPanel.add(Box.createVerticalStrut(15));

        // Buttons with better styling
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        generateButton = createStyledButton("Generate Password", new Color(70, 130, 180));
        copyButton = createStyledButton("Copy Password", new Color(60, 179, 113));
        
        generateButton.addActionListener(e -> generatePassword());
        copyButton.addActionListener(e -> copyPassword());
        
        buttonPanel.add(generateButton);
        buttonPanel.add(copyButton);
        resultPanel.add(buttonPanel);

        mainPanel.add(resultPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setBackground(Color.WHITE);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
        return checkBox;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    private void generatePassword() {
        try {
            PasswordGeneratorLogic.setCustomSymbols(customSymbolsField.getText());
            
            PasswordGeneratorLogic.PasswordResult result = PasswordGeneratorLogic.generatePassword(
                (int) lengthSpinner.getValue(),
                uppercaseCheckBox.isSelected(),
                lowercaseCheckBox.isSelected(),
                numbersCheckBox.isSelected(),
                symbolsCheckBox.isSelected(),
                customSymbolsCheckBox.isSelected()
            );
            
            passwordField.setText(result.getPassword());
            strengthLabel.setText("Strength: " + result.getStrength());
            strengthLabel.setForeground(getStrengthColor(result.getStrength()));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Color getStrengthColor(String strength) {
        switch (strength) {
            case "Weak": return new Color(220, 53, 69); // Red
            case "Medium": return new Color(255, 193, 7); // Amber
            case "Strong": return new Color(40, 167, 69); // Green
            default: return Color.BLACK;
        }
    }

    private void copyPassword() {
        if (passwordField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Generate a password first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        StringSelection selection = new StringSelection(passwordField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        
        JOptionPane.showMessageDialog(this, 
            "<html><div style='text-align: center;'>Password copied to clipboard!<br><small>You can now paste it anywhere</small></div></html>", 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}