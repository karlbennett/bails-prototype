package org.bails.util;

import org.bails.IBailsElement;
import org.bails.visitor.IVisitable;
import org.bails.visitor.IVisitor;

/**
 * User: Karl Bennett
 * Date: 09/12/10
 */
public class BailsUtil {

    private BailsUtil() {
    }

    public static <E extends IBailsElement & IVisitable> String buildBailsPath(E element) {
        final StringBuilder bailsPath = new StringBuilder();

        element.visitParents(new IVisitor() {
            @Override
            public void element(IVisitable host) {
                if (host instanceof IBailsElement) {
                    bailsPath.insert(0, ((IBailsElement) host).getBailsId());
                    bailsPath.insert(0, ":");
                }
            }
        });

        bailsPath.replace(0, 1, "");

        return bailsPath.toString();
    }
}
