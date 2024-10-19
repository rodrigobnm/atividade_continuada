package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;

public class MediadorTituloDivida {

    // Singleton: Instância única
    private static final MediadorTituloDivida instanciaUnica = new MediadorTituloDivida();
    private final RepositorioTituloDivida repositorioTituloDivida = new RepositorioTituloDivida();

    // Construtor privado para evitar múltiplas instâncias
    private MediadorTituloDivida() { }

    // Método para acessar a única instância
    public static MediadorTituloDivida obterInstancia() {
        return instanciaUnica;
    }

    // Método para validar o título da dívida
    private String validarTitulo(TituloDivida titulo) {
        // Validação do identificador
        if (titulo.getIdentificador() <= 0 || titulo.getIdentificador() >= 100000) {
            return "Identificador deve ser maior que zero e menor que 100000.";
        }
        // Validação do nome
        String nomeTitulo = titulo.getNome();
        if (nomeTitulo == null || nomeTitulo.trim().isEmpty()) {
            return "Nome deve ser preenchido e não pode ser nulo.";
        }
        if (nomeTitulo.length() < 10 || nomeTitulo.length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        // Validação da data de validade
        LocalDate dataAtual = LocalDate.now();
        if (titulo.getDataDeValidade().isBefore(dataAtual.plusDays(180))) {
            return "Data de validade deve ser superior a 180 dias a partir de hoje.";
        }
        // Validação da taxa de juros
        if (titulo.getTaxaJuros() < 0) {
            return "Taxa de juros deve ser não negativa.";
        }
        return null; // Validação sem erros
    }

    // Método para incluir um título
    public String adicionarTitulo(TituloDivida titulo) {
        String resultadoValidacao = validarTitulo(titulo);
        if (resultadoValidacao == null) {
            if (repositorioTituloDivida.incluir(titulo)) {
                return null; // Inclusão bem-sucedida
            } else {
                return "Título já existe no repositório.";
            }
        }
        return resultadoValidacao; // Retorna o erro de validação, se houver
    }

    // Método para alterar um título existente
    public String modificarTitulo(TituloDivida titulo) {
        String resultadoValidacao = validarTitulo(titulo);
        if (resultadoValidacao == null) {
            if (repositorioTituloDivida.alterar(titulo)) {
                return null; // Alteração bem-sucedida
            } else {
                return "Título não encontrado no repositório.";
            }
        }
        return resultadoValidacao; // Retorna erro de validação, se houver
    }

    // Método para remover um título pelo identificador
    public String removerTitulo(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return "Identificador fora do intervalo permitido.";
        }
        if (repositorioTituloDivida.excluir(identificador)) {
            return null; // Exclusão bem-sucedida
        } else {
            return "Título não encontrado para exclusão.";
        }
    }

    // Método para buscar um título pelo identificador
    public TituloDivida encontrarTitulo(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null; // Identificador inválido
        }
        return repositorioTituloDivida.buscar(identificador);
    }
}
