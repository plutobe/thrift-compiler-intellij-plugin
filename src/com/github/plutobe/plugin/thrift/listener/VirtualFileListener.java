package com.github.plutobe.plugin.thrift.listener;

import com.github.plutobe.plugin.thrift.compiler.ThriftCompiler;
import com.github.plutobe.plugin.thrift.constant.CompileActionTypeEnum;
import com.github.plutobe.plugin.thrift.util.VirtualFileUtils;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-23 13:32
 */
public class VirtualFileListener implements BulkFileListener {

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        List<VirtualFile> thriftVirtualFileList = events.stream()
                .filter(event -> event.getFile() != null)
                .filter(event -> event.getFile().exists())
                .filter(event -> VirtualFileUtils.isThriftFile(event.getFile()))
                .filter(event -> {
                    VirtualFile virtualFile = event.getFile();
                    byte[] contentBytes;
                    try {
                        contentBytes = virtualFile.contentsToByteArray();
                    } catch (IOException e) {
                        return false;
                    }
                    String contents = new String(contentBytes, virtualFile.getCharset());
                    return StringUtils.isNotBlank(contents);
                })
                .map(VFileEvent::getFile)
                .collect(Collectors.toList());
        ThriftCompiler.compile(thriftVirtualFileList, CompileActionTypeEnum.FILE_LISTENER);
    }

}
