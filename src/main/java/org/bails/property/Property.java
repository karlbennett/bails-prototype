package org.bails.property;

/**
 * @author Karl Bennett
 */
public class Property {

    private Object value = new Object();

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;

        Property property = (Property) o;

        return value.equals(property.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
