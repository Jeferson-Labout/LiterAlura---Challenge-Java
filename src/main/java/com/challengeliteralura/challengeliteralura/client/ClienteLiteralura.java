package com.challengeliteralura.challengeliteralura.client;

import java.util.List;
import java.util.Scanner;

import com.challengeliteralura.challengeliteralura.entity.LivroEntity;
import com.challengeliteralura.challengeliteralura.mapper.ConverteDados;
import com.challengeliteralura.challengeliteralura.model.Resposta;
import com.challengeliteralura.challengeliteralura.repository.AutorRepository;
import com.challengeliteralura.challengeliteralura.repository.LivroRepository;
import com.challengeliteralura.challengeliteralura.service.ConsumoAPI;
import com.challengeliteralura.challengeliteralura.entity.AutorEntity;

public class ClienteLiteralura {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private LivroRepository livroRepositorio;
    private AutorRepository autorRepositorio;

    public ClienteLiteralura(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void menu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    Escolha a opção através do seu número:
                        1.- Buscar livro por título
                        2.- Listar livros registrados
                        3.- Listar autores registrados
                        4.- Listar autores vivos em um determinado ano
                        5.- Listar livros por idioma
                        0 - Sair
                        """;
            System.out.println(menu);
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    buscarLivros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresVivos();
                    break;
                case 5:
                    buscarPorIdiomas();
                    break;
                case 0:
                    System.out.println("Adeus, volte logo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }

    private void buscarLivros() {

        List<LivroEntity> livros = livroRepositorio.findAll();

        if (!livros.isEmpty()) {

            for (LivroEntity livro : livros) {
                System.out.println("\n\n---------- LIVROS -------\n");
                System.out.println(" Título: " + livro.getTitulo());
                System.out.println(" Autor: " + livro.getAutor().getNome());
                System.out.println(" Idioma: " + livro.getIdioma());
                System.out.println(" Downloads: " + livro.getDownloads());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- NÃO FORAM ENCONTRADOS RESULTADOS ---- \n\n");
        }

    }

    private void buscarAutores() {
        List<AutorEntity> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- AUTORES -------\n");
                System.out.println(" Nome: " + autor.getNome());
                System.out.println(" Data de Nascimento: " + autor.getDataNascimento());
                System.out.println(" Data de Falecimento: " + autor.getDataFalecimento());
                System.out.println(" Livros: " + autor.getLivros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- NÃO FORAM ENCONTRADOS RESULTADOS ---- \n\n");

        }

    }

    private void buscarAutoresVivos() {
        System.out.println("Escreva o ano em que viveu: ");
        var ano = teclado.nextInt();
        teclado.nextLine();

        List<AutorEntity> autores = autorRepositorio.encontrarPorAno(ano);

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- AUTORES VIVOS -------\n");
                System.out.println(" Nome: " + autor.getNome());
                System.out.println(" Data de nascimento: " + autor.getDataNascimento());
                System.out.println(" Data de falecimento: " + autor.getDataFalecimento());
                System.out.println(" Livros: " + autor.getLivros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- NÃO FORAM ENCONTRADOS RESULTADOS ---- \n\n");

        }
    }

    private void buscarPorIdiomas() {

        var menu = """
                Selecione um Idioma:
                    1.- Espanhol
                    2.- Inglês
                
                    """;
        System.out.println(menu);
        var idioma = teclado.nextInt();
        teclado.nextLine();

        String selecao = "";

        if (idioma == 1) {
            selecao = "es";
        } else if (idioma == 2) {
            selecao = "en";
        }

        List<LivroEntity> livros = livroRepositorio.findForIdioma(selecao);

        if (!livros.isEmpty()) {

            for (LivroEntity livro : livros) {
                System.out.println("\n\n---------- LIVROS POR IDIOMA -------\n");
                System.out.println(" Título: " + livro.getTitulo());
                System.out.println(" Autor: " + livro.getAutor().getNome());
                System.out.println(" Idioma: " + livro.getIdioma());
                System.out.println(" Downloads: " + livro.getDownloads());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- NÃO FORAM ENCONTRADOS RESULTADOS ---- \n\n");
        }

    }

    private void buscarLivroWeb() {
        Resposta dados = getDadosSerie();

        if (!dados.resultados().isEmpty()) {

            LivroEntity livro = new LivroEntity(dados.resultados().get(0));
            livro = livroRepositorio.save(livro);

        }

        System.out.println("Dados: ");
        System.out.println(dados);
    }

    private Resposta getDadosSerie() {
        System.out.println("Insira o nome do livro que deseja buscar: ");
        var titulo = teclado.nextLine();
        titulo = titulo.replace(" ", "%20");
        System.out.println("Título: " + titulo);
        System.out.println(URL_BASE + titulo);
        var json = consumoApi.obterDados(URL_BASE + titulo);
        System.out.println(json);
        Resposta dados = conversor.obterDados(json, Resposta.class);
        return dados;
    }

}
