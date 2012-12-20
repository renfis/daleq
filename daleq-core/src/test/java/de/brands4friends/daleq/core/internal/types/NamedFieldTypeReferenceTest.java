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

package de.brands4friends.daleq.core.internal.types;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.FieldType;
import de.brands4friends.daleq.core.TableDef;
import de.brands4friends.daleq.core.TableType;

public class NamedFieldTypeReferenceTest {

    public static final String NAMED_FIELD_STR = "NAMED_FIELD";
    private TableType tableType;

    @TableDef("TABLE_WITH_FIELD")
    static class TableWithField {
        public static final FieldDef NAMED_FIELD = Daleq.fd(DataType.BIGINT);
    }

    @Before
    public void setUp() throws Exception {
        final TableTypeFactory tableTypeFactory = new TableTypeFactory();
        tableType = tableTypeFactory.create(TableWithField.class);
    }

    @Test
    public void resolve_should_returnExistingFieldByName() {
        final NamedFieldTypeReference reference = new NamedFieldTypeReference(NAMED_FIELD_STR);
        final FieldType fieldType = reference.resolve(tableType);
        assertThat(fieldType.getName(), is(NAMED_FIELD_STR));
    }

    @Test
    public void resolve_ofNonExistingField_should_returnNull() {
        final NamedFieldTypeReference reference = new NamedFieldTypeReference("NOT_EXISTING_FIELD");
        assertThat(reference.resolve(tableType), nullValue());
    }

    @Test(expected = NullPointerException.class)
    public void resolve_ofNull_should_throw() {
        new NamedFieldTypeReference(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolveOnNullTable_should_throw() {
        final NamedFieldTypeReference reference = new NamedFieldTypeReference(NAMED_FIELD_STR);
        final FieldType fieldType = reference.resolve(null);
        // should have already failed
        Assert.assertThat(fieldType, nullValue());
    }
}
