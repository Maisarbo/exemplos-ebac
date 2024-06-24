package br.com.maisa.categoria_meme.controllers;

import br.com.maisa.categoria_meme.entidades.CategoriaMeme;
import br.com.maisa.categoria_meme.servicos.ServicoCategoriaMeme;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/categoria-meme")
public class ControllerCategoriaMeme {

    private static final Logger log = LoggerFactory.getLogger(ControllerCategoriaMeme.class);

    private final ServicoCategoriaMeme servicoCategoriaMeme;
    private final MeterRegistry meterRegistry;

    public ControllerCategoriaMeme(ServicoCategoriaMeme servicoCategoriaMeme, MeterRegistry meterRegistry) {
        this.servicoCategoriaMeme = servicoCategoriaMeme;
        this.meterRegistry = meterRegistry;
    }

    @PostMapping
    public CategoriaMeme criarCategoriaMeme(@RequestBody CategoriaMeme categoriaMeme) {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            log.info("Recebida requisição para criar categoria de meme: {}", categoriaMeme);
            CategoriaMeme createdCategoriaMeme = servicoCategoriaMeme.criarCategoriaMeme(categoriaMeme);
            meterRegistry.counter("categoria_meme.criar.sucesso").increment();
            return createdCategoriaMeme;
        } catch (Exception e) {
            meterRegistry.counter("categoria_meme.criar.erro").increment();
            log.error("Erro ao processar requisição para criar categoria de meme", e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("categoria_meme.criar.tempo"));
        }
    }

    @GetMapping
    public Iterable<CategoriaMeme> encontrarTodos() {
        Timer.Sample sample = Timer.start(meterRegistry);
        log.info("Recebida requisição para encontrar todas as categorias de memes");
        try {
            Iterable<CategoriaMeme> categorias = servicoCategoriaMeme.encontrarTodos();
            meterRegistry.counter("categoria_meme.encontrarTodos.sucesso").increment();
            return categorias;
        } catch (Exception e) {
            meterRegistry.counter("categoria_meme.encontrarTodos.erro").increment();
            log.error("Erro ao processar requisição para encontrar todas as categorias de memes", e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("categoria_meme.encontrarTodos.tempo"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaMeme> findById(@PathVariable String id) {
        Timer.Sample sample = Timer.start(meterRegistry);
        log.info("Recebida requisição para encontrar categoria de meme por ID: {}", id);
        try {
            CategoriaMeme categoriaMeme = servicoCategoriaMeme.findById(id);
            if (categoriaMeme != null) {
                meterRegistry.counter("categoria_meme.encontrarPorId.sucesso").increment();
                return ResponseEntity.ok(categoriaMeme);
            } else {
                meterRegistry.counter("categoria_meme.encontrarPorId.naoEncontrado").increment();
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            meterRegistry.counter("categoria_meme.encontrarPorId.erro").increment();
            log.error("Erro ao processar requisição para encontrar categoria de meme por ID", e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("categoria_meme.encontrarPorId.tempo"));
        }
    }
}
