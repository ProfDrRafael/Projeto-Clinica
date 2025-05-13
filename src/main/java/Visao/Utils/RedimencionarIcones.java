package Visao.Utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Utilitário para redimensionar ícones antes de associá‑los a JButton,
 * garantindo boa qualidade de interpolação e opção de tamanho padrão.
 */
public class RedimencionarIcones {

    // Tamanho padrão (em pixels) para ícones, se nenhum outro for passado
    private static final int DEFAULT_SIZE = 92;

    /**
     * Redimensiona o ícone de um botão para o tamanho padrão (92×92).
     *
     * @param botao   o JButton que receberá o ícone
     * @param caminho caminho no classpath para o recurso de imagem (ex: "/icons/meuIcone.png")
     */
    public void redimensionarIcones(JButton botao, String caminho) {
        // delega para o método que recebe tamanho customizável, usando o padrão
        redimensionarIcones(botao, caminho, DEFAULT_SIZE);
    }

    /**
     * Redimensiona o ícone de um botão para um tamanho específico.
     *
     * @param botao   o JButton que receberá o ícone
     * @param caminho caminho no classpath para o recurso de imagem
     * @param tamanho largura e altura desejadas (em pixels)
     */
    public void redimensionarIcones(JButton botao, String caminho, int tamanho) {
        try {
            // 1) Carrega o ImageIcon diretamente do resource
            ImageIcon iconeOriginal = new ImageIcon(getClass().getResource(caminho));
            // 2) Converte o ImageIcon em BufferedImage para manipulação
            BufferedImage bufferedOriginal = toBufferedImage(iconeOriginal);
            // 3) Aplica redimensionamento de alta qualidade (bicúbico)
            BufferedImage imagemRedimensionada = getScaledInstance(
                bufferedOriginal,
                tamanho, tamanho,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                true  // usa algoritmo progressivo para melhor qualidade
            );
            // 4) Define o ícone redimensionado no botão
            botao.setIcon(new ImageIcon(imagemRedimensionada));
            // 5) Pequeno espaçamento entre ícone e texto
            botao.setIconTextGap(10);
        } catch (Exception e) {
            // Em caso de falha no carregamento ou desenho, imprime stack trace
            e.printStackTrace();
        }
    }

    /**
     * Converte um ImageIcon em BufferedImage, preservando canal alfa (transparência).
     *
     * @param icon o ImageIcon original
     * @return BufferedImage equivalente ao ImageIcon
     */
    private BufferedImage toBufferedImage(ImageIcon icon) {
        int width  = icon.getIconWidth();
        int height = icon.getIconHeight();
        // Cria uma imagem vazia com transparência (ARGB)
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        // Desenha o conteúdo do ImageIcon dentro do BufferedImage
        g2d.drawImage(icon.getImage(), 0, 0, null);
        g2d.dispose();  // libera recursos
        return bufferedImage;
    }

    /**
     * Redimensiona uma BufferedImage para as dimensões alvo,
     * opcionalmente em múltiplas etapas para qualidade superior.
     *
     * @param img           a imagem original
     * @param targetWidth   largura desejada
     * @param targetHeight  altura desejada
     * @param hint          tipo de interpolação (ex: RenderingHints.VALUE_INTERPOLATION_BICUBIC)
     * @param higherQuality se true, faz down‑sampling em passos (metade do tamanho) até chegar ao alvo
     * @return imagem redimensionada
     */
    private BufferedImage getScaledInstance(
            BufferedImage img,
            int targetWidth,
            int targetHeight,
            Object hint,
            boolean higherQuality
    ) {
        int width  = img.getWidth();
        int height = img.getHeight();
        BufferedImage scaled = img;

        if (higherQuality) {
            // Enquanto ainda for maior que o alvo, reduz pela metade em cada passo
            while (width > targetWidth || height > targetHeight) {
                // calcula próximo passo, nunca ficando abaixo do tamanho alvo
                width  = Math.max(targetWidth, width  / 2);
                height = Math.max(targetHeight, height / 2);

                // cria imagem temporária intermediária
                BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = tmp.createGraphics();
                // configura dicas de renderização para máxima qualidade
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                // desenha a versão anterior na nova dimensão
                g2.drawImage(scaled, 0, 0, width, height, null);
                g2.dispose();

                // define a temporária como base para o próximo passo
                scaled = tmp;
            }
        } else {
            // Redimensionamento direto em um único passo (mais rápido, menor qualidade)
            BufferedImage tmp = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(img, 0, 0, targetWidth, targetHeight, null);
            g2.dispose();
            scaled = tmp;
        }

        return scaled;
    }
}
