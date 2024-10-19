package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Entidade {
    private String id;
    private String descricao;
    private double salario;

    private JLabel idLabel;
    private JLabel descricaoLabel;
    private JLabel salarioLabel;

    private JTextField idInput;
    private JTextField descricaoInput;
    private JTextField salarioInput;

    public Entidade(String id, String descricao, double salario) {
        this.id = id;
        this.descricao = descricao;
        this.salario = salario;
        configurarJanela();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        idLabel.setText("Código: " + id);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        descricaoLabel.setText("Nome: " + descricao);
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
        salarioLabel.setText("Renda: " + salario);
    }

    private void configurarJanela() {
        JFrame janela = new JFrame("Detalhes da Entidade");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(450, 250);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints configuracao = new GridBagConstraints();
        configuracao.fill = GridBagConstraints.HORIZONTAL;
        configuracao.insets = new Insets(8, 8, 8, 8);

        idInput = new JTextField(id, 15);
        descricaoInput = new JTextField(descricao, 15);
        salarioInput = new JTextField(String.valueOf(salario), 15);

        idLabel = new JLabel("Código: " + id);
        descricaoLabel = new JLabel("Nome: " + descricao);
        salarioLabel = new JLabel("Renda: " + salario);

        JButton botaoAtualizar = new JButton("Salvar Alterações");
        botaoAtualizar.setBackground(new Color(0, 150, 200));
        botaoAtualizar.setForeground(Color.WHITE);

        botaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setId(idInput.getText());
                setDescricao(descricaoInput.getText());
                try {
                    setSalario(Double.parseDouble(salarioInput.getText()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(janela, "Valor inválido para renda. Tente novamente.");
                }
            }
        });

        configuracao.gridx = 0;
        configuracao.gridy = 0;
        painel.add(new JLabel("Informe o código:"), configuracao);
        configuracao.gridx = 1;
        painel.add(idInput, configuracao);

        configuracao.gridx = 0;
        configuracao.gridy = 1;
        painel.add(new JLabel("Informe o nome:"), configuracao);
        configuracao.gridx = 1;
        painel.add(descricaoInput, configuracao);

        configuracao.gridx = 0;
        configuracao.gridy = 2;
        painel.add(new JLabel("Informe a renda:"), configuracao);
        configuracao.gridx = 1;
        painel.add(salarioInput, configuracao);

        configuracao.gridx = 0;
        configuracao.gridy = 3;
        painel.add(idLabel, configuracao);
        configuracao.gridx = 1;
        painel.add(descricaoLabel, configuracao);
        configuracao.gridx = 2;
        painel.add(salarioLabel, configuracao);

        configuracao.gridx = 1;
        configuracao.gridy = 4;
        configuracao.gridwidth = 1;
        configuracao.anchor = GridBagConstraints.CENTER;
        painel.add(botaoAtualizar, configuracao);

        janela.add(painel);
        janela.setVisible(true);
    }

    public static void main(String[] args) {
        new Entidade("001", "Indefinido", 0.0);
    }
}
