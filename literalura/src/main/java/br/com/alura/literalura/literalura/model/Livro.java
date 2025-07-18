package br.com.alura.literalura.literalura.model;

public class Livro {
    private String titulo;
    private String autor;
    private int nascimentoAutor;
    private int falecimentoAutor;
    private int downloads;
    private String idioma;

    public Livro(String titulo, String autor, int nascimentoAutor, int falecimentoAutor, int downloads, String idioma) {
        this.titulo = titulo;
        this.autor = autor;
        this.nascimentoAutor = nascimentoAutor;
        this.falecimentoAutor = falecimentoAutor;
        this.downloads = downloads;
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getNascimentoAutor() {
        return nascimentoAutor;
    }

    public int getFalecimentoAutor() {
        return falecimentoAutor;
    }

    public int getDownloads() {
        return downloads;
    }

    public String getIdioma() {
        return idioma;
    }

    @Override
    public String toString() {
        String falecimentoTexto = falecimentoAutor == 0 ? "vivo" : String.valueOf(falecimentoAutor);
        return "\n----- LIVRO -----" +
                "\nTítulo: " + titulo +
                "\nAutor: " + autor +
                "\nAno de nascimento: " + nascimentoAutor +
                "\nAno de falecimento: " + falecimentoTexto +
                "\nIdioma: " + idioma +
                "\nNúmero de downloads: " + downloads +
                "\n-----------------\n";
    }
}