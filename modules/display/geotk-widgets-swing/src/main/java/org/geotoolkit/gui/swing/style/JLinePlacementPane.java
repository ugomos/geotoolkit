/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2007 - 2008, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2008 - 2009, Johann Sorel
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

import org.geotoolkit.gui.swing.resource.MessageBundle;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import org.geotoolkit.gui.swing.style.StyleElementEditor;
import org.geotoolkit.map.MapLayer;
import org.opengis.style.LinePlacement;

/**
 * Line placement panel
 * 
 * @author Johann Sorel (Puzzle-GIS)
 */
public class JLinePlacementPane extends StyleElementEditor<LinePlacement>{
    
    private MapLayer layer = null;
    private LinePlacement placement = null;
    
    public JLinePlacementPane() {
        initComponents();
        guiOffset.setModel(0d, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1d);
        guiInitial.setModel(0d, 0d, Double.POSITIVE_INFINITY, 1d);
        guiGap.setModel(0d, 0d, Double.POSITIVE_INFINITY, 1d);
    }
    
    @Override
    public void setLayer(MapLayer layer) {
        this.layer = layer;
        guiOffset.setLayer(layer);
        guiInitial.setLayer(layer);
        guiGap.setLayer(layer);
    }

    @Override
    public MapLayer getLayer() {
        return layer;
    }

    @Override
    public void parse(LinePlacement target) {
        placement = target;
        
        if(placement != null){
            guiOffset.parse(placement.getPerpendicularOffset());
            guiInitial.parse(placement.getInitialGap());
            guiGap.parse(placement.getGap());
            guiAligned.setSelected(placement.IsAligned());
            guiGeneralized.setSelected(placement.isGeneralizeLine());
            guiRepetead.setSelected(placement.isRepeated());
        }
        
    }

    @Override
    public LinePlacement create() {
        return getStyleFactory().linePlacement(
                guiOffset.create(),
                guiInitial.create(),
                guiGap.create(),
                guiRepetead.isSelected(),
                guiAligned.isSelected(),
                guiGeneralized.isSelected()
                );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new JLabel();
        guiOffset = new JNumberExpressionPane();
        guiInitial = new JNumberExpressionPane();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        guiGap = new JNumberExpressionPane();
        guiRepetead = new JCheckBox();
        guiAligned = new JCheckBox();
        guiGeneralized = new JCheckBox();

        setOpaque(false);




        jLabel1.setText(MessageBundle.getString("offset2")); // NOI18N
        jLabel2.setText(MessageBundle.getString("initial_gap")); // NOI18N
        jLabel3.setText(MessageBundle.getString("gap")); // NOI18N
        guiRepetead.setText(MessageBundle.getString("repeated")); // NOI18N
        guiRepetead.setOpaque(false);

        guiAligned.setText(MessageBundle.getString("aligned")); // NOI18N
        guiAligned.setOpaque(false);

        guiGeneralized.setText(MessageBundle.getString("generalize")); // NOI18N
        guiGeneralized.setOpaque(false);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiOffset, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiInitial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiGap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addComponent(guiRepetead)
            .addComponent(guiAligned)
            .addComponent(guiGeneralized)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jLabel1, jLabel2, jLabel3});

        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(guiOffset, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(guiInitial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(guiGap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(guiRepetead)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiAligned)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiGeneralized))
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {guiOffset, jLabel1});

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {guiInitial, jLabel2});

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {guiGap, jLabel3});

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JCheckBox guiAligned;
    private JNumberExpressionPane guiGap;
    private JCheckBox guiGeneralized;
    private JNumberExpressionPane guiInitial;
    private JNumberExpressionPane guiOffset;
    private JCheckBox guiRepetead;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
    
}
