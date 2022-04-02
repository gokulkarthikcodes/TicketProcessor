package com.sync.ticketprocessor.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Category extends Auditable{

    private String category;
    private List<Category> subCategories;
    private List<Product> products;
}
