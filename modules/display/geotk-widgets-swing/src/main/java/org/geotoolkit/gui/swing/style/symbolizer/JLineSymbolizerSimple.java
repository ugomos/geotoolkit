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
package org.geotoolkit.gui.swing.style.symbolizer;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.measure.unit.NonSI;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import org.geotoolkit.gui.swing.propertyedit.styleproperty.simple.JStrokeControlPane;
import org.geotoolkit.gui.swing.resource.MessageBundle;
import org.geotoolkit.gui.swing.style.JNumberExpressionPane;
import org.geotoolkit.gui.swing.style.StyleElementEditor;
import org.geotoolkit.map.MapLayer;
import org.geotoolkit.style.StyleConstants;
import org.opengis.style.LineSymbolizer;

/**
 * This class able to display LineSymbolizer tool pane
 *
 * @author Fabien Rétif (Geomatys)
 * @author Johann Sorel (Geomatys)
 */
public class JLineSymbolizerSimple extends StyleElementEditor<LineSymbolizer> {

    private MapLayer layer = null;

    /**
     * Creates new form JLineSymbolizerPanel
     */
    public JLineSymbolizerSimple() {
        super(LineSymbolizer.class);
        initComponents();
        guiDisplacementX.setModel(0d, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1d);
        guiDisplacementX.setExpressionVisible(false);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setLayer(final MapLayer layer) {
        this.layer = layer;
        guiStrokeControlPane.setLayer(layer);
        guiDisplacementX.setLayer(layer);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public MapLayer getLayer() {
        return layer;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void parse(final LineSymbolizer symbol) {

        if (symbol != null) {
            guiStrokeControlPane.parse(symbol.getStroke());
            guiDisplacementX.parse(symbol.getPerpendicularOffset());
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public LineSymbolizer create() {
        return getStyleFactory().lineSymbolizer(
                "lineSymbolizer",
                (String) null,
                StyleConstants.DEFAULT_DESCRIPTION,
                NonSI.PIXEL,
                guiStrokeControlPane.create(),
                guiDisplacementX.create());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new JLabel();
        jLabel5 = new JLabel();
        guiStrokeControlPane = new JStrokeControlPane();
        guiDisplacementX = new JNumberExpressionPane();

        jLabel1.setText(MessageBundle.getString("lineShapeAndColor")); // NOI18N

        jLabel5.setText(MessageBundle.getString("displacementX")); // NOI18N

        guiStrokeControlPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                JLineSymbolizerSimple.this.propertyChange(evt);
            }
        });

        guiDisplacementX.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                JLineSymbolizerSimple.this.propertyChange(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(guiDisplacementX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(guiStrokeControlPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(Alignment.LEADING, false)
                    .addComponent(guiStrokeControlPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(guiDisplacementX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {guiDisplacementX, jLabel5});

    }// </editor-fold>//GEN-END:initComponents

    private void propertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_propertyChange
        if (PROPERTY_TARGET.equalsIgnoreCase(evt.getPropertyName())) {
            firePropertyChange(PROPERTY_TARGET, null, create());
        }
    }//GEN-LAST:event_propertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JNumberExpressionPane guiDisplacementX;
    private JStrokeControlPane guiStrokeControlPane;
    private JLabel jLabel1;
    private JLabel jLabel5;
    // End of variables declaration//GEN-END:variables

}
