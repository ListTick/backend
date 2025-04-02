package com.pro.list_tick.shopping_list.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "category")
public class Category {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Min(value = 3, message = "Name has to have at least 3 characters long")
    @Max(value = 255, message = "Name cannot be more than 255 characters long")
    private String name;

    @NotBlank(message = "Colour cannot be blank")
    @Min(value = 3, message = "Name has to have at least 3 characters long")
    @Max(value = 255, message = "Name cannot be more than 255 characters long")
    private String colour;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "category")
    private List<ShoppingList> shoppingLists;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private SLAccount account;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colour='" + colour + '}';
    }

}
