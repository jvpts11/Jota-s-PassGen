package com.jota.core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGeneratorLogic {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_SYMBOLS = "!@#$%^&*/()[]{}";
    private static String customSymbols = "?+*º~^´`»«-.;,";

    public static void setCustomSymbols(String symbols) {
        if (symbols == null || symbols.isEmpty()) {
            throw new IllegalArgumentException("Custom symbols cannot be empty");
        }
        customSymbols = symbols;
    }

    public static PasswordResult generatePassword(int length, boolean useUppercase, boolean useLowercase,
                                               boolean useNumbers, boolean useSymbols, boolean useCustomSymbols) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be greater than 0");
        }

        if (!useUppercase && !useLowercase && !useNumbers && !useSymbols) {
            throw new IllegalArgumentException("Select at least one character type");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        List<String> selectedCategories = new ArrayList<>();

        if (useUppercase) selectedCategories.add(UPPERCASE_LETTERS);
        if (useLowercase) selectedCategories.add(LOWERCASE_LETTERS);
        if (useNumbers) selectedCategories.add(NUMBERS);
        if (useSymbols) selectedCategories.add(SPECIAL_SYMBOLS);
        if(useCustomSymbols) selectedCategories.add(customSymbols);

        // Ensure at least one from each selected category
        for (String category : selectedCategories) {
            password.append(category.charAt(random.nextInt(category.length())));
        }

        // Fill remaining characters
        String allChars = String.join("", selectedCategories);
        while (password.length() < length) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle
        List<Character> chars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) chars.add(c);
        Collections.shuffle(chars, random);
        
        String finalPassword = chars.stream()
                                 .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                 .toString();

        return new PasswordResult(finalPassword, calculateStrength(finalPassword, useUppercase,
                               useLowercase, useNumbers, useSymbols));
    }

    private static String calculateStrength(String password, boolean useUppercase,
                                         boolean useLowercase, boolean useNumbers, boolean useSymbols) {
        int strengthScore = 0;
        
        // Length-based scoring
        if (password.length() >= 12) strengthScore += 2;
        else if (password.length() >= 8) strengthScore += 1;
        
        // Diversity-based scoring
        int categoriesUsed = (useUppercase ? 1 : 0) + (useLowercase ? 1 : 0) + 
                           (useNumbers ? 1 : 0) + (useSymbols ? 1 : 0);
        strengthScore += categoriesUsed;
        
        if (strengthScore >= 5) return "Strong";
        if (strengthScore >= 3) return "Medium";
        return "Weak";
    }

    public static class PasswordResult {
        private final String password;
        private final String strength;

        public PasswordResult(String password, String strength) {
            this.password = password;
            this.strength = strength;
        }

        public String getPassword() {
            return password;
        }

        public String getStrength() {
            return strength;
        }
    }
}