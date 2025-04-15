package Services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsApiService {
    
    private static final String API_KEY = "pub_7779279a2604c144ed0c5cc79677c752392fa";
    private static final String BASE_URL = "https://newsdata.io/api/1/news?";

    public List<ArticleData> fetchTopPsychologyHeadlines() {
        List<ArticleData> articles = new ArrayList<>();
        try {
            URL url = new URL(BASE_URL + "apikey=" + API_KEY + "&q=psicologia&country=br&language=pt&category=health");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            
            JsonArray articlesArray = jsonResponse.has("results") && jsonResponse.get("results").isJsonArray()
                    ? jsonResponse.getAsJsonArray("results")
                    : new JsonArray();

            for (int i = 0; i < articlesArray.size(); i++) {
                JsonObject articleObj = articlesArray.get(i).getAsJsonObject();

                String title = articleObj.has("title") && !articleObj.get("title").isJsonNull()
                        ? articleObj.get("title").getAsString()
                        : "Título não disponível";

                String imageUrl = articleObj.has("image_url") && !articleObj.get("image_url").isJsonNull()
                        ? articleObj.get("image_url").getAsString()
                        : "";

                String newsUrl = articleObj.has("link") && !articleObj.get("link").isJsonNull()
                        ? articleObj.get("link").getAsString()
                        : "#";

                articles.add(new ArticleData(title, imageUrl, newsUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return articles.isEmpty() ? List.of(new ArticleData("Nenhuma notícia encontrada", "", "#")) : articles;
    }
}
