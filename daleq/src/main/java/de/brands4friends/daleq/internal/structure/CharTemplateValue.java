package de.brands4friends.daleq.internal.structure;

public class CharTemplateValue implements TemplateValue {
    @Override
    public String render(final long value) {
        final int offset = (byte) value % (122 - 65 + 1);
        char x = (char) (65 + offset);
        return Character.toString(x);
    }
}
