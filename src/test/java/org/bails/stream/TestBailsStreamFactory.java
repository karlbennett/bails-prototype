package org.bails.stream;

import org.junit.Ignore;

import java.util.*;

/**
 * @author Karl Bennett
 */
@Ignore // Make sure this isn't mistaken for a unit test class.
public final class TestBailsStreamFactory {

    private TestBailsStreamFactory() {
    }

    public static enum BAILS_STREAM_SELECTOR {
        CHAR_SEQUENCE_CHILD,
        SINGLE_ELEMENT_CHILD,
        DOUBLE_ELEMENT_CHILD,
        BAILS_ELEMENT_CHILD,
        CHILD_WITH_ELEMENT_CHILD
    }

    public static final String TEST_BAILS_ID = "test_element";

    public static final String XML_LINE_ONE =      "<element xmlns:bails='http://www.bails.org/'>\n";
    public static final String XML_LINE_TWO =      "    <element one='1' two='2' three='3'>Some text one.</element>\n";
    public static final String XML_LINE_THREE =    "    <element>Some test two.</element>\n";
    public static final String XML_LINE_FOUR =     "    <element bails:id='" + TEST_BAILS_ID + "'>Some text three.</element>\n";
    public static final String XML_LINE_FIVE =     "    <element></element>\n";
    public static final String XML_LINE_SIX =      "    <element/>\n";
    public static final String XML_LINE_SEVEN =    "</element>";

    public static final String XML_DCOUMENT = XML_LINE_ONE + XML_LINE_TWO + XML_LINE_THREE + XML_LINE_FOUR
            + XML_LINE_FIVE + XML_LINE_SIX + XML_LINE_SEVEN;

    public static final int CHILD_NUM = 5;
    public static final int BAILS_CHILD_NUM = 1;

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

    public static final Map<String, Object> TEST_ATTRIBUTES_WITH_BAILS_ID;

    static {
        Map<String, Object> attributeMap = new HashMap<String, Object>();
        attributeMap.put(ELEMENT_ATTRIBUTE_ONE, 1);
        attributeMap.put(ELEMENT_ATTRIBUTE_TWO, 2);
        attributeMap.put(ELEMENT_ATTRIBUTE_THREE, 3);
        attributeMap.put(ELEMENT_ATTRIBUTE_BAILS_ID, TEST_BAILS_ID);

        TEST_ATTRIBUTES_WITH_BAILS_ID = Collections.unmodifiableMap(attributeMap);
    }

    private static final List<TestElement> TEST_ELEMENTS_CHAR_SEQUENCE_CHILD;

    static {
        List<TestElement> testElements = new ArrayList<TestElement>();

        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, false, false, true, TEST_CHAR_SEQUENCE, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));

        TEST_ELEMENTS_CHAR_SEQUENCE_CHILD = Collections.unmodifiableList(testElements);
    }

    private static final List<TestElement> TEST_ELEMENTS_SINGLE_ELEMENT_CHILD;

    static {
        List<TestElement> testElements = new ArrayList<TestElement>();

        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, false, false, true, TEST_CHAR_SEQUENCE, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));

        TEST_ELEMENTS_SINGLE_ELEMENT_CHILD = Collections.unmodifiableList(testElements);
    }

    private static final List<TestElement> TEST_ELEMENTS_DOUBLE_ELEMENT_CHILD;

    static {
        List<TestElement> testElements = new ArrayList<TestElement>();

        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, false, false, true, TEST_CHAR_SEQUENCE, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, false, false, true, TEST_CHAR_SEQUENCE, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));

        TEST_ELEMENTS_DOUBLE_ELEMENT_CHILD = Collections.unmodifiableList(testElements);
    }

    private static final List<TestElement> TEST_ELEMENTS_BAILS_ELEMENT_CHILD;

    static {
        List<TestElement> testElements = new ArrayList<TestElement>();

        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, false, false, true, TEST_CHAR_SEQUENCE, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, false, false, true, TEST_CHAR_SEQUENCE, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME,
                TEST_ATTRIBUTES_WITH_BAILS_ID));
        testElements.add(new TestElement(false, false, false, true, TEST_CHAR_SEQUENCE, TEST_NAME,
                TEST_ATTRIBUTES_WITH_BAILS_ID));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME,
                TEST_ATTRIBUTES_WITH_BAILS_ID));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));

        TEST_ELEMENTS_BAILS_ELEMENT_CHILD = Collections.unmodifiableList(testElements);
    }

    private static final List<TestElement> TEST_ELEMENTS_ELEMENT_CHILD_WITH_CHILD;

    static {
        List<TestElement> testElements = new ArrayList<TestElement>();

        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(true, false, false, false, TEST_OPEN_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, false, false, true, TEST_CHAR_SEQUENCE, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));
        testElements.add(new TestElement(false, true, false, false, TEST_CLOSE_TAG, TEST_NAME, TEST_ATTRIBUTES));

        TEST_ELEMENTS_ELEMENT_CHILD_WITH_CHILD = Collections.unmodifiableList(testElements);
    }

    public static IBailsStream getNewBailsStream(BAILS_STREAM_SELECTOR stream_SELECTOR_selector) {
        IBailsStream stream = null;

        switch (stream_SELECTOR_selector) {
            case CHAR_SEQUENCE_CHILD:
                stream = new TestBailsStream(TEST_ELEMENTS_CHAR_SEQUENCE_CHILD);
                break;
            case SINGLE_ELEMENT_CHILD:
                stream = new TestBailsStream(TEST_ELEMENTS_SINGLE_ELEMENT_CHILD);
                break;
            case DOUBLE_ELEMENT_CHILD:
                stream = new TestBailsStream(TEST_ELEMENTS_DOUBLE_ELEMENT_CHILD);
                break;
            case BAILS_ELEMENT_CHILD:
                stream = new TestBailsStream(TEST_ELEMENTS_BAILS_ELEMENT_CHILD);
                break;
            case CHILD_WITH_ELEMENT_CHILD:
                stream = new TestBailsStream(TEST_ELEMENTS_ELEMENT_CHILD_WITH_CHILD);
                break;
            default:
                stream = null;
        }

        return stream;
    }

    private static class TestBailsStream implements IBailsStream {

        private Iterator<TestElement> elementIterator;

        private TestElement currentElement;

        public TestBailsStream(List<TestElement> elements) {
            elementIterator = elements.iterator();
            currentElement = elementIterator.next();
        }

        @Override
        public boolean hasNext() {
            return elementIterator.hasNext();
        }

        @Override
        public void next() {
            currentElement = elementIterator.next();
        }

        @Override
        public void close() {

        }

        @Override
        public boolean isOpenTag() {
            return currentElement.isOpenTag();
        }

        @Override
        public boolean isCloseTag() {
            return currentElement.isCloseTag();
        }

        @Override
        public boolean isOpenCloseTag() {
            return currentElement.isOpenCloseTag();
        }

        @Override
        public boolean isCharacters() {
            return currentElement.isCharSequence();
        }

        @Override
        public CharSequence getCharSequence() {
            return currentElement.getElementChars();
        }

        @Override
        public String getName() {
            return currentElement.getName();
        }

        @Override
        public Map<String, Object> getAttributes() {
            return currentElement.getAttributes();
        }


    }

    private static class TestElement {
        private boolean openTag = false;
        private boolean closeTag = false;
        private boolean openCloseTag = false;
        private boolean charSequence = false;
        private CharSequence elementChars;
        private String name;
        private Map<String, Object> attributes = new HashMap<String, Object>();

        public TestElement(boolean openTag,
                           boolean closeTag,
                           boolean openCloseTag,
                           boolean charSequence,
                           CharSequence elementChars,
                           String name,
                           Map<String, Object> attributes) {
            this.openTag = openTag;
            this.closeTag = closeTag;
            this.openCloseTag = openCloseTag;
            this.charSequence = charSequence;
            this.elementChars = elementChars;
            this.name = name;
            this.attributes = attributes;
        }

        public boolean isOpenTag() {
            return openTag;
        }

        public boolean isCloseTag() {
            return closeTag;
        }

        public boolean isOpenCloseTag() {
            return openCloseTag;
        }

        public boolean isCharSequence() {
            return charSequence;
        }

        public CharSequence getElementChars() {
            return elementChars;
        }

        public String getName() {
            return name;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestElement that = (TestElement) o;

            return elementChars.equals(that.getElementChars());
        }

        @Override
        public int hashCode() {
            return elementChars.hashCode();
        }
    }
}