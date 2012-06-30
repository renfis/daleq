/*
 * Copyright 2012 brands4friends, Private Sale GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brands4friends.daleq.internal.builder;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.brands4friends.daleq.FieldData;
import de.brands4friends.daleq.NoSuchDaleqFieldException;
import nl.jqno.equalsverifier.Warning;

public class RowContainerImplTest {

    @Test
    public void testHashcodeAndEquals() {
        forClass(RowContainerImpl.class).suppress(Warning.NULL_FIELDS).verify();
    }

    @Test
    public void getFieldByName_should_getAnExistingField() {
        final String fieldName = "A";
        final FieldData existingField = field(fieldName, "foo");
        final RowContainerImpl row = new RowContainerImpl(fields(existingField));

        assertThat(row.getFieldBy(fieldName), is(existingField));
    }

    @Test(expected = NoSuchDaleqFieldException.class)
    public void getFieldByName_should_throwForNonExistingField() {
        final String fieldName = "A";
        final FieldData existingField = field(fieldName, "foo");
        final RowContainerImpl row = new RowContainerImpl(fields(existingField));

        final FieldData result = row.getFieldBy("B");
        // should have failed yet.
        assertThat(result, is(nullValue()));
    }

    private FieldData field(final String name, final String value) {
        return new FieldContainerImpl(name, value);
    }

    private List<FieldData> fields(final FieldData... fields) {
        return Arrays.asList(fields);
    }
}
