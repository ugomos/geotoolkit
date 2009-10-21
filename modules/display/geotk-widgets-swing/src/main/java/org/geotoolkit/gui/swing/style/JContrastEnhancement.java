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
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import org.geotoolkit.gui.swing.style.StyleElementEditor;
import org.geotoolkit.map.MapLayer;

import org.opengis.style.ContrastEnhancement;
import org.opengis.style.ContrastMethod;

/**
 * ContrastEnchancement panel
 * 
 * @author  Johann Sorel
 * @module pending
 */
public class JContrastEnhancement extends StyleElementEditor<ContrastEnhancement>{

    private MapLayer layer = null;

    /** 
     * Creates new form JFillPanel 
     */
    public JContrastEnhancement() {
        initComponents();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setLayer(MapLayer layer) {
        this.layer = layer;
        guiGamma.setLayer(layer);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public MapLayer getLayer(){
        return layer;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void parse(ContrastEnhancement enhancement) {
        if (enhancement != null) {
            guiGamma.parse(enhancement.getGammaValue());
            final ContrastMethod method = enhancement.getMethod();
            if(ContrastMethod.HISTOGRAM.equals(method)){
                guiHistogram.setSelected(true);
            }else if(ContrastMethod.NORMALIZE.equals(method)){
                guiNormalize.setSelected(true);
            }else{
                guiNone.setSelected(true);
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ContrastEnhancement create() {
        if(guiHistogram.isSelected()){
            return getStyleFactory().contrastEnhancement(guiGamma.create(),ContrastMethod.HISTOGRAM );
        }else if(guiNormalize.isSelected()){
            return getStyleFactory().contrastEnhancement(guiGamma.create(),ContrastMethod.NORMALIZE);
        }else{
            return getStyleFactory().contrastEnhancement(guiGamma.create(),ContrastMethod.NONE);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        methodGroup = new ButtonGroup();
        jLabel2 = new JLabel();
        guiGamma = new JNumberExpressionPane();
        guiNone = new JRadioButton();
        guiHistogram = new JRadioButton();
        guiNormalize = new JRadioButton();

        setOpaque(false);


        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(MessageBundle.getString("gamma")); // NOI18N
        methodGroup.add(guiNone);

        guiNone.setText(MessageBundle.getString("method_none")); // NOI18N
        methodGroup.add(guiHistogram);

        guiHistogram.setText(MessageBundle.getString("method_histogram")); // NOI18N
        methodGroup.add(guiNormalize);

        guiNormalize.setText(MessageBundle.getString("method_normalize")); // NOI18N
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiGamma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addComponent(guiHistogram)
            .addComponent(guiNone)
            .addComponent(guiNormalize)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {guiHistogram, guiNone, guiNormalize});

        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(guiGamma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiNone)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiHistogram)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiNormalize))
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {guiGamma, jLabel2});

    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JNumberExpressionPane guiGamma;
    private JRadioButton guiHistogram;
    private JRadioButton guiNone;
    private JRadioButton guiNormalize;
    private JLabel jLabel2;
    private ButtonGroup methodGroup;
    // End of variables declaration//GEN-END:variables
}
