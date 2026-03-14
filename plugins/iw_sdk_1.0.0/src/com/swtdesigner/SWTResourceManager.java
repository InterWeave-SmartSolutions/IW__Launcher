/*
 * Decompiled with CFR.
 */
package com.swtdesigner;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;

public class SWTResourceManager {
    private static HashMap<RGB, Color> m_ColorMap = new HashMap();
    private static HashMap<String, Image> m_ClassImageMap = new HashMap();
    private static HashMap<Image, HashMap<Image, Image>> m_ImageToDecoratorMap = new HashMap();
    private static final int MISSING_IMAGE_SIZE = 10;
    public static final int TOP_LEFT = 1;
    public static final int TOP_RIGHT = 2;
    public static final int BOTTOM_LEFT = 3;
    public static final int BOTTOM_RIGHT = 4;
    private static HashMap<String, Font> m_FontMap = new HashMap();
    private static HashMap<Font, Font> m_FontToBoldFontMap = new HashMap();
    private static HashMap<Integer, Cursor> m_IdToCursorMap = new HashMap();

    public static void dispose() {
        SWTResourceManager.disposeColors();
        SWTResourceManager.disposeFonts();
        SWTResourceManager.disposeImages();
        SWTResourceManager.disposeCursors();
    }

    public static Color getColor(int systemColorID) {
        Display display = Display.getCurrent();
        return display.getSystemColor(systemColorID);
    }

    public static Color getColor(int r, int g, int b) {
        return SWTResourceManager.getColor(new RGB(r, g, b));
    }

    public static Color getColor(RGB rgb) {
        Color color = m_ColorMap.get(rgb);
        if (color == null) {
            Display display = Display.getCurrent();
            color = new Color((Device)display, rgb);
            m_ColorMap.put(rgb, color);
        }
        return color;
    }

    public static void disposeColors() {
        Iterator<Color> iter = m_ColorMap.values().iterator();
        while (iter.hasNext()) {
            iter.next().dispose();
        }
        m_ColorMap.clear();
    }

    protected static Image getImage(InputStream is) {
        Display display = Display.getCurrent();
        ImageData data = new ImageData(is);
        if (data.transparentPixel > 0) {
            return new Image((Device)display, data, data.getTransparencyMask());
        }
        return new Image((Device)display, data);
    }

    public static Image getImage(String path) {
        return SWTResourceManager.getImage("default", path);
    }

    public static Image getImage(String section, String path) {
        String key = String.valueOf(section) + '|' + SWTResourceManager.class.getName() + '|' + path;
        Image image = m_ClassImageMap.get(key);
        if (image == null) {
            try {
                FileInputStream fis = new FileInputStream(path);
                image = SWTResourceManager.getImage(fis);
                m_ClassImageMap.put(key, image);
                fis.close();
            }
            catch (Exception e) {
                image = SWTResourceManager.getMissingImage();
                m_ClassImageMap.put(key, image);
            }
        }
        return image;
    }

    public static Image getImage(Class clazz, String path) {
        String key = String.valueOf(clazz.getName()) + '|' + path;
        Image image = m_ClassImageMap.get(key);
        if (image == null) {
            try {
                if (path.length() > 0 && path.charAt(0) == '/') {
                    String newPath = path.substring(1, path.length());
                    image = SWTResourceManager.getImage(new BufferedInputStream(clazz.getClassLoader().getResourceAsStream(newPath)));
                } else {
                    image = SWTResourceManager.getImage(clazz.getResourceAsStream(path));
                }
                m_ClassImageMap.put(key, image);
            }
            catch (Exception e) {
                image = SWTResourceManager.getMissingImage();
                m_ClassImageMap.put(key, image);
            }
        }
        return image;
    }

    private static Image getMissingImage() {
        Image image = new Image((Device)Display.getCurrent(), 10, 10);
        GC gc = new GC((Drawable)image);
        gc.setBackground(SWTResourceManager.getColor(3));
        gc.fillRectangle(0, 0, 10, 10);
        gc.dispose();
        return image;
    }

    public static Image decorateImage(Image baseImage, Image decorator) {
        return SWTResourceManager.decorateImage(baseImage, decorator, 4);
    }

    public static Image decorateImage(Image baseImage, Image decorator, int corner) {
        Image result;
        HashMap<Object, Object> decoratedMap = m_ImageToDecoratorMap.get(baseImage);
        if (decoratedMap == null) {
            decoratedMap = new HashMap();
            m_ImageToDecoratorMap.put(baseImage, decoratedMap);
        }
        if ((result = decoratedMap.get(decorator)) == null) {
            Rectangle bid = baseImage.getBounds();
            Rectangle did = decorator.getBounds();
            result = new Image((Device)Display.getCurrent(), bid.width, bid.height);
            GC gc = new GC((Drawable)result);
            gc.drawImage(baseImage, 0, 0);
            if (corner == 1) {
                gc.drawImage(decorator, 0, 0);
            } else if (corner == 2) {
                gc.drawImage(decorator, bid.width - did.width - 1, 0);
            } else if (corner == 3) {
                gc.drawImage(decorator, 0, bid.height - did.height - 1);
            } else if (corner == 4) {
                gc.drawImage(decorator, bid.width - did.width - 1, bid.height - did.height - 1);
            }
            gc.dispose();
            decoratedMap.put(decorator, result);
        }
        return result;
    }

    public static void disposeImages() {
        Iterator<Object> I = m_ClassImageMap.values().iterator();
        while (I.hasNext()) {
            I.next().dispose();
        }
        m_ClassImageMap.clear();
        for (HashMap hashMap : m_ImageToDecoratorMap.values()) {
            for (Image image : hashMap.values()) {
                image.dispose();
            }
        }
    }

    public static void disposeImages(String section) {
        Iterator<String> I = m_ClassImageMap.keySet().iterator();
        while (I.hasNext()) {
            String key = I.next();
            if (!key.startsWith(String.valueOf(section) + '|')) continue;
            Image image = m_ClassImageMap.get(key);
            image.dispose();
            I.remove();
        }
    }

    public static Font getFont(String name, int height, int style) {
        return SWTResourceManager.getFont(name, height, style, false, false);
    }

    public static Font getFont(String name, int size, int style, boolean strikeout, boolean underline) {
        String fontName = String.valueOf(name) + '|' + size + '|' + style + '|' + strikeout + '|' + underline;
        Font font = m_FontMap.get(fontName);
        if (font == null) {
            FontData fontData = new FontData(name, size, style);
            if (strikeout || underline) {
                try {
                    Class<?> logFontClass = Class.forName("org.eclipse.swt.internal.win32.LOGFONT");
                    Object logFont = FontData.class.getField("data").get(fontData);
                    if (logFont != null && logFontClass != null) {
                        if (strikeout) {
                            logFontClass.getField("lfStrikeOut").set(logFont, new Byte(1));
                        }
                        if (underline) {
                            logFontClass.getField("lfUnderline").set(logFont, new Byte(1));
                        }
                    }
                }
                catch (Throwable e) {
                    System.err.println("Unable to set underline or strikeout (probably on a non-Windows platform). " + e);
                }
            }
            font = new Font((Device)Display.getCurrent(), fontData);
            m_FontMap.put(fontName, font);
        }
        return font;
    }

    public static Font getBoldFont(Font baseFont) {
        Font font = m_FontToBoldFontMap.get(baseFont);
        if (font == null) {
            FontData[] fontDatas = baseFont.getFontData();
            FontData data = fontDatas[0];
            font = new Font((Device)Display.getCurrent(), data.getName(), data.getHeight(), 1);
            m_FontToBoldFontMap.put(baseFont, font);
        }
        return font;
    }

    public static void disposeFonts() {
        Iterator<Font> iter = m_FontMap.values().iterator();
        while (iter.hasNext()) {
            iter.next().dispose();
        }
        m_FontMap.clear();
    }

    public static void fixCoolBarSize(CoolBar bar) {
        CoolItem item;
        CoolItem[] items = bar.getItems();
        int i = 0;
        while (i < items.length) {
            item = items[i];
            if (item.getControl() == null) {
                item.setControl((Control)new Canvas((Composite)bar, 0){

                    public Point computeSize(int wHint, int hHint, boolean changed) {
                        return new Point(20, 20);
                    }
                });
            }
            ++i;
        }
        i = 0;
        while (i < items.length) {
            item = items[i];
            Control control = item.getControl();
            control.pack();
            Point size = control.getSize();
            item.setSize(item.computeSize(size.x, size.y));
            ++i;
        }
    }

    public static Cursor getCursor(int id) {
        Integer key = new Integer(id);
        Cursor cursor = m_IdToCursorMap.get(key);
        if (cursor == null) {
            cursor = new Cursor((Device)Display.getDefault(), id);
            m_IdToCursorMap.put(key, cursor);
        }
        return cursor;
    }

    public static void disposeCursors() {
        Iterator<Cursor> iter = m_IdToCursorMap.values().iterator();
        while (iter.hasNext()) {
            iter.next().dispose();
        }
        m_IdToCursorMap.clear();
    }
}

