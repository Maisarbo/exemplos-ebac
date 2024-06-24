package br.com.maisa.meme.controllers;

import br.com.maisa.meme.entidades.Meme;
import br.com.maisa.meme.servicos.ServicoMeme;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/memes")
public class ControllerMeme {

    private static final Logger log = LoggerFactory.getLogger(ControllerMeme.class);

    private final ServicoMeme servicoMeme;
    private final MeterRegistry meterRegistry;

    public ControllerMeme(ServicoMeme servicoMeme, MeterRegistry meterRegistry) {
        this.servicoMeme = servicoMeme;
        this.meterRegistry = meterRegistry;
    }

    @PostMapping
    public Meme criarMeme(@RequestBody Meme meme) {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            log.info("Recebida requisição para criar meme: {}", meme);
            Meme createdMeme = servicoMeme.criarMeme(meme);
            meterRegistry.counter("meme.criar.sucesso").increment();
            return createdMeme;
        } catch (Exception e) {
            meterRegistry.counter("meme.criar.erro").increment();
            log.error("Erro ao processar requisição para criar meme", e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("meme.criar.tempo"));
        }
    }

    @GetMapping
    public Iterable<Meme> encontrarTodos() {
        Timer.Sample sample = Timer.start(meterRegistry);
        log.info("Recebida requisição para encontrar todos os memes");
        try {
            Iterable<Meme> memes = servicoMeme.encontrarTodos();
            meterRegistry.counter("meme.encontrarTodos.sucesso").increment();
            return memes;
        } catch (Exception e) {
            meterRegistry.counter("meme.encontrarTodos.erro").increment();
            log.error("Erro ao processar requisição para encontrar todos os memes", e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("meme.encontrarTodos.tempo"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meme> findById(@PathVariable String id) {
        Timer.Sample sample = Timer.start(meterRegistry);
        log.info("Recebida requisição para encontrar meme por ID: {}", id);
        try {
            Meme meme = servicoMeme.findById(id);
            if (meme != null) {
                meterRegistry.counter("meme.encontrarPorId.sucesso").increment();
                return ResponseEntity.ok(meme);
            } else {
                meterRegistry.counter("meme.encontrarPorId.naoEncontrado").increment();
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            meterRegistry.counter("meme.encontrarPorId.erro").increment();
            log.error("Erro ao processar requisição para encontrar meme por ID", e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("meme.encontrarPorId.tempo"));
        }
    }
}