package com.example.currency;

import com.example.currency.UI.Window;
import com.example.currency.service.CurrencyService;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Window(new CurrencyService());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
