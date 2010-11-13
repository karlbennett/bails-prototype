package org.bails.element;

import org.bails.markup.MarkupElement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Karl Bennett
 */
public class HTMLElement extends Element {

    private Map<String, Object> attributes = new HashMap<String, Object>();
    private Object value;

    public HTMLElement(String bailsId) {
        super(bailsId);
    }

    public HTMLElement(String bailsId, Object value) {
        super(bailsId);
        this.value = value;
    }

    @Override
    public String render() {
        clearRender();

        appendToRender(getMarkupElement().getOpenTag());

        if (hasChildren()) {
            renderChildren();
        } else {
            appendToRender(value.toString());
        }

        appendToRender(getMarkupElement().getCloseTag());

        return getRender();
    }
}
