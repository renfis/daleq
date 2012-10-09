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

package de.brands4friends.daleq.examples;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

public class OrderTest {

    @Test
    public void accessors_should_beCorrect() {
        final long id = 342l;
        final long customerId = 2348l;
        final DateTime creation = new DateTime(234786823746l);

        final Order order = new Order(id, customerId, creation);

        assertThat(order.getId(), is(id));
        assertThat(order.getCustomerId(), is(customerId));
        assertThat(order.getCreation(), is(creation));
    }
}
