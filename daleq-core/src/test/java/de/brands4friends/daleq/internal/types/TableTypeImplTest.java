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

package de.brands4friends.daleq.internal.types;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import com.google.common.base.Optional;

import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.FieldType;
import de.brands4friends.daleq.TableType;
import de.brands4friends.daleq.TemplateValue;
import de.brands4friends.daleq.internal.template.StringTemplateValue;
import de.brands4friends.daleq.test.EqualsAssert;

public class TableTypeImplTest {

    @Test
    public void testHashcodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(TableTypeImpl.class);
    }

    @Test
    public void findStructureByDefOfExisting_should_returnStructure() {
        final DataType integer = DataType.INTEGER;
        final FieldDef origin = Daleq.fd(integer).name("propertyDef");
        final FieldType fieldType = new FieldTypeImpl(
                "P NAME",
                integer,
                Optional.<TemplateValue>of(new StringTemplateValue("bar")),
                origin
        );
        final TableType table =
                new TableTypeImpl("SOME_NAME",
                        fieldType);

        assertThat(table.findFieldBy(origin), is(fieldType));
    }

    @Test
    public void findStructureByDefOfNonExisting_should_notReturnStructure() {
        final FieldDef origin = Daleq.fd(DataType.INTEGER).name("propertyDef");
        final TableType table = new TableTypeImpl("SOME_NAME");
        assertThat(table.findFieldBy(origin), is(nullValue()));
    }
}
