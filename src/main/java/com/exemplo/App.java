package com.exemplo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class Pessoa {
    private String nome;
    private int idade;
    private String cidade;

    public Pessoa(String nome, int idade, String cidade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome + " (" + idade + " anos) - " + cidade;
    }
}

public class App {
    private static final String ARQUIVO_JSON = "pessoas.json";
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("Maria", 25, "São Paulo"));
        pessoas.add(new Pessoa("João", 30, "Rio de Janeiro"));
        pessoas.add(new Pessoa("Ana", 22, "Belo Horizonte"));

        try {
            salvarListaEmJson(pessoas, ARQUIVO_JSON);
            List<Pessoa> pessoasLidas = lerListaDoJson(ARQUIVO_JSON);

            System.out.println("Pessoas carregadas do arquivo JSON:");
            for (Pessoa p : pessoasLidas) {
                System.out.println(p);
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    // Salva lista de pessoas em arquivo JSON
    public static void salvarListaEmJson(List<Pessoa> pessoas, String arquivo) throws IOException {
        if (pessoas == null) {
            throw new NullPointerException("Lista de pessoas não pode ser nula");
        }

        try (FileWriter writer = new FileWriter(arquivo)) {
            writer.write(gson.toJson(pessoas));
        }
    }

    // Lê lista de pessoas do arquivo JSON
    public static List<Pessoa> lerListaDoJson(String arquivo) throws IOException {
        try (FileReader reader = new FileReader(arquivo)) {
            if (reader.ready() == false) { // verifica se arquivo está vazio
                throw new IOException("Arquivo vazio");
            }
            Type listaTipo = new TypeToken<ArrayList<Pessoa>>(){}.getType();
            return gson.fromJson(reader, listaTipo);
        }
    }

    // Converte lista de pessoas para JSON
    public static String converterParaJson(List<Pessoa> pessoas) {
        if (pessoas == null) {
            throw new NullPointerException("Lista não pode ser nula");
        }
        for (Pessoa p : pessoas) {
            if (p.getNome() == null) {
                throw new IllegalArgumentException("Pessoa com nome nulo não pode ser convertida");
            }
        }
        return gson.toJson(pessoas);
    }
}
