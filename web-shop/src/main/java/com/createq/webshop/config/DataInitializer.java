package com.createq.webshop.config;

import com.createq.webshop.model.CategoryModel;
import com.createq.webshop.model.ProductModel;
import com.createq.webshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Autowired
    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) {
            return;
        }
        CategoryModel womenCategory = new CategoryModel();
        womenCategory.setName("Women");

        CategoryModel menCategory = new CategoryModel();
        menCategory.setName("Men");

        List<ProductModel> womenProducts = new ArrayList<>();
        womenProducts.add(createProduct(womenCategory, "AIR JORDAN 1 MID - Sneakers high", 550.00, "The AIR JORDAN 1 MID is a minimalist classic for any sneaker outfit.", 150));
        womenProducts.add(createProduct(womenCategory, "Champion FX III Chunky sports shoes", 269.00, "These modern sneakers combine bold, chunky design with breathable materials.", 200));
        womenProducts.add(createProduct(womenCategory, "Elegant Stiletto Heels - Black", 320.00, "Classic black stiletto heels, perfect for formal events or a night out.", 80));
        womenProducts.add(createProduct(womenCategory, "Comfortable Walking Sandals - Beige", 180.00, "Lightweight and comfortable sandals with arch support for summer walks.", 300));
        womenProducts.add(createProduct(womenCategory, "Puma Carina Street - White/Gold", 350.00, "Retro-inspired sneakers with a modern twist and a comfortable SoftFoam+ sockliner.", 250));
        womenProducts.add(createProduct(womenCategory, "UGG Classic Mini II Boots - Chestnut", 750.00, "Iconic sheepskin boots, pre-treated to repel moisture and stains.", 100));
        womenProducts.add(createProduct(womenCategory, "Vans Old Skool - Black & White", 380.00, "The timeless skate shoe featuring the iconic side stripe.", 400));
        womenProducts.add(createProduct(womenCategory, "Birkenstock Arizona - Oiled Leather", 600.00, "A comfort legend and a fashion staple with its contoured cork footbed.", 180));

        List<ProductModel> menProducts = new ArrayList<>();
        menProducts.add(createProduct(menCategory, "FILA Boots Sneaker - Wheat", 250.00, "Blending the rugged durability of boots with the casual comfort of sneakers.", 180));
        menProducts.add(createProduct(menCategory, "Boots SOLITAIR SKECHERS", 250.00, "Designed with a high-top silhouette and durable materials.", 220));
        menProducts.add(createProduct(menCategory, "Classic Oxford Dress Shoes - Brown", 450.00, "Timeless leather Oxford shoes, a staple for any gentleman's wardrobe.", 120));
        menProducts.add(createProduct(menCategory, "Adidas Adilette Slides - Blue", 99.99, "The iconic Adilette slides, perfect for the pool, beach, or casual wear.", 500));
        menProducts.add(createProduct(menCategory, "New Balance 574 Core - Grey", 480.00, "A versatile and reliable sneaker with a clean and classic silhouette.", 350));
        menProducts.add(createProduct(menCategory, "Dr. Martens 1460 Smooth Leather Boots", 850.00, "The original Dr. Martens boot, known for its durability and iconic yellow stitching.", 90));
        menProducts.add(createProduct(menCategory, "Converse Chuck 70 - Parchment", 420.00, "A premium, re-crafted classic with modern details for enhanced comfort.", 300));
        menProducts.add(createProduct(menCategory, "Timberland 6-Inch Premium Waterproof Boots", 950.00, "The original waterproof boot designed more than 40 years ago.", 75));

        womenCategory.setProducts(womenProducts);
        menCategory.setProducts(menProducts);

        categoryRepository.saveAll(List.of(womenCategory, menCategory));

    }

    private ProductModel createProduct(CategoryModel category, String name, Double price, String description, int stock) {
        ProductModel product = new ProductModel();
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setStockQuantity(stock);
        return product;
    }
}