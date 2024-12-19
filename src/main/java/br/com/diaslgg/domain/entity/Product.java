package br.com.diaslgg.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {

    private Long id;

    private String name;

    private String code;

    private Long quantity;

    public Product(String name, String code, Long quantity) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
