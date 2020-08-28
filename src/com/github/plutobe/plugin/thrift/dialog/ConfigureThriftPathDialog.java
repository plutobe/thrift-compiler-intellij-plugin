package com.github.plutobe.plugin.thrift.dialog;

import com.github.plutobe.plugin.thrift.compiler.ThriftCompiler;
import com.github.plutobe.plugin.thrift.constant.Constants;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-25 21:18
 */
public class ConfigureThriftPathDialog extends DialogWrapper {

    public static boolean isShowing = false;

    private EditorTextField thriftPathField;

    public ConfigureThriftPathDialog() {
        super(true);
        setTitle("Please Configure Thrift Path");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        String thriftPath = PropertiesComponent.getInstance().getValue(Constants.THRIFT_PATH_PROPERTY_KEY);
        thriftPathField = new EditorTextField(StringUtils.isNotBlank(thriftPath) ? thriftPath : Constants.THRIFT_DEFAULT_PATH);
        thriftPathField.setPreferredWidth(500);
        panel.add(thriftPathField);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel();
        JButton testButton = new JButton("Test");
        testButton.addActionListener(e -> {
            String thriftPathText = thriftPathField.getText();
            String thriftPath = ThriftCompiler.checkThriftPath(thriftPathText);
            TipsDialog tipsDialog = new TipsDialog();
            if (thriftPath == null) {
                tipsDialog.getLabel().setText("Sorry, the thrift path is not available");
            } else {
                tipsDialog.getLabel().setText("Success");
            }
            tipsDialog.show();
        });
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            String thriftPathText = thriftPathField.getText();
            String thriftPath = ThriftCompiler.checkThriftPath(thriftPathText);
            if (thriftPath == null) {
                TipsDialog tipsDialog = new TipsDialog();
                tipsDialog.getLabel().setText("Sorry, The thrift path is not available");
                tipsDialog.show();
                return;
            }
            PropertiesComponent.getInstance().setValue(Constants.THRIFT_PATH_PROPERTY_KEY, thriftPathText);
            dispose();
        });
        panel.add(testButton);
        panel.add(confirmButton);
        return panel;
    }

    @Override
    public void show() {
        super.show();
        isShowing = true;
    }

    @Override
    public void dispose() {
        super.dispose();
        isShowing = false;
    }

}
