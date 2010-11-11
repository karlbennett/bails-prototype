package org.bails.document;

import java.util.List;
import java.util.Map;

/**
 * @author Karl Bennett
 */
public interface BailsDocument {

    public boolean hasNext();

    public String next();

    public boolean isBailsTag();

    public String getBailsId();

    public boolean isOpeningTag();

    public boolean isClosingTag();

    public List<Map<String, String>> getAttrbutes();

    public String getValue();

    @Override
    public String toString();
}
