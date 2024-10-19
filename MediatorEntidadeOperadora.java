package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class ControladorEntidadeOperadora {
    // Instância única (Singleton)
    private static ControladorEntidadeOperadora instanciaUnica = new ControladorEntidadeOperadora();
    
    // Repositório de Entidades Operadoras
    private final RepositorioEntidadeOperadora repositorioEntidade = new RepositorioEntidadeOperadora();

    // Construtor privado para o padrão Singleton
    private ControladorEntidadeOperadora() { }

    // Método para obter a instância única do Singleton
    public static ControladorEntidadeOperadora obterInstancia() {
        return instanciaUnica;
    }

    // Método para validar os dados da Entidade Operadora
    private String verificarDados(EntidadeOperadora entidadeOperadora) {
        if (entidadeOperadora.getIdentificador() <= 0 || entidadeOperadora.getIdentificador() >= 100000) {
            return "O identificador deve estar entre 1 e 99999.";
        }
        if (entidadeOperadora.getNome() == null || entidadeOperadora.getNome().trim().isEmpty()) {
            return "O nome da entidade é obrigatório.";
        }
        if (entidadeOperadora.getNome().length() < 10 || entidadeOperadora.getNome().length() > 100) {
            return "O nome da entidade deve conter entre 10 e 100 caracteres.";
        }
        if (!entidadeOperadora.isAutorizadoAcao()) {
            return "A ação deve ser autorizada.";
        }
        if (entidadeOperadora.getSaldoAcao() < 0) {
            return "O saldo da ação deve ser maior ou igual a zero.";
        }
        if (entidadeOperadora.getSaldoTituloDivida() < 0) {
            return "O saldo do título da dívida deve ser maior ou igual a zero.";
        }
        return null;
    }

    // Método para incluir uma nova Entidade Operadora
    public String adicionarEntidade(EntidadeOperadora entidade) {
        String validacao = verificarDados(entidade);
        if (validacao == null) {
            boolean adicionada = repositorioEntidade.incluir(entidade);
            return adicionada ? null : "Entidade já cadastrada.";
        } else {
            return validacao;
        }
    }

    // Método para alterar uma Entidade Operadora existente
    public String modificarEntidade(EntidadeOperadora entidade) {
        String validacao = verificarDados(entidade);
        if (validacao == null) {
            boolean alterada = repositorioEntidade.alterar(entidade);
            return alterada ? null : "Entidade não encontrada.";
        } else {
            return validacao;
        }
    }

    // Método para excluir uma Entidade Operadora pelo identificador
    public String removerEntidade(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return "Identificador inválido.";
        }
        boolean removida = repositorioEntidade.excluir(identificador);
        return removida ? null : "Entidade não encontrada.";
    }

    // Método para buscar uma Entidade Operadora pelo identificador
    public EntidadeOperadora localizarEntidade(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioEntidade.buscar(identificador);
    }
}
