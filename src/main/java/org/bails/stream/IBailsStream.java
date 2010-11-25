package org.bails.stream;

import java.util.Map;

/**
 * A stream that is used by bails to pars any given markup document.
 *
 * @author Karl Bennett
 */
public interface IBailsStream {

    /**
     * @return true if the stream has more elements to process.
     */
    public boolean hasNext();

    /**
     * Move to the next element in the stream populating all the flags and attributes appropriately.
     */
    public void next();

    /**
     * Close the stream freeing up and related resources.
     */
    public void close();

    public ELEMENT_TYPE getType();

    /**
     * @return the character sequence representation of the current element. This should preserve all surrounding
     * whitespace.
     */
    public CharSequence getCharSequence();

    /**
     * @return the name of the current element if it contains a name other wise null.
     */
    public String getName();

    /**
     * @return the attributes related to the current element if it contains attributes otherwise null.
     */
    public Map<String, Object> getAttributes();

    /**
     * @return the bails id for this element.
     */
    public String getBailsId();
}
