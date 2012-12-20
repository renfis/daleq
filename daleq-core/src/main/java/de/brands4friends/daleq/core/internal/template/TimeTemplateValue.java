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

package de.brands4friends.daleq.core.internal.template;

import java.math.BigInteger;

import org.joda.time.LocalTime;

import de.brands4friends.daleq.core.TemplateValue;
import de.brands4friends.daleq.core.internal.formatting.DateFormatter;

public class TimeTemplateValue implements TemplateValue {

    private static final BigInteger MODULUS = BigInteger.valueOf(86400000L);

    @Override
    public String render(final long value) {

        final BigInteger bigInteger = BigInteger.valueOf(value);
        final int remainder = bigInteger.mod(MODULUS).intValue();

        final LocalTime localTime = new LocalTime(0, 0, 0, 0).plusSeconds(remainder);
        return DateFormatter.print(localTime);
    }
}
