package org.bails.visitor;

import org.bails.Element;

/**
 * User: Karl Bennett
 * Date: 18/12/10
 */
public interface IVisitor {

    public void element(IVisitable host);
}
