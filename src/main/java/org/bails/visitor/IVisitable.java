package org.bails.visitor;

/**
 * User: Karl Bennett
 * Date: 19/12/10
 */
public interface IVisitable {

    public <R> void visitChildren(IVisitor<R> visitor);

    public <R> void visitParents(IVisitor<R> visitor);
}
