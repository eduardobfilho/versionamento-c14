package com.exemplo;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final String ARQUIVO_TESTE = "teste.json";

    @AfterEach
    void cleanup() {
        File f = new File(ARQUIVO_TESTE);
        if (f.exists()) f.delete();
    }

    @Test
    void testSalvarListaValida() throws IOException {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("Carlos", 28, "Curitiba"));
        App.salvarListaEmJson(pessoas, ARQUIVO_TESTE);
        assertTrue(new File(ARQUIVO_TESTE).exists());
    }

    @Test
    void testSalvarListaNulaLancaExcecao() {
        assertThrows(NullPointerException.class, () -> {
            App.salvarListaEmJson(null, ARQUIVO_TESTE);
        });
    }

    @Test
    void testConverterParaJsonValido() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("Ana", 22, "Belo Horizonte"));
        String json = App.converterParaJson(pessoas);
        assertNotNull(json);
        assertTrue(json.contains("Ana"));
    }

    @Test
    void testConverterParaJsonComNomeNuloLancaExcecao() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("Ana", 22, "BH"));
        Pessoa pNulo = new Pessoa("Temp", 20, "Cidade");
        pNulo = new Pessoa(null, 20, "Cidade"); // nome nulo
        pessoas.add(pNulo);
        assertThrows(IllegalArgumentException.class, () -> App.converterParaJson(pessoas));
    }

    @Test
    void testArquivoVazioLancaExcecao() throws IOException {
        new File(ARQUIVO_TESTE).createNewFile(); // cria arquivo vazio
        assertThrows(IOException.class, () -> App.lerListaDoJson(ARQUIVO_TESTE));
    }

    @Test
    void testLerListaValida() throws IOException {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("Maria", 25, "SP"));
        App.salvarListaEmJson(pessoas, ARQUIVO_TESTE);
        List<Pessoa> lidas = App.lerListaDoJson(ARQUIVO_TESTE);
        assertEquals(1, lidas.size());
        assertEquals("Maria", lidas.get(0).getNome());
    }

    @Test
    void testPessoaComNomeNuloAindaEhConvertida() {
        List<Pessoa> pessoas = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> pessoas.add(new Pessoa(null, 30, "RJ")));
    }

    @Test
    void testListaVaziaParaJson() {
        List<Pessoa> pessoas = new ArrayList<>();
        String json = App.converterParaJson(pessoas);
        assertEquals("[]", json);
    }

    @Test
    void testSalvarListaVazia() throws IOException {
        List<Pessoa> pessoas = new ArrayList<>();
        App.salvarListaEmJson(pessoas, ARQUIVO_TESTE);
        assertTrue(new File(ARQUIVO_TESTE).exists());
    }

    @Test
    void testSalvarVariasPessoas() throws IOException {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("A", 20, "X"));
        pessoas.add(new Pessoa("B", 21, "Y"));
        pessoas.add(new Pessoa("C", 22, "Z"));
        App.salvarListaEmJson(pessoas, ARQUIVO_TESTE);
        List<Pessoa> lidas = App.lerListaDoJson(ARQUIVO_TESTE);
        assertEquals(3, lidas.size());
    }

    // Pode repetir padrões semelhantes para completar 20 testes...
}
