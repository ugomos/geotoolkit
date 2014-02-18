/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2012 Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotoolkit.gui.swing.style;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;
import org.geotoolkit.gui.swing.resource.MessageBundle;

/**
 * ColorChooser with alpha
 *
 * @author Fabien Rétif (Geomatys)
 * @author Johann Sorel (Geomatys)
 * @module pending
 */
public class JColorPane extends javax.swing.JPanel {

    public JColorPane() {
        this(null);
    }

    public JColorPane(Color c) {
        initComponents();
        guiOpacity.setMinimum(0);
        guiOpacity.setMaximum(100);
        guiOpacity.setValue(100);
        guiOpacity.setMinorTickSpacing(0);
        guiOpacity.setMajorTickSpacing(100);
        setColor(c);
    }

    public final Color getColor() {
        Color c = this.guiColor.getColor();
        int alpha = (int)((this.guiOpacity.getValue()/100f)*255f);
        if(alpha>255) alpha = 255;
        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
        return c;
    }

    public final void setColor(Color c) {
        if (c == null) {
            c = Color.WHITE;
        }
        this.guiColor.setColor(c);
        this.guiOpacity.setValue((int) ((c.getAlpha() / 255f) * 100f));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        guiColor = new javax.swing.JColorChooser();
        guiOpacity = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        guiOpacity.setMajorTickSpacing(100);
        guiOpacity.setPaintLabels(true);
        guiOpacity.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                JColorPane.this.propertyChange(evt);
            }
        });

        jLabel1.setText(MessageBundle.getString("opacity")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(guiColor, javax.swing.GroupLayout.PREFERRED_SIZE, 565, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guiOpacity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(guiColor, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guiOpacity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_propertyChange
        
    }//GEN-LAST:event_propertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JColorChooser guiColor;
    private javax.swing.JSlider guiOpacity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

    public static Color showDialog(Component component, String title, Color initialColor) {
        final JColorPane pane = new JColorPane(initialColor != null ? initialColor : Color.white);
        JOptionPane.showMessageDialog(component, pane, title, JOptionPane.PLAIN_MESSAGE);
        return pane.getColor();
    }
}