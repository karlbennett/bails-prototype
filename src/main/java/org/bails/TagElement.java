package org.bails;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Karl Bennett
 */
public class TagElement extends Element {

    private Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * Add an attribute to the Element.
     *
     * @param name the name of the attribute.
     * @param value the value of the attribute.
     * @return the previous value if the attribute already exists otherwise null.
     */
    public Object addAttribute(String name, Object value) {

        return this.attributes.put(name, value);
    }

    /**
     * Get an attribute from the Element.
     *
     * @param name the name of an attribute.
     * @return the value of that attribute if it exists otherwise null.
     */
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    /**
     * Get all the attributes contained within the Element.
     *
     * @return the map of attributes contain within this Element.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Merge the attributes retrieved from the related document element (tag) and merge them with the Elements
     * attributes.
     * Note: Any matching attributes already contained within the Element will be retained over the attributes being
     * merged.
     *
     * @param attributes to be merged.
     */
    public void mergeDocumentAttributes(Map<String, Object> attributes) {
        // Create a new map to hold the attributes so that we don't change the state of the map being passed in.
        Map<String, Object> attributesHolder = new HashMap<String, Object>();
        attributesHolder.putAll(attributes); // Populate the holder map with the document tags attributes.
        // Then populate the holder with this Elements attributes, over writing any duplicates.
        attributesHolder.putAll(this.attributes);
        // Set this Elements attributes map to point to the new holder map. We do this so that we don't change the state
        // of any attribute maps that may have been previously requested with the getAttributes method.
        this.attributes = attributesHolder;
    }
}
