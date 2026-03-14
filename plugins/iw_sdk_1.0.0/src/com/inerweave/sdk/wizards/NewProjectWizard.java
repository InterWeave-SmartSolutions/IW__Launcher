/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.wizards;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.Designer;
import com.inerweave.sdk.vews.NavigationView;
import com.inerweave.sdk.wizards.pages.NewProjectMainPage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;

public class NewProjectWizard
extends Wizard
implements INewWizard {
    NewProjectMainPage mainPage;
    IWorkbench workbench;
    IStructuredSelection selection;

    public boolean performFinish() {
        if (!this.mainPage.finish()) {
            return false;
        }
        return this.createProject();
    }

    public boolean createProject() {
        String[] xsltFiles;
        final IProject newProjectHandle = this.mainPage.getProjectHandle();
        IPath newPath = null;
        if (!this.mainPage.useDefaults()) {
            newPath = this.mainPage.getLocationPath();
        }
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProjectDescription description = workspace.newProjectDescription(newProjectHandle.getName());
        description.setLocation(newPath);
        WorkspaceModifyOperation op = new WorkspaceModifyOperation(){

            protected void execute(IProgressMonitor monitor) throws CoreException {
                NewProjectWizard.this.createProject(description, newProjectHandle, monitor);
            }
        };
        try {
            this.getContainer().run(true, true, (IRunnableWithProgress)op);
        }
        catch (InterruptedException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)ResourceMessages.NewProject_errorMessage, (String)NLS.bind((String)ResourceMessages.NewProject_internalError, (Object)e.getMessage()));
            return false;
        }
        catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t instanceof CoreException) {
                if (((CoreException)t).getStatus().getCode() == 275) {
                    MessageDialog.openError((Shell)this.getShell(), (String)ResourceMessages.NewProject_errorMessage, (String)NLS.bind((String)ResourceMessages.NewProject_caseVariantExistsError, (Object)newProjectHandle.getName()));
                } else {
                    ErrorDialog.openError((Shell)this.getShell(), (String)ResourceMessages.NewProject_errorMessage, null, (IStatus)((CoreException)t).getStatus());
                }
            } else {
                IDEWorkbenchPlugin.getDefault().getLog().log((IStatus)new Status(4, "org.eclipse.ui.ide", 0, t.toString(), t));
                MessageDialog.openError((Shell)this.getShell(), (String)ResourceMessages.NewProject_errorMessage, (String)NLS.bind((String)ResourceMessages.NewProject_internalError, (Object)t.getMessage()));
            }
            return false;
        }
        try {
            newProjectHandle.open(null);
            IFolder newFolder = newProjectHandle.getFolder(".deployables");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder(".deployables/jar");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder(".deployables/war");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("configuration");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("configuration/im");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("configuration/ts");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("classes");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("xslt");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("xslt/include");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("xslt/Site");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("xslt/Site/include");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("xslt/Site/new");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("xslt/Site/new/include");
            newFolder.create(false, true, null);
            newFolder = newProjectHandle.getFolder("xslt/Site/new/xml");
            newFolder.create(false, true, null);
            try {
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/include/dataconnections.xslt");
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/Site/include/appconstants.xslt");
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/Site/include/globals.xslt");
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/Site/include/sitetran_host.xslt");
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/Site/include/sitetran_ent.xslt");
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/Site/new/transactions.xslt");
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/Site/new/include/soltran_start.dat");
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/Site/new/include/soltran_end.dat");
                ConfigContext.copyFileToProject(newProjectHandle, "xslt/Site/new/xml/transactions.xml");
                ConfigContext.copyFileToProject(newProjectHandle, "configuration/im/config.xml");
                ConfigContext.copyFileToProject(newProjectHandle, "configuration/ts/config.xml");
                ConfigContext.copyFileToProject(newProjectHandle, "configuration/project.properties", "configuration/" + newProjectHandle.getName() + ".properties", false);
            }
            catch (IOException e) {
                MessageDialog.openError((Shell)this.getShell(), (String)"Project Error", (String)("Unable to create Project " + e.toString()));
                return false;
            }
        }
        catch (CoreException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Project Error", (String)("Unable to create Project " + e.toString()));
            return false;
        }
        Designer.setSelectedProject(newProjectHandle);
        this.mainPage.saveDeploymentURL();
        ConfigContext.readIMConfigContext();
        ConfigContext.readTSConfigContext();
        ConfigContext.setProductionPackage(this.mainPage.isBlackBoxSolutionValue());
        String tsUrl = this.mainPage.getTsDeploymentURIValue();
        if (tsUrl.length() > 0) {
            ConfigContext.setPrimaryTransformationServerURL(String.valueOf(tsUrl) + "/iwtransformationserver/scheduledtransform");
            ConfigContext.setPrimaryTransformationServerURLT(String.valueOf(tsUrl) + "/iwtransformationserver/scheduledtransform");
            ConfigContext.setPrimaryTransformationServerURL1(String.valueOf(tsUrl) + "/iwtransformationserver/scheduledtransform");
            ConfigContext.setPrimaryTransformationServerURLT1(String.valueOf(tsUrl) + "/iwtransformationserver/scheduledtransform");
            ConfigContext.setPrimaryTransformationServerURLD(String.valueOf(tsUrl) + "/iwtransformationserver/scheduledtransform");
        }
        ConfigContext.setHosted(this.mainPage.isHostedSolutionValue());
        ConfigContext.writeTSConfigContext();
        ConfigContext.writeIMConfigContext();
        String[] stringArray = xsltFiles = this.mainPage.getTransformerListValue();
        int n = 0;
        int n2 = stringArray.length;
        while (n < n2) {
            String xsltName = stringArray[n];
            String fn = new File(xsltName).getName();
            String fileContext = ConfigContext.readFile(xsltName);
            if (fileContext != null) {
                try {
                    ConfigContext.saveFileInProject(newProjectHandle, "xslt/" + fn, fileContext, false);
                }
                catch (IOException e) {
                    MessageDialog.openError((Shell)this.getShell(), (String)"Project Error", (String)("Unable to store xslt " + fn + " into Project " + e.toString()));
                    return false;
                }
                catch (CoreException e) {
                    MessageDialog.openError((Shell)this.getShell(), (String)"Project Error", (String)("Unable to store xslt " + fn + " into Project " + e.toString()));
                    return false;
                }
            }
            ++n;
        }
        IWorkbenchPage ap = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        NavigationView nv = (NavigationView)ap.findView("iw_sdk.NavigationView");
        if (nv != null) {
            nv.setSelectedObject(null);
            nv.setAndSelectViewer();
        }
        return true;
    }

    void createProject(IProjectDescription description, IProject projectHandle, IProgressMonitor monitor) throws CoreException, OperationCanceledException {
        try {
            monitor.beginTask("", 2000);
            projectHandle.create(description, (IProgressMonitor)new SubProgressMonitor(monitor, 1000));
            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }
            projectHandle.open(128, (IProgressMonitor)new SubProgressMonitor(monitor, 1000));
        }
        finally {
            monitor.done();
        }
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        this.setWindowTitle("New Project");
    }

    public void addPages() {
        try {
            this.mainPage = new NewProjectMainPage("New Project");
            super.addPages();
            this.mainPage.setDescription("Create a new project resource.");
            this.addPage((IWizardPage)this.mainPage);
        }
        catch (RuntimeException e) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Project", (String)("Unable to create wizard main page: " + e.toString()));
        }
    }
}

