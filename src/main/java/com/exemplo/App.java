package com.exemplo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class Pessoa {
    private String nome;
    private int idade;
    private String cidade;

    public Pessoa(String nome, int idade, String cidade) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
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

        String json = gson.toJson(pessoas);
        System.out.println("Lista de pessoas convertida para JSON:\n" + json);

        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            writer.write(json);
            System.out.println("\nJSON salvo em " + ARQUIVO_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            Type listaTipo = new TypeToken<ArrayList<Pessoa>>(){}.getType();
            List<Pessoa> pessoasLidas = gson.fromJson(reader, listaTipo);

            System.out.println("\nPessoas carregadas do arquivo JSON:");
            for (Pessoa p : pessoasLidas) {
                System.out.println(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
