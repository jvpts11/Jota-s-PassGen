package com.jota.core;

import java.security.SecureRandom;

public class PasswordGeneratorLogic {
	
	private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*";
    
    private String[] customSymbols;
    
    public static String generatePassword(int length, boolean useUppercase, boolean useLowercase, boolean useNumbers, boolean useSymbols) {
    	if(length <= 0) {
    		throw new IllegalArgumentException("Password size must be bigger than 0.");
    	}
    	
    	StringBuilder allCharacters = new StringBuilder();
    	if (useUppercase) allCharacters.append(UPPERCASE_LETTERS);
        if (useLowercase) allCharacters.append(LOWERCASE_LETTERS);
        if (useNumbers) allCharacters.append(NUMBERS);
        if (useSymbols) allCharacters.append(SYMBOLS);
        
        if(allCharacters.length() <= 0) {
        	throw new IllegalArgumentException("Select at least one type of character.");
        }
        
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allCharacters.length());
            password.append(allCharacters.charAt(randomIndex));
        }

        return password.toString();
    }

}
