package org.bails;

import org.bails.markup.BailsTagElement;
import org.bails.markup.CharactersElement;
import org.bails.markup.Document;
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

    public final String render(Document document) {

        final Page thisPage = this;

        return document.toString(new IVisitor<String>() {
            @Override
            public String element(IVisitable host) {
                if (host instanceof CharactersElement) {
                    Element parent = ((CharactersElement) host).getParent();
                    // The parent is a bail tag and it only has this character element as it child then it must be a candidate for replacement.
                    if (parent instanceof BailsTagElement && parent.getChildren().size() == 1) {
                        BailsElement element = thisPage.getBailsChild(((BailsTagElement) parent).getBailsPath());

                        // TODO Create a proper exception for missing bails elements.
                        if (element == null) throw new RuntimeException("No matching BailsElement found for bails tag "
                                + ((BailsTagElement) parent).getBailsId() + ".");
                        else return element.toString();
                    }

                    return host.toString();
                }

                return "";
            }
        });
    }

    public abstract void design();

    public BailsElement getBailsChild(String bailsPath) {
        return bailsChildren.get(bailsPath);
    }

    private Page addBailsChild(BailsElement child) {
        bailsChildren.put(child.getBailsPath(), child);

        return this;
    }

    protected Page addBailsChildren(BailsElement... childs) {
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
        child.visitChildren(new IVisitor<Object>() {
            @Override
            public Object element(IVisitable host) {
                if (host instanceof BailsElement) {
                    ((BailsElement) host).setAncestor(thisPage);
                    thisPage.addBailsChild((BailsElement) host);
                }

                return null;
            }
        });

        child.visitParents(new IVisitor<Object>() {
            @Override
            public Object element(IVisitable host) {
                if (host instanceof BailsElement) {
                    ((BailsElement) host).setAncestor(thisPage);
                }

                return null;
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
