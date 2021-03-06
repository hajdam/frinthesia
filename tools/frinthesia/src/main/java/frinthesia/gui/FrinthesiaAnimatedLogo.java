/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frinthesia.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 *
 * @version 0.1.0 2015/11/28
 * @author Frinthesia Project
 */
public class FrinthesiaAnimatedLogo extends javax.swing.JPanel {

    private final ImageIcon lightImage = new javax.swing.ImageIcon(getClass().getResource("/frinthesia/resources/animated_logo/frinika_light_gradient.png"));
    private final ImageIcon cloudImage = new javax.swing.ImageIcon(getClass().getResource("/frinthesia/resources/animated_logo/frinika_score.png"));
    private final ImageIcon labelImage = new javax.swing.ImageIcon(getClass().getResource("/frinthesia/resources/animated_logo/frinthesia_alpha.png"));

    private final Point cloud1Position;
    private final Point cloud2Position;
    private final Point lightPosition;
    private BufferedImage animationBuffer;
    private final ImageObserver observer = new ImageObserver() {
        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return true;
        }
    };

    // Cached values
    private int cloudWidth = 0;
    private int labelWidth = 0;
    private int lightWidth = 0;

    public FrinthesiaAnimatedLogo() {
        cloudWidth = cloudImage.getIconWidth();
        labelWidth = labelImage.getIconWidth();
        lightWidth = lightImage.getIconWidth();

        cloud1Position = new Point(0, 40);
        cloud2Position = new Point(cloudWidth, 40);
        lightPosition = new Point(0, 0);

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void invalidate() {
        animationBuffer = null;
        updateBuffer();
        super.invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (animationBuffer != null) {
            g.drawImage(animationBuffer, 0, 0, this);
        }
    }

    protected void updateBuffer() {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            if (animationBuffer == null) {
                animationBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = animationBuffer.createGraphics();

                int panelWidth = width;
                int startPoint = (panelWidth - labelWidth) / 2;
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);
                g.drawImage(labelImage.getImage(), startPoint, 0, observer);
                g.drawImage(cloudImage.getImage(), startPoint + cloud1Position.x, cloud1Position.y, observer);
                g.drawImage(cloudImage.getImage(), startPoint + cloud2Position.x, cloud2Position.y, observer);
                g.drawImage(lightImage.getImage(), startPoint + lightPosition.x, lightPosition.y, observer);
                g.dispose();
            }
        }
    }

    public void animate() {
        cloud1Position.x--;
        if (cloud1Position.x < -cloudWidth) {
            cloud1Position.x = cloudWidth;
        }

        cloud2Position.x--;
        if (cloud2Position.x < -cloudWidth) {
            cloud2Position.x = cloudWidth;
        }

        lightPosition.x += 3;
        if (lightPosition.x > labelWidth) {
            lightPosition.x = -lightWidth;
        }

        invalidate();
        repaint();
    }
}
