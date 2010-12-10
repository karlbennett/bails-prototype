package org.bails.markup;

import org.bails.IBailsEntity;


/**
 * User: Karl Bennett
 * Date: 09/12/10
 */
public class BailsElement extends BailsElementHolder implements IBailsEntity {

    private String bailsId;

    private String bailsPath;

    public BailsElement(String bailsId) {
        this(bailsId, null);
    }

    public BailsElement(String bailsId, Object value) {
        this.bailsId = bailsId;

        this.bailsPath = bailsId;

        if (value != null) add(value);
    }

    /*
       Override methods.
    */

    @Override
    public String getBailsId() {
        return bailsId;
    }

    @Override
    public String getBailsPath() {
        return bailsPath;
    }

    protected void setBailsPath(String bailsPath) {
        this.bailsPath = bailsPath;
    }

    @Override
    public String toString() {
        return "";
    }
}
