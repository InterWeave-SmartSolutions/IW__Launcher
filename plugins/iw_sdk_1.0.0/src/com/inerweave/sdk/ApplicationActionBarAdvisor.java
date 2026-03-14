/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.inerweave.sdk.actions.BuildProjectAction;
import com.inerweave.sdk.actions.CloseProjectAction;
import com.inerweave.sdk.actions.NewWizardAction;
import com.inerweave.sdk.actions.OpenFileAction;
import com.inerweave.sdk.actions.OpenProjectAction;
import com.inerweave.sdk.actions.ProjectPropertiesAction;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor
extends ActionBarAdvisor {
    private ActionFactory.IWorkbenchAction exitAction;
    private ActionFactory.IWorkbenchAction aboutAction;
    private ActionFactory.IWorkbenchAction newWindowAction;
    private NewWizardAction newAction;
    private OpenFileAction openAction;
    private ActionFactory.IWorkbenchAction importAction;
    private ActionFactory.IWorkbenchAction exportAction;
    private ActionFactory.IWorkbenchAction undoAction;
    private ActionFactory.IWorkbenchAction redoAction;
    private ActionFactory.IWorkbenchAction cutAction;
    private ActionFactory.IWorkbenchAction copyAction;
    private ActionFactory.IWorkbenchAction pasteAction;
    private RetargetAction deleteAction;
    private OpenProjectAction openProjectAction;
    private CloseProjectAction closeProjectAction;
    private BuildProjectAction compileXSLTAction;
    private BuildProjectAction buildIMAction;
    private BuildProjectAction buildTSAction;
    private BuildProjectAction buildProjectAction;
    private ActionFactory.IWorkbenchAction openPerspectiveAction;
    private ActionFactory.IWorkbenchAction openViewAction;
    private IContributionItem viewList;
    private IContributionItem perspectiveList;
    private ProjectPropertiesAction projectPropertiesAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
        this.exitAction = ActionFactory.QUIT.create(window);
        this.register((IAction)this.exitAction);
        this.aboutAction = ActionFactory.ABOUT.create(window);
        this.register((IAction)this.aboutAction);
        this.newAction = new NewWizardAction("New", window);
        this.newAction.setToolTipText("New");
        this.register((IAction)this.newAction);
        this.openAction = new OpenFileAction("Open File", window);
        this.register((IAction)this.openAction);
        this.newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
        this.register((IAction)this.newWindowAction);
        this.importAction = ActionFactory.IMPORT.create(window);
        this.register((IAction)this.importAction);
        this.exportAction = ActionFactory.EXPORT.create(window);
        this.register((IAction)this.exportAction);
        this.undoAction = ActionFactory.UNDO.create(window);
        this.register((IAction)this.undoAction);
        this.redoAction = ActionFactory.REDO.create(window);
        this.register((IAction)this.redoAction);
        this.cutAction = ActionFactory.CUT.create(window);
        this.register((IAction)this.cutAction);
        this.copyAction = ActionFactory.COPY.create(window);
        this.register((IAction)this.copyAction);
        this.pasteAction = ActionFactory.PASTE.create(window);
        this.register((IAction)this.pasteAction);
        this.deleteAction = (RetargetAction)ActionFactory.DELETE.create(window);
        this.register((IAction)this.deleteAction);
        this.openPerspectiveAction = ActionFactory.OPEN_PERSPECTIVE_DIALOG.create(window);
        this.register((IAction)this.openPerspectiveAction);
        this.openViewAction = ActionFactory.SHOW_VIEW_MENU.create(window);
        this.register((IAction)this.openViewAction);
        this.projectPropertiesAction = new ProjectPropertiesAction("Project Properties", window);
        this.register((IAction)this.projectPropertiesAction);
        this.openProjectAction = new OpenProjectAction("Open Project", window);
        this.register((IAction)this.openProjectAction);
        this.closeProjectAction = new CloseProjectAction("Close Project", window);
        this.register((IAction)this.closeProjectAction);
        this.compileXSLTAction = new BuildProjectAction("Compile XSLT", window, 1, "iw_sdk.compileXSLT", null);
        this.compileXSLTAction.setToolTipText("Compile XSLT");
        this.register((IAction)this.compileXSLTAction);
        this.buildIMAction = new BuildProjectAction("Build Inegration Manager", window, 4, "iw_sdk.buildIM", "/icons/IM_build.gif");
        this.buildIMAction.setToolTipText("Build Inegration Manager");
        this.register((IAction)this.buildIMAction);
        this.buildTSAction = new BuildProjectAction("Build Transformation Sever", window, 11, "iw_sdk.buildTS", "/icons/TS_build.gif");
        this.buildTSAction.setToolTipText("Build  Transformation Sever");
        this.register((IAction)this.buildTSAction);
        this.buildProjectAction = new BuildProjectAction("Build Project", window, 15, "iw_sdk.buildProject", "/icons/web_library_project_obj.gif");
        this.buildProjectAction.setToolTipText("Build Project");
        this.register((IAction)this.buildProjectAction);
        this.viewList = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
        this.perspectiveList = ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(window);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("&File", "file");
        MenuManager editMenu = new MenuManager("&Edit", "edit");
        MenuManager projectMenu = new MenuManager("&Project", "project");
        MenuManager windowMenu = new MenuManager("&Window", "window");
        MenuManager helpMenu = new MenuManager("&Help", "help");
        MenuManager viewMenu = new MenuManager("Show View");
        MenuManager viewPerspective = new MenuManager("Open Perspective");
        menuBar.add((IContributionItem)fileMenu);
        menuBar.add((IContributionItem)editMenu);
        menuBar.add((IContributionItem)projectMenu);
        menuBar.add((IContributionItem)windowMenu);
        menuBar.add((IContributionItem)new GroupMarker("additions"));
        menuBar.add((IContributionItem)helpMenu);
        fileMenu.add((IAction)this.newAction);
        fileMenu.add((IContributionItem)new Separator());
        fileMenu.add((IAction)this.openAction);
        fileMenu.add((IContributionItem)new Separator());
        fileMenu.add((IAction)this.importAction);
        fileMenu.add((IAction)this.exportAction);
        fileMenu.add((IContributionItem)new Separator());
        fileMenu.add((IAction)this.exitAction);
        editMenu.add((IAction)this.undoAction);
        editMenu.add((IAction)this.redoAction);
        editMenu.add((IContributionItem)new Separator());
        editMenu.add((IAction)this.cutAction);
        editMenu.add((IAction)this.copyAction);
        editMenu.add((IAction)this.pasteAction);
        editMenu.add((IContributionItem)new Separator());
        editMenu.add((IAction)this.deleteAction);
        projectMenu.add((IAction)this.openProjectAction);
        projectMenu.add((IAction)this.closeProjectAction);
        projectMenu.add((IContributionItem)new Separator());
        projectMenu.add((IAction)this.compileXSLTAction);
        projectMenu.add((IAction)this.buildIMAction);
        projectMenu.add((IAction)this.buildTSAction);
        projectMenu.add((IContributionItem)new Separator());
        projectMenu.add((IAction)this.buildProjectAction);
        projectMenu.add((IContributionItem)new Separator());
        projectMenu.add((IAction)this.projectPropertiesAction);
        viewPerspective.add(this.perspectiveList);
        windowMenu.add((IContributionItem)viewPerspective);
        viewMenu.add(this.viewList);
        windowMenu.add((IContributionItem)viewMenu);
        helpMenu.add((IAction)this.aboutAction);
    }

    protected void fillCoolBar(ICoolBarManager coolBar) {
        ToolBarManager toolbar = new ToolBarManager(0x820000);
        coolBar.add((IContributionItem)new ToolBarContributionItem((IToolBarManager)toolbar, "main"));
        toolbar.add((IAction)this.newAction);
        toolbar.add((IContributionItem)new Separator());
        toolbar.add((IAction)this.buildProjectAction);
        toolbar.add((IAction)this.buildIMAction);
        toolbar.add((IAction)this.buildTSAction);
    }
}

