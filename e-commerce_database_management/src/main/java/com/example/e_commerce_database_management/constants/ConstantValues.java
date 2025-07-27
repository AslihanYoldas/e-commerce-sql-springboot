package com.example.e_commerce_database_management.constants;

import java.util.List;
import java.util.Map;

public class ConstantValues {

    final public static String FAKE_STORE_API_URL = "https://fakestoreapi.com";

    final public static Map<String, String> PARENT_CATEGORY_MAP = Map.of(
            "men's clothing", "Fashion",
            "women's clothing", "Fashion",
            "jewelery", "Accessories",
            "electronics", "");

    final public static Map<String, List<String>> CATEGORY_SUPPLIER_MAP = Map.of(
            "Fashion", List.of("Fashion World", "Clothing Corp", "Urban Style Co."),
            "Accessories", List.of("TrendBits", "Luxury Gems", "Chic Addict"),
            "Electronics", List.of("TechNova", "GadgetPro", "NextGen Devices"),
            "Furniture", List.of("Comfort Living", "ModernSpace"),
            "Beauty and Personal Care", List.of("PureBloom", "LuxeCare"),
            "Toys & Hobbies", List.of("HappyHands", "WonderToys"),
            "Sports & Outdoor", List.of("ActiveEdge", "SportyLife")

    );
}
