package com.pro.list_tick.bucket_list.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "item")
@Data
public class Item {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name has to have 3-255 characters")
    private String name;

    @NotNull(message = "Active field cannot be null")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "bucket_list_id")
    private BucketList bucketList;

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", active=" + active +
            '}';
    }

}
