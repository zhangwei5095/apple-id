package com.appleframework.id;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Generate serial IDs.
 * 
 * <p>
 * Serial IDs are:
 * </p>
 * <ul>
 * <li>Grouped into namespaces.</li>
 * <li>Unique within the namespace.</li>
 * <li>Ascending and Serial: {@code next_id = previous_id + 1}.</li>
 * </ul>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public abstract class SerialIdGenerator {

    protected static Cache<String, SerialIdGenerator> idGenerators = CacheBuilder.newBuilder()
            .expireAfterAccess(3600, TimeUnit.SECONDS)
            .removalListener(new RemovalListener<String, SerialIdGenerator>() {
                @Override
                public void onRemoval(RemovalNotification<String, SerialIdGenerator> entry) {
                    entry.getValue().destroy();
                }
            }).build();

    /**
     * Invalidates all cached {@link SerialIdGenerator}.
     * 
     * @since 0.4.0
     */
    public static void invalidate() {
        idGenerators.invalidateAll();
    }

    public SerialIdGenerator init() {
        return this;
    }

    public void destroy() {
        // EMPTY
    }

    /**
     * Generates next id.
     * 
     * @return next id for the specified namespace as a long, {@code 0} if not
     *         supported or invalid namespace, negative value if error.
     */
    public abstract long nextId(final String namespace);

    /**
     * Gets current id.
     * 
     * @param namespace
     * @return current id for the specified namespace as long, negative value if
     *         error.
     * @since 0.2.0
     */
    public abstract long currentId(final String namespace);

    /**
     * Sets a value.
     * 
     * @param namespace
     * @param value
     * @return
     * @since 0.4.0
     */
    public abstract boolean setValue(final String namespace, final long value);
}
