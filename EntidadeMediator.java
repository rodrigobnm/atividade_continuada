package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntidadeController {
    private JFrame janela;
    private JTextField campoCodigo, campoNome, campoSalario;
    private JLabel mensagemLabel;
    private Entidade entidadeAtual;

    public EntidadeController() {
        configurarInterface();
    }

    public String adicionarEntidade(Entidade entidade) {
        String validacao = validarDados(entidade);
        if (validacao == null) {
            mensagemLabel.setText("Entidade adicionada com sucesso!");
        }
        return validacao;
    }

    public String atualizarEntidade(Entidade entidade) {
        String validacao = validarDados(entidade);
        if (validacao == null) {
            mensagemLabel.setText("Entidade atualizada com sucesso!");
        }
        return validacao;
    }

    private String validarDados(Entidade entidade) {
        if (entidade.getNome() == null || entidade.getNome().trim().isEmpty()) {
            return "Nome não pode estar vazio";
        } else {
            return null;
        }
    }

    public Entidade consultarEntidade(String codigo) {
        if (codigo == null || !codigo.equals("001")) {
            return null;
        } else {
            return new Entidade("001", "João", 1500.00);
        }
    }

    private void configurarInterface() {
        janela = new JFrame("Gerenciamento de Entidade");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(450, 300);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints configuracao = new GridBagConstraints();
        configuracao.fill = GridBagConstraints.HORIZONTAL;
        configuracao.insets = new Insets(8, 8, 8, 8);

        campoCodigo = new JTextField(15);
        campoNome = new JTextField(15);
        campoSalario = new JTextField(15);

        JLabel labelCodigo = new JLabel("Código:");
        JLabel labelNome = new JLabel("Nome:");
        JLabel labelSalario = new JLabel("Salário:");
        mensagemLabel = new JLabel("Resultado:");

        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoAlterar = new JButton("Alterar");
        JButton botaoConsultar = new JButton("Consultar");

        botaoAdicionar.setBackground(new Color(0, 130, 230));
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAlterar.setBackground(new Color(0, 130, 230));
        botaoAlterar.setForeground(Color.WHITE);
        botaoConsultar.setBackground(new Color(0, 130, 230));
        botaoConsultar.setForeground(Color.WHITE);

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Entidade novaEntidade = new Entidade(campoCodigo.getText(), campoNome.getText(), Double.parseDouble(campoSalario.getText()));
                String resultado = adicionarEntidade(novaEntidade);
                if (resultado != null) {
                    mensagemLabel.setText(resultado);
                }
            }
        });

        botaoAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (entidadeAtual != null) {
                    entidadeAtual.setNome(campoNome.getText());
                    entidadeAtual.setRenda(Double.parseDouble(campoSalario.getText()));
                    String resultado = atualizarEntidade(entidadeAtual);
                    if (resultado != null) {
                        mensagemLabel.setText(resultado);
                    }
                } else {
                    mensagemLabel.setText("Entidade não encontrada para alteração.");
                }
            }
        });

        botaoConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entidadeAtual = consultarEntidade(campoCodigo.getText());
                if (entidadeAtual != null) {
                    campoNome.setText(entidadeAtual.getNome());
                    campoSalario.setText(String.valueOf(entidadeAtual.getRenda()));
                    mensagemLabel.setText("Entidade localizada.");
                } else {
                    mensagemLabel.setText("Entidade não encontrada.");
                }
            }
        });

        configuracao.gridx = 0;
        configuracao.gridy = 0;
        painel.add(labelCodigo, configuracao);
        configuracao.gridx = 1;
        painel.add(campoCodigo, configuracao);

        configuracao.gridx = 0;
        configuracao.gridy = 1;
        painel.add(labelNome, configuracao);
        configuracao.gridx = 1;
        painel.add(campoNome, configuracao);

        configuracao.gridx = 0;
        configuracao.gridy = 2;
        painel.add(labelSalario, configuracao);
        configuracao.gridx = 1;
        painel.add(campoSalario, configuracao);

        configuracao.gridx = 0;
        configuracao.gridy = 3;
        painel.add(botaoAdicionar, configuracao);
        configuracao.gridx = 1;
        painel.add(botaoAlterar, configuracao);

        configuracao.gridx = 0;
        configuracao.gridy = 4;
        configuracao.gridwidth = 2;
        painel.add(botaoConsultar, configuracao);
        configuracao.gridy = 5;
        painel.add(mensagemLabel, configuracao);

        janela.add(painel);
        janela.setVisible(true);
    }

    public static void main(String[] args) {
        new EntidadeController();
    }
}
