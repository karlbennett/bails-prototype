package org.bails;

import java.util.List;

/**
 * User: Karl Bennett
 * Date: 09/12/10
 */
public interface IElement {

    public IElement getAncestor();

    public IElement getParent();

    public List getChildren();

    public Object getChild(int i);
}
