package org.telegram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class History {
    private String id;
    private List<Product> products;
    private Double overallPrice;
}
