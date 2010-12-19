package org.bails;

import org.bails.visitor.VisitableElement;

import static org.bails.util.BailsUtil.buildBailsPath;

/**
 * User: Karl Bennett
 * Date: 19/12/10
 */
public class BailsElement extends VisitableElement implements IBailsElement {

    private String bailsId;
    private String bailsPath;

    public BailsElement(String bailsId) {
        super(null);
        this.bailsId = bailsId;
    }

    public BailsElement(String bailsId, Object value) {
        super(null);
        this.bailsId = bailsId;
        add(value);
    }

    public BailsElement(Element parent) {
        super(parent);
    }

    @Override
    public String getBailsId() {
        return bailsId;
    }

    public void setBailsId(String bailsId) {
        this.bailsId = bailsId;
    }

    @Override
    public String getBailsPath() {
        if (bailsPath == null) {
            bailsPath = buildBailsPath(this);
        }

        return bailsPath;
    }

    public void setBailsPath(String bailsPath) {
        this.bailsPath = bailsPath;
    }

    @Override
    public Element add(Object... childs) {

        if (getAncestor() != null) {
            for (Object child : childs) {
                if (child instanceof BailsElement) {
                    ((Page) getAncestor()).add(child);
                }
            }
        }

        return super.add(childs);
    }
}
