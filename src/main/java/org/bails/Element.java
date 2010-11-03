package org.bails;

import java.util.*;

/**
 * Class that contains all the generic methods for an element.
 *
 * @author Karl Bennett
 */
public abstract class Element implements Iterable<Element> {

    private String id;

    private List<Element> children = new ArrayList<Element>();

    /**
     * Add a child element.
     *
     * @param childs;
     * @return this.
     */
    public Element add(Element... childs) {
        if (childs.length == 1) {
            this.children.add(childs[0]);
        } else {
            this.children.addAll(Arrays.asList(childs));
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
     * Get the elements Bails id,
     * @return the bails id of the Element.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the elements Bails id.
     * @param id the Bails id of the Element.
     */
    public void setId(String id) {
        this.id = id;
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
