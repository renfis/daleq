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

package de.brands4friends.daleq.internal.dbunit.dataset;

import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

import de.brands4friends.daleq.TableData;
import de.brands4friends.daleq.internal.dbunit.IDataSetFactory;

public class InMemoryDataSetFactory implements IDataSetFactory {
    @Override
    public IDataSet create(final List<TableData> tables) throws DataSetException {
        final DefaultDataSet dataSet = new DefaultDataSet();
        for (TableData tableData : tables) {
            dataSet.addTable(createTableAdapter(tableData));
        }
        return dataSet;
    }

    private ITable createTableAdapter(final TableData tableData) {
        return new TableAdapter(tableData);
    }
}
