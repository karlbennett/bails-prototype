package org.bails.render;

/**
 * Interface to denote if a class can be rendered.
 *
 * @author Karl Bennett
 */
public interface Renderable {

    /**
     * Render the object.
     */
    public void render() throws Exception;
}
