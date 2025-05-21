package com.automation.core.commerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessTypeRequest {
    private String name;
    private String description;
}


//Types of Businesses Based on Industry:
//Retail: Selling goods directly to consumers.
//        Manufacturing: Producing goods from raw materials.
//        Services: Providing intangible products, says Study.com.
//        Agriculture: Farming and related activities.
//Construction: Building and related infrastructure.
//Technology: Developing and selling software or hardware.
//Healthcare: Providing medical services and related products.
//Education: Providing educational services, says AFRIKTA.
//Financial Services: Banks, credit unions, insurance companies, and investment firms.
//Real Estate: Buying, selling, and managing properties.
//        Transportation: Moving goods or people.
//Hospitality: Hotels, restaurants, and other venues for accommodation and food.
//        Entertainment: Movies, theaters, music, and sports.
//Utilities: Providing essential services like electricity, gas, and water.
//III. Other Business Categories:
//Franchise: A business model where an established brand grants permission to operate a business under its name.
//Joint Venture: A partnership between two or more companies to undertake a specific project.
//Small Business: A business with limited resources and a smaller scope of operations.
//Large Business: A business with significant resources and a broader scope of operations.
//        E-commerce: Selling products online.