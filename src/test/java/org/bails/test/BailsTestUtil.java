package org.bails.test;

import org.junit.Ignore;

import java.util.*;

/**
 * @author Karl Bennett
 */
@Ignore // Make sure this isn't mistaken for a unit test class.
public final class BailsTestUtil {

    private BailsTestUtil() {
    }

    public static final String TEST_BAILS_ID_ONE = "test_element_one";
    public static final String TEST_BAILS_ID_TWO = "test_element_two";
    public static final String TEST_BAILS_ID_THREE = "test_element_three";

    public static final String XML_LINE_ONE =      "<element xmlns:bails='http://www.bails.org/' bails:id='" + TEST_BAILS_ID_ONE + "'>\n";
    public static final String XML_LINE_TWO =      "    <element one='1' two='2' three='3'>Some text one.</element>\n";
    public static final String XML_LINE_THREE =    "    <element>Some test two.</element>\n";
    public static final String XML_LINE_FOUR =     "    <element bails:id='" + TEST_BAILS_ID_TWO + "'>Some text three.</element>\n";
    public static final String XML_LINE_FIVE =     "    <element></element>\n";
    public static final String XML_LINE_SIX =      "    <element/>\n";
    public static final String XML_LINE_SEVEN =    "    <element><element bails:id='" + TEST_BAILS_ID_THREE + "'>Some text four.</element></element>\n";
    public static final String XML_LINE_EIGHT =    "</element>";

    public static final String XML_DCOUMENT = XML_LINE_ONE + XML_LINE_TWO + XML_LINE_THREE + XML_LINE_FOUR
            + XML_LINE_FIVE + XML_LINE_SIX + XML_LINE_SEVEN + XML_LINE_EIGHT;

    public static final int CHILD_NUM = 6;

    public static final String TEST_OPEN_TAG = "<element>\n";
    public static final String TEST_OPEN_CLOSE_TAG = "<element/>\n";
    public static final String TEST_OPEN_TAG_WITH_ATTRIBUTES = "<element one=\"1\" two=\"2\" three=\"3\">\n";
    public static final String TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES = "<element one=\"1\" two=\"2\" three=\"3\"/>\n";
    public static final String TEST_CHAR_SEQUENCE = "Some text\n";
    public static final String TEST_CLOSE_TAG = "</element>\n";
    public static final String TEST_NAME = "element";

    public static final String ELEMENT_ATTRIBUTE_ONE = "one";
    public static final String ELEMENT_ATTRIBUTE_TWO = "two";
    public static final String ELEMENT_ATTRIBUTE_THREE = "three";
    public static final String ELEMENT_ATTRIBUTE_BAILS_ID = "bails:id";

    public static final Map<String, Object> TEST_ATTRIBUTES;

    static {
        Map<String, Object> attributeMap = new HashMap<String, Object>();
        attributeMap.put(ELEMENT_ATTRIBUTE_ONE, 1);
        attributeMap.put(ELEMENT_ATTRIBUTE_TWO, 2);
        attributeMap.put(ELEMENT_ATTRIBUTE_THREE, 3);

        TEST_ATTRIBUTES = Collections.unmodifiableMap(attributeMap);
    }
}
