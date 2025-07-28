package Visao.Components;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class CardIndicador extends JPanel {

    private JLabel lbDescription; 
    private JLabel lbIcon;
    private JLabel lbTitle;
    private JLabel lbValues;
    private Color color1;
    private Color color2;

    private int shadowSize = 5;
    private Color shadowColor = new Color(0, 0, 0, 80); 
    private int cornerRadius = 15; 

    public CardIndicador(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        initComponents();
        setOpaque(false); 
    }

    public void setData(String title, String value) {
        lbTitle.setText(title);
        lbValues.setText(value);

        try {
            double numericValue = Double.parseDouble(value.replace("%", "").replace(",", ".")); 
            if (numericValue > 0) {
                lbValues.setForeground(new Color(110, 200, 110));
            } else if (numericValue < 0) {
                lbValues.setForeground(new Color(255, 100, 100)); 
            } else {
                lbValues.setForeground(new Color(255, 220, 100)); 
            }
        } catch (NumberFormatException e) {
            lbValues.setForeground(new Color(230, 230, 230)); 
        }
    }

    public void setIcon(Icon icon) {
        if (lbIcon == null) {
            lbIcon = new JLabel();
        }
        lbIcon.setIcon(icon);
    }

    private void initComponents() {
        lbTitle = new JLabel();
        lbValues = new JLabel();
        lbIcon = new JLabel(); 


        lbTitle.setFont(new Font("SansSerif", Font.BOLD, 16)); 
        lbTitle.setForeground(new Color(240, 240, 240)); 

        lbValues.setFont(new Font("SansSerif", Font.BOLD, 24));
        lbValues.setForeground(new Color(255, 255, 255));

        lbIcon.setPreferredSize(new Dimension(32, 32)); 

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false); 
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2; 
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.weightx = 0.0; 
        gbc.weighty = 0.0; 
        contentPanel.add(lbIcon, gbc);

        gbc.gridx = 1; 
        gbc.gridy = 0;
        gbc.gridheight = 1; 
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.weightx = 1.0; 
        contentPanel.add(lbTitle, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        contentPanel.add(lbValues, gbc);

        setLayout(new BorderLayout()); 
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create(); 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int width = getWidth() - shadowSize;
        int height = getHeight() - shadowSize;

        g2.setColor(shadowColor);
        g2.fillRoundRect(shadowSize, shadowSize, width, height, cornerRadius, cornerRadius);

        GradientPaint g = new GradientPaint(0, 0, color1, 0, height, color2);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        g2.dispose();

        super.paintComponent(grphcs); 
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension superPref = super.getPreferredSize();
        return new Dimension(superPref.width + shadowSize, superPref.height + shadowSize);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
}