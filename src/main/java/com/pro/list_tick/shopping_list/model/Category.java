package com.pro.list_tick.shopping_list.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "Colour cannot be blank")
    @Size(min = 7, max = 7, message = "Colour must be exactly 7 characters long")
    private String colour;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "category")
    private List<ShoppingList> shoppingLists;

    @Nullable // for one shared category across the whole system
    @Column(name = "account_id")
    private UUID accountId;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colour='" + colour + '}';
    }

}
