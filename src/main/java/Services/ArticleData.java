/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author john
 */
public class ArticleData {

    private final String title;
    private final String imageUrl;
    private final String newsUrl;

    public ArticleData(String title, String imageUrl, String newsUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.newsUrl = newsUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getNewsUrl() {
        return newsUrl;
    }
}
