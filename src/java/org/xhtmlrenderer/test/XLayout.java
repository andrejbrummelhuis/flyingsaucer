/*
 * {{{ header & license
 * Copyright (c) 2004 Joshua Marinacci
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package org.xhtmlrenderer.test;

import org.w3c.dom.Element;
import org.xhtmlrenderer.layout.Context;
import org.xhtmlrenderer.layout.CustomBlockLayout;
import org.xhtmlrenderer.render.Box;
import org.xhtmlrenderer.util.Uu;

import java.awt.Dimension;


/**
 * Description of the Class
 *
 * @author empty
 */
public class XLayout extends CustomBlockLayout {

    /**
     * Description of the Method
     *
     * @param c   PARAM
     * @param box PARAM
     */
    public void paintComponent(Context c, Box box) {
        Dimension dim = box.getInternalDimension();
        Uu.p("dim = " + dim);
        c.getGraphics().drawLine(box.x,
                box.y,
                box.x + (int) dim.getWidth(),
                box.y + (int) dim.getHeight());
        c.getGraphics().drawLine(box.x,
                box.y + (int) dim.getHeight(),
                box.x + (int) dim.getWidth(),
                box.y);
    }

    /**
     * Gets the intrinsicDimensions attribute of the XLayout object
     *
     * @param c    PARAM
     * @param elem PARAM
     * @return The intrinsicDimensions value
     */
    public Dimension getIntrinsicDimensions(Context c, Element elem) {
        return new Dimension(50, 50);
    }

}

/*
 * $Id$
 *
 * $Log$
 * Revision 1.4  2004/12/12 03:33:04  tobega
 * Renamed x and u to avoid confusing IDE. But that got cvs in a twist. See if this does it
 *
 * Revision 1.3  2004/10/23 14:01:42  pdoubleya
 * Re-formatted using JavaStyle tool.
 * Cleaned imports to resolve wildcards except for common packages (java.io, java.util, etc).
 * Added CVS log comments at bottom.
 *
 *
 */

