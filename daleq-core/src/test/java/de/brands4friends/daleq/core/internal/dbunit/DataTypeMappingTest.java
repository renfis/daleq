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

package de.brands4friends.daleq.core.internal.dbunit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.brands4friends.daleq.core.DataType;

public class DataTypeMappingTest {

    @Test
    public void toDbUnit_should_mapNumeric() {
        assertThat(DataTypeMapping.toDbUnit(DataType.NUMERIC), is(org.dbunit.dataset.datatype.DataType.NUMERIC));
    }

    @Test
    public void toDaleq_should_mapNumeric() {
        assertThat(DataTypeMapping.toDaleq(org.dbunit.dataset.datatype.DataType.NUMERIC), is(DataType.NUMERIC));
    }

}
