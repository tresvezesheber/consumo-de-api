package br.com.hebio.consumodeapi.api.exceptionhandler;

import br.com.hebio.consumodeapi.domain.exception.ErroDeConversaoDeAnoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource mensagemFonte;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ProblemDetail detalheDoProblema = ProblemDetail.forStatus(status);
        detalheDoProblema.setTitle("Um ou mais campos estão inválidos");
        detalheDoProblema.setType(URI.create("https://vouchers.fusve.com.br/erros/campos-invalidos"));

        Map<String, String> fields = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(objectError -> ((FieldError) objectError).getField(),
                        objectError -> mensagemFonte.getMessage(objectError, LocaleContextHolder.getLocale())));

        detalheDoProblema.setProperty("fields", fields);

        return handleExceptionInternal(ex, detalheDoProblema, headers, status, request);
    }

    @ExceptionHandler(ErroDeConversaoDeAnoException.class)
    public ProblemDetail handleErroDeConversaoDeAno(ErroDeConversaoDeAnoException e) {
        ProblemDetail detalheDoProblema = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        detalheDoProblema.setTitle("Erro na converssão do ano");
        detalheDoProblema.setDetail(e.getMessage());
        detalheDoProblema.setType(URI.create("https://omdb.hebio.com.br/erros/erro-conversao-ano"));

        return detalheDoProblema;
    }
}
