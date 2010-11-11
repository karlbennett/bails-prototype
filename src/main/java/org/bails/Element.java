package org.bails;

import org.bails.document.BailsDocument;
import org.bails.render.Renderable;

import java.util.*;

/**
 * Class that contains all the generic methods for an element.
 *
 * @author Karl Bennett
 */
public abstract class Element<V> implements Iterable<Element>, Renderable {

    private BailsDocument document;

    private String id;

    private V value;

    private Element parent;

    private List<Element> children = new ArrayList<Element>();

    private StringBuilder response;

    public Element(String id) {
        this.id = id;
    }

    public Element(String id, V value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Add a child or children to the element.
     *
     * @param childs;
     * @return this.
     */
    public Element add(Element... childs) {
        if (childs.length == 1) {
            this.children.add(childs[0]); // Add the child to this Element and then...
            childs[0].setParent(this); // Set this Element as the child's parent.
        } else {
            this.children.addAll(Arrays.asList(childs));
            for (Element child : childs) {
                this.children.add(child);
                child.setParent(this);
            }
        }

        return this;
    }

    /**
     * Convenience method to check if this Element contains children.
     *
     * @return
     */
    public boolean hasChildren() {
        return 0 < children.size();
    }

    /**
     * Get the document for the Element.
     *
     * @return the Elements document.
     */
    public BailsDocument getDocument() {
        return document;
    }

    /**
     * Set the document for this Element.
     *
     * @param document the new document for the Element.
     */
    public void setDocument(BailsDocument document) {
        this.document = document;
    }

    /**
     * Get the elements Bails id,
     *
     * @return the bails id of the Element.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the elements Bails id.
     *
     * @param id the Bails id of the Element.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the value for this element.
     *
     * @return the value of the element.
     */
    public V getValue() {
        return value;
    }

    /**
     * Set the value of the Element.
     *
     * @param value the new value for the element.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Get the Elements parent.
     *
     * @return the parent of the Element.
     */
    public Element getParent() {
        return parent;
    }

    /**
     * Set the parent of the Element. This is protected because it should only ever be called from within the add
     * method of the parent.
     *
     * @param parent the Element that is the parent of this Element.
     */
    protected void setParent(Element parent) {
        this.parent = parent;
    }

    /**
     * Get the response string for this Element.
     *
     * @return the responce string asa StringBuilder.
     */
    public StringBuilder getResponse() {
        if (response == null) response = new StringBuilder();

        return response;
    }

    /**
     * Set the response instance for this Element. This should only ever be called within the render method.
     *
     * @param response
     */
    private void setResponse(StringBuilder response) {
        this.response = response;
    }

    @Override
    public void render() throws Exception {
        if (getParent() != null) { // If this Element has a parent then...
            setResponse(getParent().getResponse()); // ...get a reference to the parents response and...
            setDocument(getParent().getDocument()); // ...also get a reference to the parents document.
        }

        StringBuilder response = getResponse();
        BailsDocument document = getDocument();
        Iterator<Element> childIterator = null;

        // If the current line in the document is a bails tag then that means this method has been called recursively.
        // So tag now needs to be processed.
        if (document.isBailsTag()) {
            response.append(document.toString()); // First add the tag back into the response.
            // If this Element does not have any children then it the value within must be replaced.
            if (!hasChildren()) {
                response.append(getValue().toString());
                // Read to the closing tag for this element.
                while (document.hasNext() && !document.isClosingTag()) document.next();
                response.append(document.toString()); // Add the closing tag to the document.

                return; // Then lastly return back to the parent.
            } else { // Otherwise if this Element has children then they need to be processed so get the child iterator.
                childIterator = iterator();
            }
        }

        // Read through the document.
        while (document.hasNext()) {
            document.next();

            if (document.isBailsTag()) { // If a bails tag has been found it must be a child.
                if (hasChildren()) {
                    Element child = childIterator.next();

                    if (child.getId().equals(document.getBailsId())) {

                    } else {
                        throw new Exception();
                    }

                } else {
                    throw new Exception("Document syntax error: Child tags found for " + getId()
                            + " where none have been added to the class.");
                }
            } else {
                response.append(document.toString());
            }

        }
    }

    /*
    * Methods stolen from the java.util.List interface.
    */

    public int size() {
        return children.size();
    }

    public boolean isEmpty() {
        return children.isEmpty();
    }

    public boolean contains(Object o) {
        return children.contains(o);
    }

    @Override
    public Iterator<Element> iterator() {
        return children.iterator();
    }

    public Object[] toArray() {
        return children.toArray();
    }

    public <Element> Element[] toArray(Element[] ts) {
        return children.toArray(ts);
    }

    public boolean remove(Object o) {
        return children.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return children.containsAll(objects);
    }

    public boolean addAll(Collection<? extends Element> elements) {
        return children.addAll(elements);
    }

    public boolean addAll(int i, Collection<? extends Element> elements) {
        return children.addAll(i, elements);
    }

    public boolean removeAll(Collection<?> objects) {
        return children.removeAll(objects);
    }

    public boolean retainAll(Collection<?> objects) {
        return children.retainAll(objects);
    }

    public void clear() {
        children.clear();
    }

    public Element get(int i) {
        return children.get(i);
    }

    public Element set(int i, Element element) {
        return children.set(i, element);
    }

    public Element remove(int i) {
        return children.remove(i);
    }

    public int indexOf(Object o) {
        return children.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return children.lastIndexOf(o);
    }

    public ListIterator<Element> listIterator() {
        return children.listIterator();
    }

    public ListIterator<Element> listIterator(int i) {
        return children.listIterator(i);
    }

    public List<Element> subList(int i, int i1) {
        return children.subList(i, i1);
    }
}
