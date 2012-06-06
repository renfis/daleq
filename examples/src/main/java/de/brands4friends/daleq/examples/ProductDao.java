package de.brands4friends.daleq.examples;

import java.util.List;

public interface ProductDao {
    Product findById(long id);
    List<Product> findBySize(String size);
}
