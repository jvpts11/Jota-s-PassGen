package com.jota.core;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class PasswordGeneratorUI extends JFrame{
	
	private JTextField passwordField;
    private JSpinner lengthSpinner;
    private JCheckBox uppercaseCheckBox;
    private JCheckBox lowercaseCheckBox;
    private JCheckBox numbersCheckBox;
    private JCheckBox symbolsCheckBox;
    private JButton generateButton;
    private JButton copyButton;
	
	public PasswordGeneratorUI(){
		setTitle("Jota's PassGen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        passwordField = new JTextField();
        passwordField.setEditable(false);
        passwordField.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(new JLabel("Generated Password"));
        panel.add(passwordField);

        panel.add(new JLabel("Password Size:"));
        lengthSpinner = new JSpinner(new SpinnerNumberModel(12, 4, 128, 1));
        panel.add(lengthSpinner);
        
        uppercaseCheckBox = new JCheckBox("Include capital letters (A-Z)");
        lowercaseCheckBox = new JCheckBox("Include small letters (a-z)");
        numbersCheckBox = new JCheckBox("Include numbers (0-9)");
        symbolsCheckBox = new JCheckBox("Include symbols (!@#$%^&*)");
        
        panel.add(uppercaseCheckBox);
        panel.add(lowercaseCheckBox);
        panel.add(numbersCheckBox);
        panel.add(symbolsCheckBox);
        
        generateButton = new JButton("Generate Password.");
        generateButton.addActionListener(e -> generatePassword());
        panel.add(generateButton);

        copyButton = new JButton("Copy Password.");
        copyButton.addActionListener(e -> copyPasswordToClipboard());
        panel.add(copyButton);

        add(panel);
	}
	
	private void generatePassword() {
		try {
			int length = (int) lengthSpinner.getValue();
            boolean useUppercase = uppercaseCheckBox.isSelected();
            boolean useLowercase = lowercaseCheckBox.isSelected();
            boolean useNumbers = numbersCheckBox.isSelected();
            boolean useSymbols = symbolsCheckBox.isSelected();
            String password = PasswordGeneratorLogic.generatePassword(length, useUppercase, useLowercase, useNumbers, useSymbols);
            passwordField.setText(password);
		}catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void copyPasswordToClipboard() {
		String password = passwordField.getText();
		if(password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Generate a Password", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
		}
		
		StringSelection stringSelection = new StringSelection(password);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		JOptionPane.showMessageDialog(this, "String Copied to clipboard");
	}
}
