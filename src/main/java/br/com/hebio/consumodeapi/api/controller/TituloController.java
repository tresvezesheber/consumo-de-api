package br.com.hebio.consumodeapi.api.controller;

import br.com.hebio.consumodeapi.domain.model.Titulo;
import br.com.hebio.consumodeapi.domain.service.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/titulos")
public class TituloController {

    @Autowired
    private TituloService tituloService;

    @GetMapping
    public ResponseEntity<Titulo> buscaPorNomeDoTitulo(@RequestParam String nome) {
        Titulo titulo = tituloService.buscaPorTitulo(nome);
        return ResponseEntity.ok(titulo);
    }
}
