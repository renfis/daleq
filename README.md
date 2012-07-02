Introduction
------------

Writing unit tests for SQL queries in a Java application stack is not easy. One of the challenges setting up the test data to run the query against. 

To keep tests comprehensive and maintainable we have the following requirements for such data:

* Data should be defined per test. We think it is not maintainable to have a single dump, which is used for all tests. Each test ensures that a certain aspect of the query is implemented correctly. We doubt that it is possible to set up a single dump which contains all possible test cases. We doubt even more that such a dump stays maintainable in the long run.
* Data should be defined in the test. 

Daleq is a DSL to define the content of a relational database in a concise and neat manner. 

Examples
--------

For now have a look at the [tests in the example code](https://github.com/brands4friends/daleq/blob/master/examples/src/test/java/de/brands4friends/daleq/examples/JdbcProductDaoTest.java).

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
