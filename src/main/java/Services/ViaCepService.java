package Services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import Persistencia.Model.EnderecoModelCepApi;
import java.util.logging.Level;
import java.util.logging.Logger;
import raven.toast.Notifications;

public class ViaCepService {

    public EnderecoModelCepApi getEndereco(String cep) throws IOException {
        EnderecoModelCepApi end = null;
        HttpGet request = new HttpGet("https://viacep.com.br/ws/" + cep + "/json/");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Gson gson = new Gson();
                end = gson.fromJson(result, EnderecoModelCepApi.class);
            }
        } catch (IOException ex) {
            // Exceção específica de IO é tratada aqui
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro ao buscar o CEP: " + ex.getMessage());
            Logger.getLogger(ViaCepService.class.getName()).log(Level.SEVERE, null, ex);
            // Re-lança a exceção para que o chamador saiba que ocorreu um erro
            throw ex; // Corrigido para lançar a exceção correta
        } catch (Exception ex) {
            // Captura qualquer outra exceção que possa ocorrer
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Erro inesperado: " + ex.getMessage());
            Logger.getLogger(ViaCepService.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException("Erro inesperado ao buscar o CEP.", ex); // Lança uma nova exceção
        }

        return end;
    }
}
