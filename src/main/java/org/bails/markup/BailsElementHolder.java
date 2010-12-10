package org.bails.markup;

import org.bails.IBailsEntity;
import org.bails.IElement;
import org.bails.stream.IBailsStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bails.util.BailsUtil.buildBailsPath;
import static org.bails.util.BailsUtil.findBailsEntity;

/**
 * User: Karl Bennett
 * Date: 09/12/10
 */
public class BailsElementHolder extends Element {

    private Map<String, IBailsEntity> bailsChildren = new HashMap<String, IBailsEntity>();

    public BailsElementHolder(IBailsStream stream) {
        super(stream);
    }

    public BailsElementHolder(Element... childs) {
        super(childs);
    }

    public BailsElementHolder(List<Element> children) {
        super(children);
    }

    /*
       Convenience methods.
    */

    /**
     * Get a bails child from the Element using the bails path.
     *
     * @param path the path to the BailsTagElement. This is a string of bails ids separated by  colons (:).
     * @return the child at the given path. Or null if the path is incorrect.
     */
    public IBailsEntity getBailsChild(String path) {
        return bailsChildren.get(path);
    }

    /**
     * Add a bails child to this element.
     *
     * @param path       the path of the bails child.
     * @param bailsChild the child bails element.
     * @return this element to allow for chaining.
     */
    public Element addBailsChild(String path, IBailsEntity bailsChild) {
        bailsChildren.put(path, bailsChild);

        return this;
    }

    /*
       Override methods.
    */

    @Override
    protected void addLambda(Object child) {
        if (child instanceof BailsElement) {
            BailsElement element = (BailsElement) child;
            element.setHeritage(this);
            element.setBailsPath(buildBailsPath(element));
            if (element.getAncestor() instanceof BailsElementHolder) {
                if (null != findBailsEntity(element.getBailsPath(), element.getAncestor())) {
                    ((BailsElementHolder) element.getAncestor()).addBailsChild(element.getBailsPath(), element);
                } else {
                    throw new RuntimeException("No matching element found in the markup document for bails element: " +
                    element.getBailsId());
                }
            } else {
                throw new RuntimeException("The Ancestor class for this bails element (" + element.getBailsId()
                        + ") is not a BailsElementHolder");
            }
        }
    }
}
