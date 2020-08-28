package com.github.plutobe.plugin.thrift.compiler;

import com.github.plutobe.plugin.thrift.constant.Constants;
import com.github.plutobe.plugin.thrift.dialog.ConfigureThriftPathDialog;
import com.github.plutobe.plugin.thrift.notifier.MessageNotifier;
import com.github.plutobe.plugin.thrift.util.CommandExecutor;
import com.github.plutobe.plugin.thrift.util.ProjectUtils;
import com.google.common.util.concurrent.AtomicDouble;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-23 21:12
 */
public class ThriftCompiler {

    public static void compile(List<VirtualFile> virtualFileList) {
        ProgressManager.getInstance().run(new Task.Backgroundable(ProjectUtils.getCurrentProject(), "Thrift compiling") {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                String thriftPathCache = PropertiesComponent.getInstance().getValue(Constants.THRIFT_PATH_PROPERTY_KEY);
                String thriftPath = checkThriftPath(thriftPathCache);
                if (thriftPath == null) {
                    MessageNotifier.notice("thrift path is not configured", MessageType.ERROR);
                    if (ConfigureThriftPathDialog.isShowing) {
                        return;
                    }
                    ConfigureThriftPathDialog configureThriftPathDialog = new ConfigureThriftPathDialog();
                    configureThriftPathDialog.show();
                    return;
                }
                if (CollectionUtils.isEmpty(virtualFileList)) {
                    return;
                }
                AtomicDouble progress = new AtomicDouble();
                Map<String, Integer> compileResultMap = new LinkedHashMap<>();
                List<VirtualFile> thriftVirtualFileList = virtualFileList.parallelStream()
                        .distinct()
                        .filter(thriftVirtualFile -> thriftVirtualFile.getPath().contains(Constants.THRIFT_FILE_PATH))
                        .collect(Collectors.toList());
                thriftVirtualFileList.forEach(thriftVirtualFile -> {
                            String thriftFilePath = thriftVirtualFile.getPath();
                            String thriftFullPath = thriftFilePath.substring(0, thriftFilePath.indexOf(Constants.THRIFT_FILE_PATH) + Constants.THRIFT_FILE_PATH.length());
                            String javaFullPath = thriftFullPath.replace(Constants.THRIFT_STR, Constants.JAVA_STR);
                            File javaFileDir = new File(javaFullPath);
                            if (!javaFileDir.exists()) {
                                boolean mkdir = javaFileDir.mkdir();
                                if (mkdir) {
                                    MessageNotifier.notice("the thrift compilation target directory has been automatically created", MessageType.INFO);
                                }
                            }
                            try {
                                int waitFor = CommandExecutor.execute(String.format(Constants.COMMAND_TEMPLATE, thriftPath, javaFullPath, thriftFilePath));
                                compileResultMap.put(thriftVirtualFile.getName(), waitFor);
                            } catch (Exception e) {
                                compileResultMap.put(thriftVirtualFile.getName(), -1);
                                MessageNotifier.notice("thrift path configuration error", MessageType.ERROR);
                            }
                            progress.addAndGet(1);
                            progressIndicator.setFraction(progress.get() / thriftVirtualFileList.size());
                        });
                List<String> compileSuccessList = new ArrayList<>();
                List<String> compileFailureList = new ArrayList<>();
                compileResultMap.forEach((filename, code) -> {
                    if (code == 0) {
                        compileSuccessList.add(filename);
                    } else {
                        compileFailureList.add(filename);
                    }
                });
                if (compileResultMap.size() == 1) {
                    Map.Entry<String, Integer> entry = compileResultMap.entrySet().iterator().next();
                    if (entry.getValue() == 0) {
                        MessageNotifier.notice( "compile success<br/>" + entry.getKey(), MessageType.INFO);
                    } else {
                        MessageNotifier.notice( "compile failure<br/>" + entry.getKey(), MessageType.ERROR);
                    }
                } else {
                    String message =
                            "compile success " + compileSuccessList.size() + " thrift file(s)" +
                            "<br/>" +
                            "compile failure " + compileFailureList.size() + " thrift file(s)";
                    MessageNotifier.notice(message, MessageType.INFO);
                }
                if (CollectionUtils.isNotEmpty(compileSuccessList)) {
                    VirtualFileManager.getInstance().syncRefresh();
                }
            }
        });
    }

    public static String checkThriftPath(String thriftPath) {
        if (StringUtils.isBlank(thriftPath)) {
            thriftPath = Constants.THRIFT_DEFAULT_PATH;
        }
        try {
            int waitFor = CommandExecutor.execute(thriftPath + " -version");
            if (waitFor == 0) {
                return thriftPath;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
