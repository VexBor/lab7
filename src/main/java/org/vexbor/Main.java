package org.vexbor;

import org.vexbor.data.CategoryRepository;
import org.vexbor.data.DatabaseManager;
import org.vexbor.data.ProductRepository;
import org.vexbor.model.Category;
import org.vexbor.model.Product;

public class Main {
  static void main() {
    DatabaseManager db = new DatabaseManager("./data/supermarket.db");
    db.init();

    CategoryRepository categories = new CategoryRepository(db);
    ProductRepository products = new ProductRepository(db);

    Category fruits = categories.create(new Category(null, "Fruits " + System.currentTimeMillis()));
    Product apple = products.create(new Product(null, "Apple", 12.5, fruits.id()));
    System.out.println("Created: " + fruits + ", " + apple);

    System.out.println("findById: " + products.findById(apple.id()).orElseThrow());
    System.out.println("All categories: " + categories.findAll());

    products.update(new Product(apple.id(), "Green apple", 14.0, fruits.id()));
    System.out.println("Updated: " + products.findById(apple.id()).orElseThrow());

    System.out.println("Deleted product: " + products.deleteById(apple.id()));
    System.out.println("Deleted category: " + categories.deleteById(fruits.id()));
  }
}
