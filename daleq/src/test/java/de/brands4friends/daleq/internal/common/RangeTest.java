package de.brands4friends.daleq.internal.common;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

import de.brands4friends.daleq.internal.common.Range;

/**
 *
 */
public class RangeTest {

    @Test
    public void testRangeWithFromAndTo(){
        List<Integer> actual = Lists.newArrayList(Range.range(0,10));
        List<Integer> expected = Lists.newArrayList(0,1,2,3,4,5,6,7,8,9);
        assertEquals(expected,actual);
    }

    @Test
    public void testRangeWithTo(){
        List<Integer> actual = Lists.newArrayList(Range.range(10));
        List<Integer> expected = Lists.newArrayList(0,1,2,3,4,5,6,7,8,9);
        assertEquals(expected,actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeWithFromAndToWithSameValue(){
        Range.range(0,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeWithToOnZero(){
        Range.range(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeWithFromAndToInverseOrder(){
        Range.range(10,5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeWithToAndNegativeValue(){
        Range.range(-5);
    }
}
