package com.example.currency.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.example.currency.service.CurrencyService;

public class Window extends JFrame implements ActionListener, WindowListener {
    private static final String CONFIG_FILE = "C:\\Users\\Jeja\\Desktop\\form\\src\\main\\resources\\config.cfg";
    private CurrencyService service;

    private JComboBox<String> chooseToConvert;
    private JComboBox<String> chooseConverted;

    private JButton convertBtn;

    private JLabel label;

    private JTextField field;

    public Window(CurrencyService service) throws IOException{
        setWindow();
        this.service = service;

        setTitle("Currency");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        chooseToConvert = new JComboBox<>();
        chooseConverted = new JComboBox<>();
        
        try {
            service.request("EUR").getConversionRates().forEach((key, value) -> {
                chooseToConvert.addItem(key);
                chooseConverted.addItem(key);
            });
            chooseToConvert.setSelectedItem("EUR");
            chooseConverted.setSelectedItem("USD");
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        label = new JLabel();
        field = new JTextField();

        convertBtn = new JButton("CONVERTI");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(field); panel.add(chooseToConvert);
        panel.add(label); panel.add(chooseConverted);

        add(panel);
        add(convertBtn, BorderLayout.SOUTH);

        convertBtn.addActionListener(this);
        addWindowListener(this);
        setVisible(true);
    }

    public void setWindow() throws IOException {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            setSize(800, 600);
            setLocationRelativeTo(null);
        }
        else {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            int width = Integer.parseInt(line.split(" ")[1]);

            line = br.readLine();
            int height = Integer.parseInt(line.split(" ")[1]);

            line = br.readLine();
            int x = Integer.parseInt(line.split(" ")[1]);

            line = br.readLine();
            int y = Integer.parseInt(line.split(" ")[1]);

            setBounds(x, y, width, height);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            field.setBorder(null);

            int intLabel = Integer.parseInt(field.getText());
            var rate = service.request((String)chooseToConvert.getSelectedItem());
            rate.getConversionRates().forEach((key, value) -> {
                String item = (String) chooseConverted.getSelectedItem();
                if (item.equals(key)) {
                    label.setText(String.valueOf(value * intLabel));
                }
            });
        } catch (IOException | InterruptedException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e1) {
            field.setBorder(new TitledBorder(new LineBorder(Color.RED),
                    "Inserisci solo numeri"));
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONFIG_FILE))){
            bw.write("width " + getWidth() + "\n");
            bw.write("height " + getHeight() + "\n");
            bw.write("x " + getX() + "\n");
            bw.write("y " + getY());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }
    @Override
    public void windowIconified(WindowEvent e) {

    }
    @Override
    public void windowDeiconified(WindowEvent e) {

    }
    @Override
    public void windowActivated(WindowEvent e) {

    }
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
