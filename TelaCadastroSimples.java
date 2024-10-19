package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroSimples {

    private JFrame tela;
    private static EntidadeMediator mediator = new EntidadeMediator();
    private JTextField campoCodigo;
    private JTextField campoNome;
    private JTextField campoRenda;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaCadastroSimples app = new TelaCadastroSimples();
                app.tela.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaCadastroSimples() {
        configurarTela();
    }

    private void configurarTela() {
        tela = new JFrame("Cadastro de Entidade");
        tela.setSize(600, 400);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.getContentPane().setLayout(new GridBagLayout());
        tela.getContentPane().setBackground(new Color(245, 245, 245)); // Cor de fundo levemente ajustada

        GridBagConstraints config = new GridBagConstraints();
        config.fill = GridBagConstraints.HORIZONTAL;
        config.insets = new Insets(6, 6, 6, 6);

        JLabel labelCodigo = new JLabel("Código:");
        config.gridx = 0;
        config.gridy = 0;
        tela.getContentPane().add(labelCodigo, config);

        campoCodigo = new JTextField(10);
        config.gridx = 1;
        tela.getContentPane().add(campoCodigo, config);

        JButton botaoNovo = new JButton("Adicionar");
        botaoNovo.setBackground(new Color(60, 160, 60)); // Mudança leve na cor
        botaoNovo.setForeground(Color.WHITE);
        config.gridx = 2;
        tela.getContentPane().add(botaoNovo, config);

        JButton botaoBuscar = new JButton("Localizar");
        botaoBuscar.setBackground(new Color(60, 120, 220)); // Mudança leve na cor
        botaoBuscar.setForeground(Color.WHITE);
        config.gridx = 3;
        tela.getContentPane().add(botaoBuscar, config);

        JLabel labelNome = new JLabel("Nome:");
        config.gridx = 0;
        config.gridy = 1;
        tela.getContentPane().add(labelNome, config);

        campoNome = new JTextField(15);
        campoNome.setEnabled(false);
        config.gridx = 1;
        tela.getContentPane().add(campoNome, config);

        JLabel labelRenda = new JLabel("Renda:");
        config.gridx = 0;
        config.gridy = 2;
        tela.getContentPane().add(labelRenda, config);

        campoRenda = new JTextField(10);
        campoRenda.setEnabled(false);
        config.gridx = 1;
        tela.getContentPane().add(campoRenda, config);

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.setEnabled(false);
        botaoSalvar.setBackground(new Color(220, 160, 60)); // Cor levemente ajustada
        botaoSalvar.setForeground(Color.WHITE);
        config.gridx = 0;
        config.gridy = 3;
        tela.getContentPane().add(botaoSalvar, config);

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setEnabled(false);
        botaoCancelar.setBackground(new Color(220, 60, 60)); // Cor levemente ajustada
        botaoCancelar.setForeground(Color.WHITE);
        config.gridx = 1;
        tela.getContentPane().add(botaoCancelar, config);

        JButton botaoLimpar = new JButton("Limpar");
        botaoLimpar.setBackground(new Color(120, 120, 120)); // Cor levemente ajustada
        botaoLimpar.setForeground(Color.WHITE);
        config.gridx = 2;
        tela.getContentPane().add(botaoLimpar, config);

        // Lógica dos botões
        botaoNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Entidade entidade = mediator.buscar(campoCodigo.getText());
                if (entidade != null) {
                    JOptionPane.showMessageDialog(tela, "Entidade já existe!");
                } else {
                    habilitarEdicao(true);
                }
            }
        });

        botaoBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Entidade entidade = mediator.buscar(campoCodigo.getText());
                if (entidade == null) {
                    JOptionPane.showMessageDialog(tela, "Entidade não encontrada!");
                } else {
                    preencherCampos(entidade);
                    habilitarEdicao(true);
                    botaoSalvar.setText("Atualizar");
                }
            }
        });

        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Entidade entidade = new Entidade(campoCodigo.getText(), campoNome.getText(),
                        Double.parseDouble(campoRenda.getText()));
                String mensagem;
                if (botaoSalvar.getText().equals("Salvar")) {
                    mensagem = mediator.incluir(entidade);
                } else {
                    mensagem = mediator.alterar(entidade);
                }

                if (mensagem != null) {
                    JOptionPane.showMessageDialog(tela, mensagem);
                } else {
                    limparCampos();
                    habilitarEdicao(false);
                    botaoSalvar.setText("Salvar");
                }
            }
        });

        botaoCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                habilitarEdicao(false);
                botaoSalvar.setText("Salvar");
            }
        });

        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
    }

    private void habilitarEdicao(boolean habilitar) {
        campoNome.setEnabled(habilitar);
        campoRenda.setEnabled(habilitar);
        campoCodigo.setEnabled(!habilitar);
        tela.getRootPane().getGlassPane().setVisible(!habilitar);
    }

    private void limparCampos() {
        campoCodigo.setText("");
        campoNome.setText("");
        campoRenda.setText("");
    }

    private void preencherCampos(Entidade entidade) {
        campoNome.setText(entidade.getNome());
        campoRenda.setText(String.valueOf(entidade.getRenda()));
    }
}
