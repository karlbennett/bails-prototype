package org.bails;

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

    private Document document;
    private final Map<String, Element> bailsChildren = new HashMap<String, Element>();

    public Page() {
    }

    public Page(Document document) {
        this.document = document;
        this.document.setAncestor(this);
    }

    public final String render() {
        return document.toString();
    }

    public abstract void design();

    public Element getBailsChild(String bailsPath) {
        return bailsChildren.get(bailsPath);
    }

    @Override
    public Element add(Object... childs) {
        for (Object child : childs) {

            if (child instanceof BailsElement) { // If one of the children being added to the page is a BailsElement...

                // If the new bails child has not had it's heritage set then set it for it's self and it's descendants.
                if (((BailsElement) child).getAncestor() != this || ((BailsElement) child).getParent() == null) {
                    // ...get a reference to this page that will be used as the ancestor for this child and any of it's descendants that are also
                    // bails elements.
                    final Page thisPage = this;

                    ((BailsElement) child).setParent(thisPage); // Set this child's parent to be this page.

                    ((BailsElement) child).visitChildren(new IVisitor() { // Visit all of the child's descendants...

                        @Override
                        public void element(IVisitable host) {

                            if (host instanceof BailsElement) { // ...and for any that are BailsElements...

                                BailsElement bailsElement = (BailsElement) host;

                                bailsElement.setAncestor(thisPage); // ...set their ancestor to be this page...

                                bailsChildren.put(((BailsElement) host).getBailsPath(), (Element) host);

                                // ...then lastly for any direct children of the descendants...
                                for (Object child : bailsElement.getChildren()) {
                                    // ...that are also BailsElements set their parent as this descendant.
                                    if (child instanceof BailsElement) ((BailsElement) child).setParent(bailsElement);
                                }
                            }
                        }
                    });
                }
            }
        }

        return super.add(childs);
    }
}
