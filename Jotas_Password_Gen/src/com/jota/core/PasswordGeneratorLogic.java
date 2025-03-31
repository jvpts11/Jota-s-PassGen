package com.jota.core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGeneratorLogic {
	
	private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*";
    
    private String[] customSymbols;
    
    public static String generatePassword(int length, boolean useUppercase, boolean useLowercase, boolean useNumbers, boolean useSymbols) {
        if (length <= 0) {
            throw new IllegalArgumentException("Size must be bigger than 0.");
        }

        if (!useUppercase && !useLowercase && !useNumbers && !useSymbols) {
            throw new IllegalArgumentException("Select at least one type of character.");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        List<String> selectedCategories = new ArrayList<>();

        if (useUppercase) selectedCategories.add(UPPERCASE_LETTERS);
        if (useLowercase) selectedCategories.add(LOWERCASE_LETTERS);
        if (useNumbers) selectedCategories.add(NUMBERS);
        if (useSymbols) selectedCategories.add(SYMBOLS);

        for (String category : selectedCategories) {
            int randomIndex = random.nextInt(category.length());
            password.append(category.charAt(randomIndex));
        }

        String allCharacters = String.join("", selectedCategories);
        while (password.length() < length) {
            int randomIndex = random.nextInt(allCharacters.length());
            password.append(allCharacters.charAt(randomIndex));
        }

        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        Collections.shuffle(passwordChars, random);

        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : passwordChars) {
            shuffledPassword.append(c);
        }

        return shuffledPassword.toString();
    }

}
