package com.anas.intellij.plugins.ayah.settings.userinterface;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */
public class PanelBuilder {
    private final JPanel panel;

    public PanelBuilder() {
        panel = new JPanel();
    }

    public PanelBuilder setBorder(final Border border) {
        panel.setBorder(border);
        return this;
    }

    public PanelBuilder setLayout(final LayoutManager layout) {
        panel.setLayout(layout);
        return this;
    }

    public PanelBuilder addComponent(final Component component) {
        panel.add(component);
        return this;
    }

    public PanelBuilder addComponent(final JComponent component, final Object constraints) {
        panel.add(component, constraints);
        return this;
    }

    public JPanel build() {
        return panel;
    }
}
