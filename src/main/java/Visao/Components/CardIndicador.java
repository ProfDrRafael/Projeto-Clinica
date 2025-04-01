package Visao.Components;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author john
 */
public class CardIndicador extends JPanel{
    private JLabel lbDescription;
    private JLabel lbIcon;
    private JLabel lbTitle;
    private JLabel lbValues;
    private Color color1;
    private Color color2;

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
            double numericValue = Double.parseDouble(value);
            if (numericValue > 0) {
                lbValues.setForeground(Color.GREEN);
            } else if (numericValue < 0) {
                lbValues.setForeground(Color.RED);
            } else {
                lbValues.setForeground(Color.YELLOW);
            }
        } catch (NumberFormatException e) {
            lbValues.setForeground(Color.WHITE);
        }
    }

    private void initComponents() {
        lbTitle = new JLabel();
        lbValues = new JLabel();

        lbTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lbTitle.setForeground(Color.WHITE);

        lbValues.setFont(new Font("Arial", Font.PLAIN, 18));
        lbValues.setForeground(Color.WHITE);
        
        setBorder(new EmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout(10, 10));
        add(lbTitle, BorderLayout.NORTH);
        add(lbValues, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        
        super.paintComponent(grphcs);
    }

}
