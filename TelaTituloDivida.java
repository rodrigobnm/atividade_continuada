package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaTituloDivida {

    private JFrame frame;
    private DividaMediator mediator = new DividaMediator();  // Classe mediator para lidar com títulos de dívida
    private JTextField txtCodigo;
    private JTextField txtDescricao;
    private JTextField txtValor;
    private JButton btnNovo;
    private JButton btnBuscar;
    private JButton btnIncluirAlterar;
    private JButton btnCancelar;
    private JButton btnLimpar;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaTituloDivida window = new TelaTituloDivida();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaTituloDivida() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Cadastro de Título de Dívida");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo para código do título de dívida
        JLabel lblCodigo = new JLabel("Código:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.getContentPane().add(lblCodigo, gbc);

        txtCodigo = new JTextField(15);
        gbc.gridx = 1;
        frame.getContentPane().add(txtCodigo, gbc);

        // Botão Novo para cadastrar um novo título de dívida
        btnNovo = new JButton("Novo");
        btnNovo.setBackground(new Color(50, 150, 50));
        btnNovo.setForeground(Color.WHITE);
        gbc.gridx = 2;
        frame.getContentPane().add(btnNovo, gbc);

        // Botão para buscar um título de dívida existente
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(50, 100, 200));
        btnBuscar.setForeground(Color.WHITE);
        gbc.gridx = 3;
        frame.getContentPane().add(btnBuscar, gbc);

        // Campo para descrição do título de dívida
        JLabel lblDescricao = new JLabel("Descrição:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.getContentPane().add(lblDescricao, gbc);

        txtDescricao = new JTextField(15);
        txtDescricao.setEnabled(false);
        gbc.gridx = 1;
        frame.getContentPane().add(txtDescricao, gbc);

        // Campo para valor do título de dívida
        JLabel lblValor = new JLabel("Valor:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.getContentPane().add(lblValor, gbc);

        txtValor = new JTextField(15);
        txtValor.setEnabled(false);
        gbc.gridx = 1;
        frame.getContentPane().add(txtValor, gbc);

        // Botão para incluir ou alterar título de dívida
        btnIncluirAlterar = new JButton("Incluir");
        btnIncluirAlterar.setEnabled(false);
        btnIncluirAlterar.setBackground(new Color(200, 150, 50));
        btnIncluirAlterar.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.getContentPane().add(btnIncluirAlterar, gbc);

        // Botão para cancelar a operação atual
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.setBackground(new Color(200, 50, 50));
        btnCancelar.setForeground(Color.WHITE);
        gbc.gridx = 1;
        frame.getContentPane().add(btnCancelar, gbc);

        // Botão para limpar os campos da tela
        btnLimpar = new JButton("Limpar");
        btnLimpar.setBackground(new Color(100, 100, 100));
        btnLimpar.setForeground(Color.WHITE);
        gbc.gridx = 2;
        frame.getContentPane().add(btnLimpar, gbc);

        // Implementação dos eventos dos botões
        btnNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TituloDivida titulo = mediator.buscar(txtCodigo.getText());
                if (titulo != null) {
                    JOptionPane.showMessageDialog(frame, "Título de dívida já existente!");
                } else {
                    habilitarEdicao(true);
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TituloDivida titulo = mediator.buscar(txtCodigo.getText());
                if (titulo == null) {
                    JOptionPane.showMessageDialog(frame, "Título de dívida não encontrado!");
                } else {
                    preencherCampos(titulo);
                    habilitarEdicao(true);
                    btnIncluirAlterar.setText("Alterar");
                }
            }
        });

        btnIncluirAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TituloDivida titulo = new TituloDivida(txtCodigo.getText(), txtDescricao.getText(),
                        Double.parseDouble(txtValor.getText()));
                String msg = null;
                if (btnIncluirAlterar.getText().equals("Incluir")) {
                    msg = mediator.incluir(titulo);
                } else {
                    msg = mediator.alterar(titulo);
                }

                if (msg != null) {
                    JOptionPane.showMessageDialog(frame, msg);
                } else {
                    limparCampos();
                    habilitarEdicao(false);
                    btnIncluirAlterar.setText("Incluir");
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                habilitarEdicao(false);
                btnIncluirAlterar.setText("Incluir");
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
    }

    // Método auxiliar para habilitar/desabilitar edição
    private void habilitarEdicao(boolean habilitar) {
        txtDescricao.setEnabled(habilitar);
        txtValor.setEnabled(habilitar);
        txtCodigo.setEnabled(!habilitar);
        btnIncluirAlterar.setEnabled(habilitar);
        btnCancelar.setEnabled(habilitar);
        btnNovo.setEnabled(!habilitar);
        btnBuscar.setEnabled(!habilitar);
    }

    // Método para limpar os campos da tela
    private void limparCampos() {
        txtCodigo.setText("");
        txtDescricao.setText("");
        txtValor.setText("");
    }

    // Método para preencher os campos ao buscar um título de dívida
    private void preencherCampos(TituloDivida titulo) {
        txtDescricao.setText(titulo.getDescricao());
        txtValor.setText(String.valueOf(titulo.getValor()));
    }
}
