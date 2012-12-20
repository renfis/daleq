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

package de.brands4friends.daleq.core;

import java.math.BigInteger;

import org.joda.time.LocalDate;

final class DateTemplateValue implements TemplateValue {

    public static final long MAX_VALUE = 2932896;
    private static final BigInteger MODULUS = BigInteger.valueOf(MAX_VALUE + 1);

    @Override
    public Object transform(final long value) {
        final BigInteger bd = BigInteger.valueOf(value);
        final int remainder = bd.mod(MODULUS).intValue();
        return new LocalDate(0).plusDays(remainder);
    }
}
