package org.bails.visitor;

/**
 * User: Karl Bennett
 * Date: 19/12/10
 */
public interface IVisitable {

    public void visitChildren(IVisitor visitor);

    public void visitParents(IVisitor visitor);
}
