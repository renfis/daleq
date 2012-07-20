Introduction
------------

Daleq is a DSL to define content of a relational database in a concise and neat manner. It is actually very simple. 

It lets you describe a relational database table directly in your unit test by
just writing those parts of the data that are subject to the test. Anything
else is filled out by Daleq. 

Let's have a look at an example:

```java
@Test
public void findBySize_should_returnThoseProductsHavingThatSize() {
    daleq.insertIntoDatabase(
            aTable(ProductTable.class)
                    .withRowsUntil(10)
                    .with(
                            aRow(10).f(SIZE, "S"),
                            aRow(11).f(SIZE, "S"),
                            aRow(12).f(SIZE, "M"),
                            aRow(13).f(SIZE, "L")
                    )
    );
    final List<Product> products = productDao.findBySize("S");

    assertProductsWithIds(products, 10L, 11L);
}
```
Obviously this test ensures that a query will filter products with a certain size. We insert 14 rows into the table. The first ten rows have arbitrary content. It does not matter, which content they actually have, they just don't have a size S. Then we explicitly add four further rows, two of them having a size of S. The other columns in these rows don't matter either. The test is about the SIZE column and therefore we just focus on it. Daleq will handle the rest for us just fine.

What we think about test data
-----------------------------

Writing unit tests for SQL queries in a Java application stack is not easy.
One of the challenges is setting up the test data the queries will run on.

To keep tests comprehensive and maintainable we have the following demands on such data:

* **Data should be defined per test**. We think it is not maintainable to have a single dump that is used for all tests. Each test ensures that a certain aspect of the query is implemented correctly. We doubt that it is possible to set up a single dump which contains all possible test cases. We doubt even more that such a dump stays maintainable in the long run.
* **Data should be defined close to the test**. The closer the data is to the test, the more likely is, that it stays in sync with the test. The closest the data can be to test is actually in the test method. Hence test data has to be set up in same language the test is written in.
* **Data setup should only describe the aspects that matter** and therefore should be free of redundancy. Since relational database tables tend to contain a lot of repetitive data, setting up this data is poses a challenge if such tests should stay mantainable.

How Daleq will help you
-----------------------

Daleq is a DSL, actually a family of some builders, which acts as a wrapper around [DbUnit](http://www.dbunit.org/). DbUnit is a great tool when it comes to batch scripting your database but it does a bad job to give you a tool to do this nicely and proper in unit tests. Daleq fills this gap. Instead of maintaining XML files which act like the content of your database you write that content directly in Java. Daleq is designed to benefit from code completion in your favorite IDE. What was a burden in times of clumsy XML will become a one liner with Daleq.

Next Steps
----------

1. [Learn about the DSL](https://github.com/brands4friends/daleq/wiki/Understanding-the-DSL)
1. [Have a look at some examples](https://github.com/brands4friends/daleq/wiki/Examples)


Build Status
-----------

[![Build Status](https://secure.travis-ci.org/brands4friends/daleq.png?branch=master)](http://travis-ci.org/brands4friends/daleq)
Authors
-------

**Lars Girndt**

Copyright and license
---------------------

Copyright 2012 brands4friends, Private Sale GmbH

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
