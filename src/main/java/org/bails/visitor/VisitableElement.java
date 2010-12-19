package org.bails.visitor;

import org.bails.Element;
import org.bails.util.VisitorUtil;

/**
 * User: Karl Bennett
 * Date: 18/12/10
 */
public abstract class VisitableElement extends Element implements IVisitable{

    public VisitableElement(Element parent) {
        super(parent);
    }

    @Override
    public final void visitChildren(IVisitor visitor) {
        VisitorUtil.visitChildren(this, visitor);
    }

    @Override
    public final void visitParents(IVisitor visitor) {
        VisitorUtil.visitParents(this, visitor);
    }
}
