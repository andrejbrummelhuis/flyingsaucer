package org.xhtmlrenderer.render;

import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.layout.Context;
import org.xhtmlrenderer.util.Configuration;
import org.xhtmlrenderer.util.XRLog;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;

public class DefaultRenderer implements Renderer {
//    public int contents_height;
/*
     * ========== painting code ==============
     */
    // the core function that implements the recursive layout/paint loop
    // perhaps we should call it something else?
    /**
     * Description of the Method
     *
     * @param c   PARAM
     * @param box PARAM
     */
    public void paint(Context c, Box box) {
        //Uu.p("Layout.paint() " + box);
        //Point old_cursor = new Point(c.getCursor());
        //Rectangle contents = layoutChildren(c,elem);
        //c.cursor = old_cursor;
        XRLog.render(Level.WARNING, "Using default renderer for " + box.getClass().getName());
        if (box.restyle) {
            restyle(c, box);
            box.restyle = false;
        }
        paintBackground(c, box);
        paintComponent(c, box);
        paintChildren(c, box);
        paintBorder(c, box);
        //this.contents_height = box.height;
    }

    /**
     * Description of the Method
     *
     * @param c   PARAM
     * @param box PARAM
     */
    public void paintBackground(Context c, Box box) {
    }

    /**
     * Description of the Method
     *
     * @param c   PARAM
     * @param box PARAM
     */
    public void paintComponent(Context c, Box box) {
    }

    /**
     * Description of the Method
     *
     * @param c   PARAM
     * @param box PARAM
     */
    public void paintBorder(Context c, Box box) {
    }

    /**
     * Description of the Method
     *
     * @param c   PARAM
     * @param box PARAM
     */
    public void paintChildren(Context c, Box box) {
        //Uu.p("Layout.paintChildren(): " + box);
        //Uu.p("child count = " + box.getChildCount());
        for (int i = 0; i < box.getChildCount(); i++) {
            Box child = (Box) box.getChild(i);
            //Uu.p("child = " + child);
            Renderer renderer = null;
            if (child.isAnonymous()) {
                renderer = c.getRenderingContext().getLayoutFactory().getAnonymousRenderer();
            } else {
                if (child.getContent() == null) {
                    XRLog.render(Level.WARNING, "null node of child: " + child + " of type " + child.getClass().getName());
                    renderer = new InlineRenderer();
                } else {
                    //renderer = c.getRenderer(child.getNode());
                    renderer = c.getRenderer(child.getContent().getElement());
                }
            }
            paintChild(c, child, renderer);
        }
    }

    /**
     * Description of the Method
     *
     * @param c      PARAM
     * @param box    PARAM
     * @param layout PARAM
     */
    public void paintChild(Context c, Box box, Renderer layout) {
        if (box.isChildrenExceedBounds()) {
            layout.paint(c, box);
            return;
        }

        if (Configuration.isTrue("xr.renderer.viewport-repaint", false)) {
            if (c.getGraphics().getClip() != null) {
                Rectangle2D oldclip = (Rectangle2D) c.getGraphics().getClip();
                Rectangle2D box_rect = new Rectangle(box.x, box.y, box.width, box.height);
                if (oldclip.intersects(box_rect)) {
                    layout.paint(c, box);
                }
                return;
            }
        }


        layout.paint(c, box);
    }

    public void restyle(Context ctx, Box box) {
        CalculatedStyle style = ctx.getCurrentStyle();
        box.color = style.getColor();
        box.border_color = style.getBorderColor();
        box.border_style = style.getStringProperty("border-top-style");
        box.background_color = style.getBackgroundColor();
        restyleChildren(ctx, box);
    }

    private void restyleChildren(Context ctx, Box box) {
        for (int i = 0; i < box.getChildCount(); i++) {
            Box child = box.getChild(i);
            child.restyle = true;
        }
    }

    //TODO: check the logic here
    public static boolean isBlockLayedOut(Box box) {
        if (box.getChildCount() == 0) return false;//have to return something, it shouldn't matter
        for (int i = 0; i < box.getChildCount(); i++) {
            Box child = box.getChild(i);
            if (child instanceof LineBox) return false;
            if (child instanceof InlineBox) return false;
            if (child instanceof InlineBlockBox) return false;
        }
        return true;
    }
}

