package org.bails.util;

import org.bails.Element;
import org.bails.visitor.IVisitable;
import org.bails.visitor.IVisitor;

/**
 * User: Karl Bennett
 * Date: 19/12/10
 */
public class VisitorUtil {

    private VisitorUtil() {}

    public static <E extends Element & IVisitable> void visitChildren(E host, IVisitor visitor) {
        visitor.element(host);

        for (Object child : host.getChildren()) {
            if (child instanceof IVisitable) ((IVisitable) child).visitChildren(visitor);
        }
    }

    public static <E extends Element & IVisitable> void visitParents(E host, IVisitor visitor) {
        visitor.element(host);

        Element parent = host.getParent();

        if (parent != null && parent instanceof IVisitable) {
            ((IVisitable) parent).visitParents(visitor);
        }
    }
}
