package org.bails;

import org.bails.visitor.IVisitable;
import org.bails.visitor.IVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * User: pyr0
 * Date: 18/12/10
 */
public abstract class Page extends Element {

    private Map<String, BailsElement> bailsChildren = new HashMap<String, BailsElement>();

    public final String render() {
        return toString();
    }

    public abstract void design();

    public BailsElement getBailsChild(String bailsPath) {
        return bailsChildren.get(bailsPath);
    }

    public Page addBailsChild(BailsElement child) {
        bailsChildren.put(child.getBailsPath(), child);

        return this;
    }

    public Page addBailsChildren(BailsElement... childs) {
        for (BailsElement child : childs) {
            populateAncestors(child);
            bailsChildren.put(child.getBailsPath(), child);
        }

        return this;
    }

    public Page add(BailsElement... childs) {
        addBailsChildren(childs);

        return (Page) super.add(childs);
    }


    private void populateAncestors(BailsElement child) {
        final Page thisPage = this;
        child.visitChildren(new IVisitor() {
            @Override
            public void element(IVisitable host) {
                if (host instanceof BailsElement) {
                    ((BailsElement) host).setAncestor(thisPage);
                    thisPage.addBailsChild((BailsElement) host);
                }
            }
        });

        child.visitParents(new IVisitor() {
            @Override
            public void element(IVisitable host) {
                if (host instanceof BailsElement) {
                    ((BailsElement) host).setAncestor(thisPage);
                }
            }
        });
    }

    protected Map<String, BailsElement> getBailsChildren() {
        return bailsChildren;
    }

    protected void setBailsChildren(Map<String, BailsElement> bailsChildren) {
        this.bailsChildren = bailsChildren;
    }
}
