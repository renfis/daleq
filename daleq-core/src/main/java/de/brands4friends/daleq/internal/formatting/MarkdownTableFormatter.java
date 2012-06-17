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

package de.brands4friends.daleq.internal.formatting;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import de.brands4friends.daleq.FieldContainer;
import de.brands4friends.daleq.RowContainer;
import de.brands4friends.daleq.TableContainer;
import de.brands4friends.daleq.internal.types.FieldType;
import de.brands4friends.daleq.internal.types.TableType;

public class MarkdownTableFormatter implements TableFormatter {

    public static final char PAD_CHAR = ' ';
    public static final String NEWLINE = "\n";

    private static enum Alignment {LEFT, RIGHT}

    private static class Column {
        private final int width;
        private final String name;
        private final Alignment alignment;

        private Column(final int width, final String name, final Alignment alignment) {
            this.width = width;
            this.name = name;
            this.alignment = alignment;
        }
    }

    @Override
    public String format(final TableContainer table) {
        final StringBuilder builder = new StringBuilder();
        try {
            formatTo(table, builder);
        } catch (IOException e) {
            throw new IllegalStateException("We did not expect an IOException.", e);
        }
        return builder.toString();
    }

    @Override
    public void formatTo(final TableContainer table, final Appendable appendable) throws IOException {
        final List<Column> columns = calculateColumns(table);

        appendHead(appendable, columns);
        appendBody(table, appendable, columns);
    }

    private void appendBody(final TableContainer table, final Appendable appendable, final List<Column> columns) throws IOException {
        for (RowContainer row : table.getRows()) {

            appendSeparator(appendable);
            for (Column column : columns) {
                appendable.append(" ");
                final FieldContainer field = row.getFieldBy(column.name);
                final String value = field.getValue().or("");
                if (column.alignment == Alignment.LEFT) {
                    appendable.append(Strings.padEnd(value, column.width, PAD_CHAR));
                } else {
                    appendable.append(Strings.padStart(value, column.width, PAD_CHAR));
                }
                appendable.append(" ");
                appendSeparator(appendable);
            }
            appendNewline(appendable);
        }
    }

    private void appendHead(final Appendable appendable, final List<Column> columns) throws IOException {
        appendColumnHeaders(appendable, columns);
        appendHorizontalHeaderLine(appendable, columns);
    }

    private List<Column> calculateColumns(final TableContainer table) {
        final TableType type = table.getTableType();
        final List<Column> columns = Lists.newArrayList();

        for (FieldType field : type.getFields()) {
            final String fieldName = field.getName();
            final int width = calcMaxWidth(table, fieldName);
            columns.add(new Column(width, fieldName, Alignment.RIGHT));
        }
        return columns;
    }

    private void appendColumnHeaders(final Appendable appendable, final List<Column> columns) throws IOException {
        appendSeparator(appendable);
        for (Column column : columns) {
            appendColumnHeader(column, appendable);
            appendSeparator(appendable);
        }
        appendNewline(appendable);
    }

    private void appendHorizontalHeaderLine(final Appendable appendable, final List<Column> columns) throws IOException {
        appendSeparator(appendable);
        for (Column column : columns) {

            if (column.alignment == Alignment.LEFT) {
                appendAlignmentOperator(appendable);
            }
            appendable.append(Strings.repeat("-", column.width + 1));
            if (column.alignment == Alignment.RIGHT) {
                appendAlignmentOperator(appendable);
            }

            appendSeparator(appendable);
        }
        appendNewline(appendable);
    }

    private void appendAlignmentOperator(final Appendable appendable) throws IOException {
        appendable.append(':');
    }

    private void appendNewline(final Appendable appendable) throws IOException {
        appendable.append(NEWLINE);
    }

    private void appendSeparator(final Appendable appendable) throws IOException {
        appendable.append('|');
    }

    private void appendColumnHeader(final Column column, final Appendable appendable) throws IOException {
        appendable.append(" ");
        appendable.append(Strings.padEnd(column.name, column.width, PAD_CHAR));
        appendable.append(" ");
    }

    private int calcMaxWidth(final TableContainer table, final String fieldName) {
        int width = Ints.max(fieldName.length(), 2);
        for (Optional<String> value : table.getValuesOfField(fieldName)) {
            if (value.isPresent()) {
                width = Ints.max(width, value.get().length());
            }
        }
        return width;
    }
}
