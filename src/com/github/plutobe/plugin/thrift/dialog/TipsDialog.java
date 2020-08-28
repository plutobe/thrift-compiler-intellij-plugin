package com.github.plutobe.plugin.thrift.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-25 22:00
 */
public class TipsDialog extends DialogWrapper {

    private JLabel label;

    public JLabel getLabel() {
        return this.label;
    }

    public TipsDialog() {
        super(true);
        setTitle("Tips");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        label = new JLabel();
        panel.add(label);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel();
        JButton confirmButton = new JButton("OK");
        confirmButton.addActionListener(e -> TipsDialog.this.dispose());
        panel.add(confirmButton);
        return panel;
    }

}
