/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.inerweave.sdk.ApplicationWorkbenchWindowAdvisor;
import java.net.URL;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.osgi.framework.Bundle;

public class ApplicationWorkbenchAdvisor
extends WorkbenchAdvisor {
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

    public String getInitialWindowPerspectiveId() {
        return "iw_sdk.config_perspective";
    }

    public void initialize(IWorkbenchConfigurer configurer) {
        configurer.setSaveAndRestore(true);
        this.declareWorkbenchImages();
    }

    private void declareWorkbenchImages() {
        String ICONS_PATH = "$nl$/icons/full/";
        String PATH_ELOCALTOOL = "$nl$/icons/full/elcl16/";
        String PATH_ETOOL = "$nl$/icons/full/etool16/";
        String PATH_DTOOL = "$nl$/icons/full/dtool16/";
        String PATH_OBJECT = "$nl$/icons/full/obj16/";
        String PATH_WIZBAN = "$nl$/icons/full/wizban/";
        Bundle ideBundle = Platform.getBundle((String)"org.eclipse.ui.ide");
        this.declareWorkbenchImage(ideBundle, "IMG_ETOOL_BUILD_EXEC", "$nl$/icons/full/etool16/build_exec.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_ETOOL_BUILD_EXEC_HOVER", "$nl$/icons/full/etool16/build_exec.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_ETOOL_BUILD_EXEC_DISABLED", "$nl$/icons/full/dtool16/build_exec.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_ETOOL_SEARCH_SRC", "$nl$/icons/full/etool16/search_src.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_ETOOL_SEARCH_SRC_HOVER", "$nl$/icons/full/etool16/search_src.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_ETOOL_SEARCH_SRC_DISABLED", "$nl$/icons/full/dtool16/search_src.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_ETOOL_NEXT_NAV", "$nl$/icons/full/etool16/next_nav.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_ETOOL_PREVIOUS_NAV", "$nl$/icons/full/etool16/prev_nav.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_WIZBAN_NEWPRJ_WIZ", "$nl$/icons/full/wizban/newprj_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_WIZBAN_NEWFOLDER_WIZ", "$nl$/icons/full/wizban/newfolder_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_WIZBAN_NEWFILE_WIZ", "$nl$/icons/full/wizban/newfile_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_WIZBAN_IMPORTDIR_WIZ", "$nl$/icons/full/wizban/importdir_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_WIZBAN_IMPORTZIP_WIZ", "$nl$/icons/full/wizban/importzip_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_WIZBAN_EXPORTDIR_WIZ", "$nl$/icons/full/wizban/exportdir_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_WIZBAN_EXPORTZIP_WIZ", "$nl$/icons/full/wizban/exportzip_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_WIZBAN_EXPORTZIP_WIZ", "$nl$/icons/full/wizban/workset_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_DLGBAN_SAVEAS_DLG", "$nl$/icons/full/wizban/saveas_wiz.gif", false);
        this.declareWorkbenchImage(ideBundle, "IMG_OBJ_PROJECT", "$nl$/icons/full/obj16/prj_obj.gif", true);
        this.declareWorkbenchImage(ideBundle, "IMG_OBJ_PROJECT_CLOSED", "$nl$/icons/full/obj16/cprj_obj.gif", true);
        this.declareWorkbenchImage(ideBundle, "IMG_OPEN_MARKER", "$nl$/icons/full/elcl16/gotoobj_tsk.gif", true);
        this.declareWorkbenchImage(ideBundle, "IMG_OBJS_TASK_TSK", "$nl$/icons/full/obj16/taskmrk_tsk.gif", true);
        this.declareWorkbenchImage(ideBundle, "IMG_OBJS_BKMRK_TSK", "$nl$/icons/full/obj16/bkmrk_tsk.gif", true);
        this.declareWorkbenchImage(ideBundle, "IMG_OBJS_COMPLETE_TSK", "$nl$/icons/full/obj16/complete_tsk.gif", true);
        this.declareWorkbenchImage(ideBundle, "IMG_OBJS_INCOMPLETE_TSK", "$nl$/icons/full/obj16/incomplete_tsk.gif", true);
        this.declareWorkbenchImage(ideBundle, "IMG_OBJS_WELCOME_ITEM", "$nl$/icons/full/obj16/welcome_item.gif", true);
        this.declareWorkbenchImage(ideBundle, "IMG_OBJS_WELCOME_BANNER", "$nl$/icons/full/obj16/welcome_banner.gif", true);
    }

    private void declareWorkbenchImage(Bundle ideBundle, String symbolicName, String path, boolean shared) {
        URL url = Platform.find((Bundle)ideBundle, (IPath)new Path(path));
        ImageDescriptor desc = ImageDescriptor.createFromURL((URL)url);
        this.getWorkbenchConfigurer().declareImage(symbolicName, desc, shared);
    }
}

