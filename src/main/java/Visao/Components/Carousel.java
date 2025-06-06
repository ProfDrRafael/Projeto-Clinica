package Visao.Components;

import Services.ArticleData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class Carousel extends JPanel {

    private CardLayout cardLayout;
    private JPanel newsPanel;
    private Timer timer;

    public Carousel(List<ArticleData> newsArticles) {
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        newsPanel = new JPanel(cardLayout);

        for (ArticleData article : newsArticles) {
            JPanel panel = new JPanel(new BorderLayout());

            JLabel titleLabel = new JLabel(
                    "<html><a href='#' style='text-align:center; font-size:14px; color:blue; text-decoration:underline;'>" 
                    + article.getTitle() + "</a></html>",
                    SwingConstants.CENTER
            );
            titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            titleLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(article.getNewsUrl()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            panel.add(titleLabel, BorderLayout.NORTH);

            if (!article.getImageUrl().isEmpty()) {
                try {
                    URL imageUrl = new URL(article.getImageUrl());
                    Image image = ImageIO.read(imageUrl).getScaledInstance(500, 250, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(image));
                    panel.add(imageLabel, BorderLayout.CENTER);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar imagem: " + e.getMessage());
                }
            }

            newsPanel.add(panel);
        }

        JButton prevButton = new JButton("Anterior");
        JButton nextButton = new JButton("Próximo");

        prevButton.addActionListener(e -> cardLayout.previous(newsPanel));
        nextButton.addActionListener(e -> cardLayout.next(newsPanel));

        timer = new Timer(3000, e -> cardLayout.next(newsPanel));
        timer.start();

        newsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                timer.stop();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                timer.start();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        add(newsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
