package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroEntidade {

    private JFrame janela;
    private EntidadeMediator mediator = new EntidadeMediator();
    private JTextField campoCodigo;
    private JTextField campoNome;
    private JTextField campoRenda;
    private JButton botaoNovo;
    private JButton botaoBuscar;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoLimpar;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaCadastroEntidade tela = new TelaCadastroEntidade();
                tela.janela.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaCadastroEntidade() {
        configurarTela();
    }

    private void configurarTela() {
        janela = new JFrame("Cadastro de Entidade");
        janela.setSize(600, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.getContentPane().setLayout(new GridBagLayout());
        janela.getContentPane().setBackground(new Color(245, 245, 245));  // Leve ajuste na cor de fundo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);  // Ajuste leve nas margens
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel rotuloCodigo = new JLabel("Código:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        janela.getContentPane().add(rotuloCodigo, gbc);

        campoCodigo = new JTextField(15);
        gbc.gridx = 1;
        janela.getContentPane().add(campoCodigo, gbc);

        botaoNovo = new JButton("Adicionar");
        botaoNovo.setBackground(new Color(60, 160, 60));  // Alteração sutil na cor
        botaoNovo.setForeground(Color.WHITE);
        gbc.gridx = 2;
        janela.getContentPane().add(botaoNovo, gbc);

        botaoBuscar = new JButton("Procurar");
        botaoBuscar.setBackground(new Color(60, 120, 220));  // Alteração sutil na cor
        botaoBuscar.setForeground(Color.WHITE);
        gbc.gridx = 3;
        janela.getContentPane().add(botaoBuscar, gbc);

        JLabel rotuloNome = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        janela.getContentPane().add(rotuloNome, gbc);

        campoNome = new JTextField(15);
        campoNome.setEnabled(false);
        gbc.gridx = 1;
        janela.getContentPane().add(campoNome, gbc);

        JLabel rotuloRenda = new JLabel("Renda:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        janela.getContentPane().add(rotuloRenda, gbc);

        campoRenda = new JTextField(15);
        campoRenda.setEnabled(false);
        gbc.gridx = 1;
        janela.getContentPane().add(campoRenda, gbc);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.setEnabled(false);
        botaoSalvar.setBackground(new Color(220, 160, 60));  // Ajuste na cor
        botaoSalvar.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        janela.getContentPane().add(botaoSalvar, gbc);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setEnabled(false);
        botaoCancelar.setBackground(new Color(220, 60, 60));  // Alteração de cor
        botaoCancelar.setForeground(Color.WHITE);
        gbc.gridx = 1;
        janela.getContentPane().add(botaoCancelar, gbc);

        botaoLimpar = new JButton("Limpar");
        botaoLimpar.setBackground(new Color(120, 120, 120));  // Ajuste leve na cor
        botaoLimpar.setForeground(Color.WHITE);
        gbc.gridx = 2;
        janela.getContentPane().add(botaoLimpar, gbc);

        // Implementação dos eventos dos botões
        botaoNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Entidade entidade = mediator.buscar(campoCodigo.getText());
                if (entidade != null) {
                    JOptionPane.showMessageDialog(janela, "Entidade já cadastrada!");
                } else {
                    habilitarEdicao(true);
                }
            }
        });

        botaoBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Entidade entidade = mediator.buscar(campoCodigo.getText());
                if (entidade == null) {
                    JOptionPane.showMessageDialog(janela, "Entidade não encontrada!");
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
                    JOptionPane.showMessageDialog(janela, mensagem);
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
        botaoSalvar.setEnabled(habilitar);
        botaoCancelar.setEnabled(habilitar);
        botaoNovo.setEnabled(!habilitar);
        botaoBuscar.setEnabled(!habilitar);
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
