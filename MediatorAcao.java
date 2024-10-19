package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

public class GestorAcao {
    // Instância Singleton
    private static GestorAcao instanciaUnica = new GestorAcao();
    // Repositório de Ações
    private RepositorioAcao repositorioDeAcoes = new RepositorioAcao();

    // Construtor privado para garantir o padrão Singleton
    private GestorAcao() { }

    // Método para obter a instância única do Singleton
    public static GestorAcao obterInstancia() {
        return instanciaUnica;
    }

    // Método para validar os dados da ação
    private String verificarDados(Acao acao) {
        if (acao.getIdentificador() <= 0 || acao.getIdentificador() >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        if (acao.getNome() == null || acao.getNome().trim().isEmpty()) {
            return "O nome é obrigatório.";
        }
        if (acao.getNome().length() < 10 || acao.getNome().length() > 100) {
            return "O nome deve conter entre 10 e 100 caracteres.";
        }
        LocalDate hoje = LocalDate.now();
        if (acao.getDataDeValidade().isBefore(hoje.plusDays(30))) {
            return "A data de validade deve ser superior a 30 dias da data atual.";
        }
        if (acao.getValorUnitario() <= 0) {
            return "O valor unitário deve ser maior que zero.";
        }
        return null;
    }

    // Método para incluir uma nova ação
    public String adicionarAcao(Acao acao) {
        String validacao = verificarDados(acao);
        if (validacao == null) {
            boolean inserido = repositorioDeAcoes.incluir(acao);
            if (inserido) {
                return null;
            } else {
                return "Ação já cadastrada.";
            }
        } else {
            return validacao;
        }
    }

    // Método para alterar uma ação existente
    public String modificarAcao(Acao acao) {
        String validacao = verificarDados(acao);
        if (validacao == null) {
            boolean atualizado = repositorioDeAcoes.alterar(acao);
            if (atualizado) {
                return null;
            } else {
                return "Ação não encontrada.";
            }
        } else {
            return validacao;
        }
    }

    // Método para excluir uma ação pelo identificador
    public String removerAcao(int identificador) {
        if (identificador < 1 || identificador >= 100000) {
            return "Identificador inválido.";
        }
        boolean removido = repositorioDeAcoes.excluir(identificador);
        if (removido) {
            return null;
        } else {
            return "Ação não encontrada.";
        }
    }

    // Método para buscar uma ação pelo identificador
    public Acao localizarAcao(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioDeAcoes.buscar(identificador);
    }
}
