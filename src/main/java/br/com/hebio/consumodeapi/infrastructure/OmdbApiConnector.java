package br.com.hebio.consumodeapi.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OmdbApiConnector {

    @Value("${omdb.api.key}")
    static final String API_KEY = "7150e3ff";
    static final String BASE_URL = "https://omdbapi.com/?apikey=";
    static final String BUSCA_POR_TITULO = "&t=";

    public String executaRequisicao(String parametrosDaRequisicao) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + API_KEY + parametrosDaRequisicao))
                .build();
        HttpResponse<String> response = null;

        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body().toString();
    }

    public String requisicaoPorTitulo(String titulo) {
        return executaRequisicao(BUSCA_POR_TITULO + titulo);
    }
}
