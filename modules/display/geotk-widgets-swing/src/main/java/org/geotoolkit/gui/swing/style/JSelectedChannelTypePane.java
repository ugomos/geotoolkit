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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import org.geotoolkit.gui.swing.style.StyleElementEditor;
import org.geotoolkit.map.MapLayer;
import org.jdesktop.swingx.JXTitledSeparator;
import org.opengis.style.SelectedChannelType;

/**
 * SelectedChannel type panel
 * 
 * @author  Johann Sorel
 * @module pending
 */
public class JSelectedChannelTypePane extends StyleElementEditor<SelectedChannelType>{

    private MapLayer layer = null;
    private SelectedChannelType channel = null;

    /** 
     * Creates new form JFillPanel 
     */
    public JSelectedChannelTypePane() {
        initComponents();
    }

    @Override
    public void setLayer(MapLayer layer) {
        this.layer = layer;
        guiContrast.setLayer(layer);
    }

    @Override
    public MapLayer getLayer(){
        return layer;
    }
    
    @Override
    public void parse(SelectedChannelType channel) {
        this.channel = channel;
        if (channel != null) {
            guiName.setText(channel.getChannelName());
            guiContrast.parse(channel.getContrastEnhancement());
        }
    }

    @Override
    public SelectedChannelType create() {
        channel = getStyleFactory().selectedChannelType(guiName.getText(), guiContrast.create());
        return channel;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        guiContrast = new JContrastEnhancement();
        jXTitledSeparator1 = new JXTitledSeparator();
        jLabel1 = new JLabel();
        guiName = new JTextField();

        setOpaque(false);

        jXTitledSeparator1.setAlpha(0.5F);
        jXTitledSeparator1.setHorizontalAlignment(0);


        jXTitledSeparator1.setTitle(MessageBundle.getString("contrast")); // NOI18N
        jLabel1.setText(MessageBundle.getString("name")); // NOI18N
        guiName.setOpaque(false);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiName, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
            .addComponent(jXTitledSeparator1, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(guiContrast, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(guiName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jXTitledSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(guiContrast, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JContrastEnhancement guiContrast;
    private JTextField guiName;
    private JLabel jLabel1;
    private JXTitledSeparator jXTitledSeparator1;
    // End of variables declaration//GEN-END:variables
}
