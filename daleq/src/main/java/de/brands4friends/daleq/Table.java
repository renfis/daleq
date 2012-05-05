package de.brands4friends.daleq;

public interface Table {

    public Table with(Row ... rows);
    public Table withSomeRows(Iterable<Object> substitutes);

}
