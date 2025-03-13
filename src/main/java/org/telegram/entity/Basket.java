package org.telegram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    private String id;
    private List<Product> products;
    private Double overallPrice;
}
