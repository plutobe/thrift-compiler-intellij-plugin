package com.github.plutobe.plugin.thrift.util;

import com.github.plutobe.plugin.thrift.constant.Constants;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileFilter;

import java.util.List;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-23 22:38
 */
public class VirtualFileUtils {

    public static boolean isThriftFile(VirtualFile virtualFile) {
        return virtualFile.getName().endsWith(Constants.THRIFT_SUFFIX);
    }

    /**
     * 递归获取所有虚拟文件
     *
     * 已弃用 官方建议使用 {@link com.intellij.openapi.vfs.VfsUtilCore#iterateChildrenRecursively(VirtualFile, VirtualFileFilter, ContentIterator)}
     *
     * @param virtualFile
     * @param virtualFileResultList
     */
    @Deprecated
    public static void getAllVirtualFile(VirtualFile virtualFile, List<VirtualFile> virtualFileResultList) {
        if (virtualFile.isDirectory()) {
            VirtualFile[] virtualFileChildren = virtualFile.getChildren();
            for (VirtualFile virtualFileChild : virtualFileChildren) {
                getAllVirtualFile(virtualFileChild, virtualFileResultList);
            }
        } else {
            virtualFileResultList.add(virtualFile);
        }
    }

}
