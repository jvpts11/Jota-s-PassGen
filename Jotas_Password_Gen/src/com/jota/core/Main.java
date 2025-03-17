package com.jota.core;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
            PasswordGeneratorUI ui = new PasswordGeneratorUI();
            ui.setVisible(true);
        });
	}

}
