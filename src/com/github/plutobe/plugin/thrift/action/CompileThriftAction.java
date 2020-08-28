package com.github.plutobe.plugin.thrift.action;

import com.github.plutobe.plugin.thrift.compiler.ThriftCompiler;
import com.github.plutobe.plugin.thrift.util.VirtualFileUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-23 22:09
 */
public class CompileThriftAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile[] virtualFiles = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        List<VirtualFile> thriftVirtualFileList = new ArrayList<>();
        for (VirtualFile virtualFile : virtualFiles) {
            if (virtualFile.isDirectory()) {
                VfsUtilCore.iterateChildrenRecursively(virtualFile, VirtualFileFilter.ALL, virtualFile1 -> {
                    if (VirtualFileUtils.isThriftFile(virtualFile1)) {
                        thriftVirtualFileList.add(virtualFile1);
                    }
                    return true;
                });
            } else {
                if (VirtualFileUtils.isThriftFile(virtualFile)) {
                    thriftVirtualFileList.add(virtualFile);
                }
            }
        }
        ThriftCompiler.compile(thriftVirtualFileList);
    }

}
