package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaDeSoma {

    private JFrame janela;
    private JTextField campoPrimeiroNumero;
    private JTextField campoSegundoNumero;
    private JTextField campoResultado;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaDeSoma tela = new TelaDeSoma();
                tela.janela.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaDeSoma() {
        configurarJanela();
    }

    private void configurarJanela() {
        janela = new JFrame("Somador Simples");
        janela.setBounds(100, 100, 400, 300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.getContentPane().setLayout(new GridLayout(4, 2, 8, 8));
        janela.getContentPane().setBackground(new Color(230, 230, 250)); // Cor de fundo ligeiramente alterada
        
        JLabel labelPrimeiroNumero = new JLabel("Digite o primeiro número:");
        janela.getContentPane().add(labelPrimeiroNumero);

        campoPrimeiroNumero = new JTextField();
        janela.getContentPane().add(campoPrimeiroNumero);
        campoPrimeiroNumero.setColumns(10);

        JLabel labelSegundoNumero = new JLabel("Digite o segundo número:");
        janela.getContentPane().add(labelSegundoNumero);

        campoSegundoNumero = new JTextField();
        janela.getContentPane().add(campoSegundoNumero);
        campoSegundoNumero.setColumns(10);

        JLabel labelResultado = new JLabel("Resultado:");
        janela.getContentPane().add(labelResultado);

        campoResultado = new JTextField();
        campoResultado.setEditable(false);
        campoResultado.setBackground(new Color(245, 245, 245)); // Cor de fundo alterada para campo de resultado
        janela.getContentPane().add(campoResultado);
        campoResultado.setColumns(10);

        JButton botaoSomar = new JButton("Calcular");
        botaoSomar.setBackground(new Color(60, 180, 75)); // Cor verde do botão somar alterada
        botaoSomar.setForeground(Color.WHITE);
        botaoSomar.setFocusPainted(false);
        botaoSomar.setPreferredSize(new Dimension(90, 35)); // Dimensão do botão ajustada
        botaoSomar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarSoma();
            }
        });
        janela.getContentPane().add(botaoSomar);

        JButton botaoLimpar = new JButton("Resetar");
        botaoLimpar.setBackground(new Color(200, 50, 50)); // Cor do botão limpar ajustada
        botaoLimpar.setForeground(Color.WHITE);
        botaoLimpar.setFocusPainted(false);
        botaoLimpar.setPreferredSize(new Dimension(90, 35));
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        janela.getContentPane().add(botaoLimpar);
    }

    private void realizarSoma() {
        try {
            double numero1 = Double.parseDouble(campoPrimeiroNumero.getText());
            double numero2 = Double.parseDouble(campoSegundoNumero.getText());
            double resultado = numero1 + numero2;
            campoResultado.setText(String.valueOf(resultado));
        } catch (NumberFormatException ex) {
            campoResultado.setText("Erro ao calcular");
        }
    }

    private void limparCampos() {
        campoPrimeiroNumero.setText("");
        campoSegundoNumero.setText("");
        campoResultado.setText("");
    }
}
