package br.com.alura.literalura.literalura.view;

import br.com.alura.literalura.literalura.model.Livro;
import br.com.alura.literalura.literalura.repository.LivroRepository;
import br.com.alura.literalura.literalura.service.GutendexService;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final GutendexService service = new GutendexService();
    private final LivroRepository livroRepository;
    private List<Livro> livrosRegistrados = new ArrayList<>();

    public Menu(LivroRepository repository) {
        this.livroRepository = repository;
    }

    public void exibirMenu() throws Exception {
        int opcao = -1;

        do {
            System.out.println("\n=== LiterAlura ===");
            System.out.println("1 - Buscar livro pelo título");
            System.out.println("2 - Listar livros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos em determinado ano");
            System.out.println("5 - Listar livros em determinado idioma");
            System.out.println("6 - Ver estatísticas de popularidade");
            System.out.println("7 - Ver os 10 livros mais populares");
            System.out.println("8 - Listar idiomas disponíveis");
            System.out.println("9 - Buscar livros por autor registrado");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(" Entrada inválida. Digite apenas números.");
                scanner.nextLine();
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosPorAno();
                case 5 -> listarPorIdioma();
                case 6 -> mostrarEstatisticas();
                case 7 -> mostrarTop10();
                case 8 -> listarIdiomas();
                case 9 -> buscarPorAutorRegistrado();
                case 0 -> System.out.println(" Encerrando LiterAlura. Até a próxima leitura!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void buscarLivroPorTitulo() throws Exception {
        System.out.print("Digite o título ou termo de busca: ");
        String termo = scanner.nextLine();

        List<Livro> encontrados = service.buscarLivros(termo);

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            encontrados.forEach(System.out::println);
            System.out.println("\nDeseja registrar os livros encontrados? (s/n)");
            String resposta = scanner.nextLine().trim().toLowerCase();

            livrosRegistrados = livroRepository.listarTodos();
            int registrados = 0;

            if (resposta.equals("s")) {
                for (Livro livro : encontrados) {
                    if (!livroJaRegistrado(livro)) {
                        livroRepository.salvarLivro(livro);
                        registrados++;
                    }
                }
                System.out.println(" " + registrados + " novos livros registrados.");
            }
        }
    }

    private boolean livroJaRegistrado(Livro novo) {
        return livrosRegistrados.stream().anyMatch(l ->
                l.getTitulo().equalsIgnoreCase(novo.getTitulo()) &&
                        l.getAutor().equalsIgnoreCase(novo.getAutor())
        );
    }

    private void listarLivros() {
        livrosRegistrados = livroRepository.listarTodos();
        if (livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            livrosRegistrados.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        livrosRegistrados = livroRepository.listarTodos();
        Set<String> autores = livrosRegistrados.stream()
                .map(Livro::getAutor)
                .collect(Collectors.toCollection(TreeSet::new));

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            autores.forEach(a -> System.out.println(" " + a));
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.print("Digite o ano mínimo de nascimento: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        livrosRegistrados = livroRepository.listarTodos();

        List<String> vivos = livrosRegistrados.stream()
                .filter(l -> l.getNascimentoAutor() >= ano && l.getFalecimentoAutor() == 0)
                .map(Livro::getAutor)
                .distinct()
                .toList();

        if (vivos.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado para o ano informado.");
        } else {
            System.out.println("Autores vivos nascidos após " + ano + ":");
            vivos.forEach(System.out::println);
        }
    }

    private void listarPorIdioma() {
        livrosRegistrados = livroRepository.listarTodos();

        System.out.println("\nInsira o idioma para realizar a busca:");
        System.out.println("pt - português");
        System.out.println("en - inglês");
        System.out.println("es - espanhol");
        System.out.println("fr - francês");

        System.out.print("Idioma: ");
        String idiomaEscolhido = scanner.nextLine().trim().toLowerCase();

        List<Livro> livrosFiltrados = livrosRegistrados.stream()
                .filter(livro -> livro.getIdioma().equalsIgnoreCase(idiomaEscolhido))
                .toList();

        if (livrosFiltrados.isEmpty()) {
            System.out.println(" Nenhum livro registrado no idioma informado.");
        } else {
            System.out.println("\n Livros encontrados no idioma \"" + idiomaEscolhido + "\":");
            livrosFiltrados.forEach(System.out::println);
        }
    }

    private void mostrarEstatisticas() {
        livrosRegistrados = livroRepository.listarTodos();

        if (livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        IntSummaryStatistics stats = livrosRegistrados.stream()
                .mapToInt(Livro::getDownloads)
                .summaryStatistics();

        System.out.println(" Estatísticas de popularidade:");
        System.out.println(" Total de downloads: " + stats.getSum());
        System.out.println(" Média de downloads: " + stats.getAverage());
        System.out.println(" Mínimo: " + stats.getMin());
        System.out.println(" Máximo: " + stats.getMax());
    }

    private void mostrarTop10() {
        livrosRegistrados = livroRepository.listarTodos();

        if (livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        livrosRegistrados.stream()
                .sorted(Comparator.comparingInt(Livro::getDownloads).reversed())
                .limit(10)
                .forEach(System.out::println);
    }

    private void listarIdiomas() {
        livrosRegistrados = livroRepository.listarTodos();
        Set<String> idiomas = livrosRegistrados.stream()
                .map(Livro::getIdioma)
                .collect(Collectors.toCollection(TreeSet::new));

        if (idiomas.isEmpty()) {
            System.out.println("Nenhum idioma registrado.");
        } else {
            System.out.println("Idiomas disponíveis:");
            idiomas.forEach(id -> System.out.println(" " + id));
        }
    }

    private void buscarPorAutorRegistrado() {
        livrosRegistrados = livroRepository.listarTodos();
        System.out.print("Digite o nome (ou parte do nome) do autor: ");
        String entrada = scanner.nextLine();
        String nome = normalizarTexto(entrada);

        List<Livro> encontrados = livrosRegistrados.stream()
                .filter(l -> normalizarTexto(l.getAutor()).contains(nome))
                .toList();

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o autor informado.");
        } else {
            encontrados.forEach(System.out::println);
        }
    }

    private String normalizarTexto(String texto) {
        if (texto == null) return "";
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase()
                .trim();
    }
}