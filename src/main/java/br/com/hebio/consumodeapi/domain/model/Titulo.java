package br.com.hebio.consumodeapi.domain.model;

public class Titulo {
    //@SerializedName("Title")
    private String nome;

    //@SerializedName("Year")
    private int anoDeLancamento;

    private int duracaoEmMinutos;

    public String getNome() {
        return nome;
    }

    public int getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public Titulo(TituloOmdb tituloOmdb) {
        this.nome = tituloOmdb.title();
        this.anoDeLancamento = Integer.parseInt(tituloOmdb.year());
        this.duracaoEmMinutos = Integer.valueOf(tituloOmdb.runtime().substring(0, 3).trim());
    }

    @Override
    public String toString() {
        return nome + " (" + anoDeLancamento +
                "), Duração: " + duracaoEmMinutos;
    }
}
