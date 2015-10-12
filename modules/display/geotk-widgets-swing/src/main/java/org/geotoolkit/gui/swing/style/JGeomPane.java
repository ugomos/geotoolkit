/*
 *    Geotoolkit - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2008 - 2014, Johann Sorel
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

import java.awt.Component;
import org.geotoolkit.gui.swing.resource.MessageBundle;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import org.geotoolkit.map.MapLayer;
import org.opengis.filter.expression.Expression;

/**
 * Geometry panel
 *
 * @author Johann Sorel
 * @module pending
 */
public class JGeomPane extends StyleElementEditor<Expression> {

    /** Creates new form JGeomPane */
    public JGeomPane() {
        super(Expression.class);
        initComponents();
    }

    public void setLayer(final MapLayer layer){
        guiBox.setLayer(layer);
    }

    public MapLayer getLayer(){
        return guiBox.getLayer();
    }

    @Override
    public void parse(Expression target) {
        guiBox.parse(target);
    }

    @Override
    public Expression create() {
        return guiBox.create();
    }
    
    @Override
    protected Object[] getFirstColumnComponents() {
        return new Object[]{guiLabel};
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        guiLabel = new JLabel();
        guiBox = new JTextExpressionPane();

        setOpaque(false);

        guiLabel.setText(MessageBundle.format("geometry")); // NOI18N

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(guiLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addComponent(guiLabel, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
            .addComponent(guiBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {guiBox, guiLabel});

    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JTextExpressionPane guiBox;
    private JLabel guiLabel;
    // End of variables declaration//GEN-END:variables

}
