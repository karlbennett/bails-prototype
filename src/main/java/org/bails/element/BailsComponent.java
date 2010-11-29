package org.bails.element;

import org.bails.markup.BailsTagElement;
import org.bails.stream.IBailsStream;

import java.util.Collections;
import java.util.Map;

/**
 * Date: 29/11/10
 *
 * @author Karl Bennett
 */
public class BailsComponent extends BailsTagElement {

    public BailsComponent() {}

    public BailsComponent(BailsTagElement element) {
        super(element.getChildren());
        setOpenTag(element.getOpenTag());
        setName(element.getName());
        setAttributes(element.getAttributes());
        setCloseTag(element.getCloseTag());
        setOpenClose(element.isOpenClose());
    }

    public BailsComponent(String bailsId) {
        setBailsId(bailsId);
    }

    public BailsComponent(String bailsId, Object value) {
        setBailsId(bailsId);
        super.add(value);

        // Lock the children list so that it is now only ever able to contain the given value..
        setChildren(Collections.unmodifiableList(getChildren()));
    }

    /*
        Convenience Methods
     */

    public BailsComponent add(Object... childs) {


        return this;
    }
}
