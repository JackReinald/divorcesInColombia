package servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.DatosDivorcios;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ConsumoAPI {
    Gson gson = new Gson();
    public List<DatosDivorcios> obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(response.body(), new TypeToken<List<DatosDivorcios>>(){}.getType());
    }
}
