package de.brands4friends.daleq.common;

import java.util.Iterator;

/**
 * Range provides Iterables that iterate over a given range from a startValue to an (exclusive) end value.
 * It would allow iterations like
 * <pre>
   for(Integer i : Range.range(0,10)){
            // do whatever u want
   }
   </pre>
 * But also even more awesome usage scenarios are imaginable.
 */
public class Range {


    private static final class RangeIterator implements Iterator<Integer>{

        private int cur;
        private int to;

        private RangeIterator(int from, int to) {

            if(from >= to) throw new IllegalArgumentException("Constraint from < to violated.");

            this.cur = from;
            this.to = to;
        }

        public boolean hasNext() {
            return cur < to;
        }

        public Integer next() {
            return cur++;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Creates an Iterable which contains the values <code>from</code> (inclusive) to <code>to</code> (exclusive).
     *
     * It is required that the constraint <code>from < to</code> is met.
     *
     * @param from the starting value of the range. This value is inclusive.
     * @param to the end value of the range. This value is exclusive.
     *
     * @return An Iterable providing the values of the given range.
     *
     * @throws IllegalArgumentException if the from < to is violated.
     */
    public static Iterable<Integer> range(int from,int to){
        return createRangeIterable(new RangeIterator(from,to));
    }

    /**
     * Creates an Iterable that contains the range from 0 to to.
     * @see #range(int, int)
     */
    public static Iterable<Integer> range(int to){
        return createRangeIterable(new RangeIterator(0,to));
    }

    /**
     * Creates a range with a single item
     */
    public static Iterable<Integer> oneItem(){
        return range(1);
    }

    private static Iterable<Integer> createRangeIterable(final RangeIterator rangeIter){
        return new Iterable<Integer>(){

            public Iterator<Integer> iterator() {
                return rangeIter;
            }
        };
    }

}
