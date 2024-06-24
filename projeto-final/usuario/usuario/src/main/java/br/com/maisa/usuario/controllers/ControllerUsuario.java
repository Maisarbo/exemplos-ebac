package br.com.maisa.usuario.controllers;

import br.com.maisa.usuario.entidades.Usuario;
import br.com.maisa.usuario.servicos.ServicoUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@RestController
@RequestMapping(path= "/usuarios")
public class ControllerUsuario {
    private static final Logger log = LoggerFactory.getLogger(ControllerUsuario.class);

    private final ServicoUsuario servicoUsuario;
    private final MeterRegistry meterRegistry;

    public ControllerUsuario(ServicoUsuario servicoUsuario, MeterRegistry meterRegistry) {
        this.servicoUsuario = servicoUsuario;
        this.meterRegistry = meterRegistry;
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            log.info("Recebida requisição para criar usuário: {}", usuario);
            Usuario createdUser = servicoUsuario.criarUsuario(usuario);
            meterRegistry.counter("usuario.criar.sucesso").increment();
            return createdUser;
        } catch (Exception e) {
            meterRegistry.counter("usuario.criar.erro").increment();
            log.error("Erro ao processar requisição para criar usuário", e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("usuario.criar.tempo"));
        }
    }

    @GetMapping
    public Iterable<Usuario> encontrarTodos() {
        Timer.Sample sample = Timer.start(meterRegistry);
        log.info("Recebida requisição para encontrar todos os usuários");
        try {
            Iterable<Usuario> users = servicoUsuario.encontrarTodos();
            meterRegistry.counter("usuario.encontrarTodos.sucesso").increment();
            return users;
        } catch (Exception e) {
            meterRegistry.counter("usuario.encontrarTodos.erro").increment();
            log.error("Erro ao processar requisição para encontrar todos os usuários", e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("usuario.encontrarTodos.tempo"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable String id) {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            log.info("Recebida requisição para encontrar usuário com ID: {}", id);
            Usuario usuario = servicoUsuario.findById(id);
            meterRegistry.counter("usuario.findById.sucesso").increment();
            log.info("Enviando resposta com usuário encontrado com ID: {}", id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            meterRegistry.counter("usuario.findById.erro").increment();
            log.error("Erro ao processar requisição para encontrar usuário com ID: {}", id, e);
            throw e; // Lançar a exceção para ser tratada globalmente
        } finally {
            sample.stop(meterRegistry.timer("usuario.findById.tempo"));
        }
    }
}
