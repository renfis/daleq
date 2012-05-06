package de.brands4friends.daleq.jdbc;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import de.brands4friends.daleq.internal.container.SchemaContainer;

public interface IDataSetFactory {
    IDataSet create(SchemaContainer schema) throws DataSetException;
}
