package de.brands4friends.daleq.internal.dbunit;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import de.brands4friends.daleq.container.SchemaContainer;

public interface IDataSetFactory {
    IDataSet create(SchemaContainer schema) throws DataSetException;
}
