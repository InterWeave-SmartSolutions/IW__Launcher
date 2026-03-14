/*
 * Decompiled with CFR.
 */
package iw_sdk;

import java.net.URL;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class Iw_sdkPlugin
extends AbstractUIPlugin {
    private static Iw_sdkPlugin plugin;

    public Iw_sdkPlugin() {
        plugin = this;
    }

    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    public static Iw_sdkPlugin getDefault() {
        return plugin;
    }

    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin((String)"iw_sdk", (String)path);
    }

    public static URL fileURLFromPlugin(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException();
        }
        Bundle bundle = Platform.getBundle((String)"iw_sdk");
        URL fullPathURL = Platform.find((Bundle)bundle, (IPath)new Path(filePath));
        return fullPathURL;
    }
}

