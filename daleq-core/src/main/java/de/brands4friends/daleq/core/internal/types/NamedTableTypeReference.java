package de.brands4friends.daleq.core.internal.types;

import com.google.common.base.Objects;
import de.brands4friends.daleq.core.TableTypeReference;

/**
 *
 */
public final class NamedTableTypeReference implements TableTypeReference {

    private final String name;

    public NamedTableTypeReference(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof NamedTableTypeReference) {
            final NamedTableTypeReference that = (NamedTableTypeReference) obj;

            return Objects.equal(name, that.name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
