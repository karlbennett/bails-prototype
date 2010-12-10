package org.bails.util;

import org.bails.IBailsEntity;
import org.bails.IElement;

/**
 * User: Karl Bennett
 * Date: 09/12/10
 */
public class BailsUtil {

    private BailsUtil() {
    }

    public static IBailsEntity findBailsParent(IElement element) {
        while (element != null) {
            element = element.getParent();
            if (element instanceof IBailsEntity) return (IBailsEntity) element;
        }

        return null;
    }

    public static String buildBailsPath(IBailsEntity entity) {
        IBailsEntity parent = findBailsParent(entity);
        return parent == null ? entity.getBailsId() : parent.getBailsPath() + ":" + entity.getBailsId();
    }

    public static IBailsEntity findBailsEntity(String bailsPath, IElement element) {
        if (!bailsPath.contains(":")) {
            if (element instanceof IBailsEntity) {
                return isBailsEntityWithId(bailsPath, element) ? (IBailsEntity) element : null;
            } else {
                IBailsEntity foundEntity = null;
                for (Object child : element.getChildren()) {
                    foundEntity = findBailsEntity(bailsPath, (IElement) child);
                    if (null != foundEntity) return foundEntity;
                }

                return null;
            }
        }

        String firstBailsId = bailsPath.split(":")[0];
        String restBailsPath = bailsPath.replaceFirst(".+\\:", "");

        if (element instanceof IBailsEntity) {
            IBailsEntity foundEntity = null;
            if (isBailsEntityWithId(firstBailsId, element)) {
                for (Object child : element.getChildren()) {
                    foundEntity = findBailsEntity(restBailsPath, (IElement) child);
                    if (null != foundEntity) {
                        return foundEntity;
                    }
                }
            }
        } else {
            IBailsEntity foundEntity = null;
            for (Object child : element.getChildren()) {
                foundEntity = findBailsEntity(bailsPath, (IElement) child);
                if (null != foundEntity) return foundEntity;
            }
        }

        return null;
    }

    public static boolean isBailsEntityWithId(String bailsId, Object entity) {
        return entity instanceof IBailsEntity && bailsId.equals(((IBailsEntity) entity).getBailsId());
    }
}
