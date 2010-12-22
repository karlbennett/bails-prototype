package org.bails.visitor;

import org.bails.Element;
import org.bails.util.VisitorUtil;

/**
 * User: Karl Bennett
 * Date: 18/12/10
 */
public abstract class VisitableElement extends Element implements IVisitable {

    public VisitableElement(Element parent) {
        super(parent);
    }

    @Override
    public final <R> void visitChildren(IVisitor<R> visitor) {
        VisitorUtil.visitChildren(this, visitor);
    }

    @Override
    public final <R> void visitParents(IVisitor<R> visitor) {
        VisitorUtil.visitParents(this, visitor);
    }
}
