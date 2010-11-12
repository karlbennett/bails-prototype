package org.bails;

import java.util.Map;

/**
 * A stream that is used by bails to pars any given markup document.
 *
 * @author Karl Bennett
 */
public interface IBailsStream {

    public boolean hasNext();

    public void next();

    public void close();

    public boolean isOpenTag();

    public boolean isCloseTag();

    public boolean isOpenCloseTag();

    public boolean isCharSequence();

    public CharSequence getCurrentElement();

    public String getName();

    public Map<String, Object> getAttributes();
}
