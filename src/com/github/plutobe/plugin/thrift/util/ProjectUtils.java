package com.github.plutobe.plugin.thrift.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;

import java.awt.*;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-28 00:05
 */
public class ProjectUtils {

    public static Project getCurrentProject() {
        ProjectManager projectManager = ProjectManager.getInstance();
        Project[] openProjects = projectManager.getOpenProjects();
        if (openProjects.length == 0) {
            return projectManager.getDefaultProject();
        } else if (openProjects.length == 1) {
            return openProjects[0];
        }
        try {
            WindowManager wm = WindowManager.getInstance();
            for (Project project : openProjects) {
                Window window = wm.suggestParentWindow(project);
                if (window != null && window.isActive()) {
                    return project;
                }
            }
        } catch (Exception ignored) {
        }
        return projectManager.getDefaultProject();
    }

}
