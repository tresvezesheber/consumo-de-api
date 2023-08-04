package br.com.hebio.consumodeapi.domain.service;

import br.com.hebio.consumodeapi.domain.exception.ErroDeConversaoDeAnoException;
import br.com.hebio.consumodeapi.domain.model.Titulo;
import br.com.hebio.consumodeapi.domain.model.TituloOmdb;
import br.com.hebio.consumodeapi.infrastructure.OmdbApiConnector;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TituloService {

    @Autowired
    private OmdbApiConnector omdbApiConnector;

    public Titulo buscaPorTitulo(String nomeDoTitulo) {
        String tituloFormatado = formataStringTitulo(nomeDoTitulo);
        String corpoDaRequisicaoDeResposta = omdbApiConnector.requisicaoPorTitulo(tituloFormatado);
        TituloOmdb tituloOmdb = null;
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        tituloOmdb = gson.fromJson(corpoDaRequisicaoDeResposta, TituloOmdb.class);
        verificaAnoDeLancamento(tituloOmdb.year());
        Titulo titulo = new Titulo(tituloOmdb);
        return titulo;
    }

    public void verificaAnoDeLancamento(String anoDeLancamento) {
        if (anoDeLancamento.length() > 4) {
            throw new ErroDeConversaoDeAnoException("Não foi possível converter o ano, " +
                    "pois contém mais de 4 caracteres");
        }
    }

    public String formataStringTitulo(String titulo) {
        String tituloFormatado = titulo.replace(" ", "+");
        return tituloFormatado;
    }
}
