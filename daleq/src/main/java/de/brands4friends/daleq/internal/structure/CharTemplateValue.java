package de.brands4friends.daleq.internal.structure;

public class CharTemplateValue implements TemplateValue {
    @Override
    public String render(final long value) {
        final int posz = 'z';
        final int posA = 'A';
        final int offset = (int) value % (posz - posA + 1);
        return Character.toString((char) (posA + offset));
    }
}
