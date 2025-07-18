package br.com.alura.literalura.literalura.model;

public class Autor {
    private String nome;
    private int anoNascimento;
    private int anoFalecimento;

    public Autor(String nome, int anoNascimento, int anoFalecimento) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public int getAnoFalecimento() {
        return anoFalecimento;
    }

    @Override
    public String toString() {
        return nome + " (" + anoNascimento + " - " + (anoFalecimento == 0 ? "..." : anoFalecimento) + ")";
    }
}