package br.com.alura.literalura.literalura.service;

import br.com.alura.literalura.literalura.model.Livro;
import com.google.gson.*;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GutendexService {
    private final HttpClient client = HttpClient.newHttpClient();

    public List<Livro> buscarLivros(String termo) throws Exception {
        List<Livro> livros = new ArrayList<>();
        String encodedTermo = URLEncoder.encode(termo, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + encodedTermo;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray resultados = root.getAsJsonArray("results");

        for (JsonElement e : resultados) {
            JsonObject obj = e.getAsJsonObject();

            String titulo = obj.get("title").getAsString();
            int downloads = obj.get("download_count").getAsInt();
            String idioma = obj.getAsJsonArray("languages").get(0).getAsString();

            JsonArray autores = obj.getAsJsonArray("authors");
            if (autores.size() > 0) {
                JsonObject autorObj = autores.get(0).getAsJsonObject();
                String autor = autorObj.get("name").getAsString();
                int nascimento = autorObj.get("birth_year").isJsonNull() ? 0 : autorObj.get("birth_year").getAsInt();
                int falecimento = autorObj.get("death_year").isJsonNull() ? 0 : autorObj.get("death_year").getAsInt();

                livros.add(new Livro(titulo, autor, nascimento, falecimento, downloads, idioma));
            }
        }

        return livros;
    }
}