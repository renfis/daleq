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
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.annotation.Nullable;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.DataType;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.NoSuchDaleqFieldException;
import de.brands4friends.daleq.TableData;
import de.brands4friends.daleq.TableDef;
import nl.jqno.equalsverifier.Warning;

public class ImmutableTableDataTest {

    @TableDef("THE_TABLE")
    public static class TheTable {
        public static final FieldDef ID = Daleq.fd(DataType.INTEGER);
    }

    @Test
    public void testHashcodeAndEquals() {
        forClass(ImmutableTableData.class).suppress(Warning.NULL_FIELDS).verify();
    }

    @Test
    public void getValuesOfField_ifFieldExists_should_returnThoseFields() {
        final TableData table = Daleq.aTable(TheTable.class).withRowsUntil(5).build(new SimpleContext());
        final List<Optional<String>> expected = Lists.transform(
                Lists.newArrayList("0", "1", "2", "3", "4"),
                new Function<String, Optional<String>>() {
                    @Override
                    public Optional<String> apply(@Nullable final String input) {
                        if (input == null) {
                            return Optional.absent();
                        }
                        return Optional.of(input);
                    }
                }
        );
        assertThat(Lists.newArrayList(table.getValuesOfField("ID")), Matchers.is(expected));
    }

    @Test(expected = NoSuchDaleqFieldException.class)
    public void getValuesOfField_ifFieldDoesNotExist_sould_fail() {
        final TableData table = Daleq.aTable(TheTable.class).withRowsUntil(5).build(new SimpleContext());
        final Iterable<Optional<String>> result = table.getValuesOfField("DOES_NOT_EXIST");
        // should have failed
        assertThat(result, Matchers.nullValue());
    }
}
