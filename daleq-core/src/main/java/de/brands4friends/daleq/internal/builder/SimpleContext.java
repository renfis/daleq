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

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.TemplateValueFactory;
import de.brands4friends.daleq.TypeConversion;
import de.brands4friends.daleq.internal.conversion.TypeConversionImpl;
import de.brands4friends.daleq.internal.template.TemplateValueFactoryImpl;
import de.brands4friends.daleq.internal.types.CachingTableTypeFactoryDecorator;
import de.brands4friends.daleq.internal.types.TableTypeFactory;
import de.brands4friends.daleq.internal.types.TableTypeFactoryImpl;

public class SimpleContext implements Context {

    private final TypeConversion typeConversion = new TypeConversionImpl();
    private final TemplateValueFactory templateValueFactory = TemplateValueFactoryImpl.getInstance();
    private final TableTypeFactory tableTypeFactory = new CachingTableTypeFactoryDecorator(new TableTypeFactoryImpl());

    @Override
    public TypeConversion getTypeConversion() {
        return this.typeConversion;
    }

    @Override
    public TemplateValueFactory getTemplateValueFactory() {
        return templateValueFactory;
    }

    @Override
    public TableTypeFactory getTableTypeFactory() {
        return tableTypeFactory;
    }
}
