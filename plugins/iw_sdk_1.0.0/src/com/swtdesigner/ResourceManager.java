/*
 * Decompiled with CFR.
 */
package com.swtdesigner;

import com.swtdesigner.SWTResourceManager;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ResourceManager
extends SWTResourceManager {
    private static HashMap<ImageDescriptor, Image> m_DescriptorImageMap = new HashMap();
    private static HashMap<URL, Image> m_URLImageMap = new HashMap();

    public static void dispose() {
        ResourceManager.disposeColors();
        ResourceManager.disposeFonts();
        ResourceManager.disposeImages();
        ResourceManager.disposeCursors();
    }

    public static ImageDescriptor getImageDescriptor(Class clazz, String path) {
        return ImageDescriptor.createFromFile((Class)clazz, (String)path);
    }

    public static ImageDescriptor getImageDescriptor(String path) {
        try {
            return ImageDescriptor.createFromURL((URL)new File(path).toURL());
        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    public static Image getImage(ImageDescriptor descriptor) {
        if (descriptor == null) {
            return null;
        }
        Image image = m_DescriptorImageMap.get(descriptor);
        if (image == null) {
            image = descriptor.createImage();
            m_DescriptorImageMap.put(descriptor, image);
        }
        return image;
    }

    public static void disposeImages() {
        SWTResourceManager.disposeImages();
        Iterator<Image> I = m_DescriptorImageMap.values().iterator();
        while (I.hasNext()) {
            I.next().dispose();
        }
        m_DescriptorImageMap.clear();
    }

    public static Image getPluginImage(Object plugin, String name) {
        try {
            try {
                Image image;
                URL url = ResourceManager.getPluginImageURL(plugin, name);
                if (m_URLImageMap.containsKey(url)) {
                    return m_URLImageMap.get(url);
                }
                InputStream is = url.openStream();
                try {
                    image = ResourceManager.getImage(is);
                    m_URLImageMap.put(url, image);
                }
                finally {
                    is.close();
                }
                return image;
            }
            catch (Throwable throwable) {
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return null;
    }

    public static ImageDescriptor getPluginImageDescriptor(Object plugin, String name) {
        try {
            try {
                URL url = ResourceManager.getPluginImageURL(plugin, name);
                return ImageDescriptor.createFromURL((URL)url);
            }
            catch (Throwable throwable) {
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return null;
    }

    private static URL getPluginImageURL(Object plugin, String name) throws Exception {
        try {
            Class<?> bundleClass = Class.forName("org.osgi.framework.Bundle");
            Class<?> bundleContextClass = Class.forName("org.osgi.framework.BundleContext");
            if (bundleContextClass.isAssignableFrom(plugin.getClass())) {
                Method getBundleMethod = bundleContextClass.getMethod("getBundle", new Class[0]);
                Object bundle = getBundleMethod.invoke(plugin, new Object[0]);
                Class<?> ipathClass = Class.forName("org.eclipse.core.runtime.IPath");
                Class<?> pathClass = Class.forName("org.eclipse.core.runtime.Path");
                Constructor<?> pathConstructor = pathClass.getConstructor(String.class);
                Object path = pathConstructor.newInstance(name);
                Class<?> platformClass = Class.forName("org.eclipse.core.runtime.Platform");
                Method findMethod = platformClass.getMethod("find", bundleClass, ipathClass);
                return (URL)findMethod.invoke(null, bundle, path);
            }
        }
        catch (Throwable bundleClass) {
            // empty catch block
        }
        Class<?> pluginClass = Class.forName("org.eclipse.core.runtime.Plugin");
        if (pluginClass.isAssignableFrom(plugin.getClass())) {
            Class<?> ipathClass = Class.forName("org.eclipse.core.runtime.IPath");
            Class<?> pathClass = Class.forName("org.eclipse.core.runtime.Path");
            Constructor<?> pathConstructor = pathClass.getConstructor(String.class);
            Object path = pathConstructor.newInstance(name);
            Method findMethod = pluginClass.getMethod("find", ipathClass);
            return (URL)findMethod.invoke(plugin, path);
        }
        return null;
    }
}

