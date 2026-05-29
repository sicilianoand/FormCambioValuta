package com.example.currency;

import com.example.currency.UI.Window;
import com.example.currency.service.CurrencyService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Window(new CurrencyService());
            } catch (IOException e) {
                JOptionPane.showConfirmDialog(null, "Errore nell'apertura della finestra");
            }
        });
    }
}
