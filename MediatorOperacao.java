package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDateTime;
import java.util.Arrays;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

public class ControladorOperacao {
    private final ControladorAcao controladorAcao = ControladorAcao.obterInstancia();
    private final ControladorTituloDivida controladorTituloDivida = ControladorTituloDivida.obterInstancia();
    private final ControladorEntidadeOperadora controladorEntidadeOperadora = ControladorEntidadeOperadora.obterInstancia();
    private final RepositorioTransacao repositorioTransacao = new RepositorioTransacao();
    private static ControladorOperacao instanciaUnica;

    private ControladorOperacao() { }

    public static ControladorOperacao obterInstancia() {
        if (instanciaUnica == null) {
            synchronized (ControladorOperacao.class) {
                if (instanciaUnica == null) {
                    instanciaUnica = new ControladorOperacao();
                }
            }
        }
        return instanciaUnica;
    }

    public String processarOperacao(boolean ehAcao, int idEntidadeCredito, int idEntidadeDebito, int idAcaoOuTitulo, double valorTransacao) {
        if (valorTransacao <= 0) {
            return "Valor inválido";
        }
        EntidadeOperadora entidadeCredito = controladorEntidadeOperadora.localizarEntidade(idEntidadeCredito);
        if (entidadeCredito == null) {
            return "Entidade de crédito não encontrada";
        }
        EntidadeOperadora entidadeDebito = controladorEntidadeOperadora.localizarEntidade(idEntidadeDebito);
        if (entidadeDebito == null) {
            return "Entidade de débito não encontrada";
        }
        if (ehAcao && !entidadeCredito.isAutorizadoAcao()) {
            return "Entidade de crédito sem autorização para ações";
        }
        if (ehAcao && !entidadeDebito.isAutorizadoAcao()) {
            return "Entidade de débito sem autorização para ações";
        }

        Acao acao = null;
        TituloDivida titulo = null;
        if (ehAcao) {
            acao = controladorAcao.localizarAcao(idAcaoOuTitulo);
            if (acao == null) {
                return "Ação não encontrada";
            }
        } else {
            titulo = controladorTituloDivida.localizarTitulo(idAcaoOuTitulo);
            if (titulo == null) {
                return "Título não encontrado";
            }
        }

        if (ehAcao) {
            if (entidadeDebito.getSaldoAcao() < valorTransacao) {
                return "Saldo insuficiente na entidade de débito";
            }
            if (acao.getValorUnitario() > valorTransacao) {
                return "Valor da operação menor que o valor unitário da ação";
            }
        } else {
            if (entidadeDebito.getSaldoTituloDivida() < valorTransacao) {
                return "Saldo insuficiente na entidade de débito";
            }
        }

        double valorFinalOperacao = ehAcao ? valorTransacao : titulo.calcularPrecoTransacao(valorTransacao);

        if (ehAcao) {
            entidadeCredito.creditarSaldoAcao(valorFinalOperacao);
            entidadeDebito.debitarSaldoAcao(valorFinalOperacao);
        } else {
            entidadeCredito.creditarSaldoTituloDivida(valorFinalOperacao);
            entidadeDebito.debitarSaldoTituloDivida(valorFinalOperacao);
        }

        String resultadoCredito = controladorEntidadeOperadora.modificarEntidade(entidadeCredito);
        if (resultadoCredito != null) {
            return resultadoCredito;
        }
        String resultadoDebito = controladorEntidadeOperadora.modificarEntidade(entidadeDebito);
        if (resultadoDebito != null) {
            return resultadoDebito;
        }

        Transacao novaTransacao = new Transacao(
            entidadeCredito, entidadeDebito, ehAcao ? acao : null, ehAcao ? null : titulo,
            valorFinalOperacao, LocalDateTime.now()
        );
        repositorioTransacao.incluir(novaTransacao);
        return "Operação concluída com sucesso";
    }

    public Transacao[] emitirExtrato(int idEntidade) {
        Transacao[] transacoesCredito = repositorioTransacao.buscarPorEntidadeCredora(idEntidade);
        Transacao[] transacoesDebito = repositorioTransacao.buscarPorEntidadeDevedora(idEntidade);

        Transacao[] extratoCompleto = new Transacao[transacoesCredito.length + transacoesDebito.length];
        System.arraycopy(transacoesCredito, 0, extratoCompleto, 0, transacoesCredito.length);
        System.arraycopy(transacoesDebito, 0, extratoCompleto, transacoesCredito.length, transacoesDebito.length);

        Arrays.sort(extratoCompleto, (transacao1, transacao2) -> transacao2.getDataHoraOperacao().compareTo(transacao1.getDataHoraOperacao()));

        return extratoCompleto;
    }
}
