package de.brands4friends.daleq.internal.structure;

import com.google.common.base.Objects;

public final class ModuloTemplateValue implements TemplateValue {

    private final long modulus;

    public ModuloTemplateValue(final long modulus) {
        this.modulus = modulus;
    }

    @Override
    public String render(final long value) {
        return Long.toString(value % modulus);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ModuloTemplateValue) {
            final ModuloTemplateValue that = (ModuloTemplateValue) obj;

            return Objects.equal(modulus, that.modulus);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(modulus);
    }
}
