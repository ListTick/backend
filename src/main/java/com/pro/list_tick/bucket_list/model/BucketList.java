package com.pro.list_tick.bucket_list.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "bucket_list")
@Data
public class BucketList {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "Active cannot be null")
    private Boolean active;

    @NotNull(message = "Shared cannot be null")
    private Boolean shared;

    @PastOrPresent(message = "Creation date cannot be in the future")
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull(message = "Account id cannot be null")
    @Column(name = "account_id")
    private UUID accountId;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            mappedBy = "bucketList")
    private List<SharedBucketList> sharedBucketLists;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "bucketList")
    private List<Item> items;

    @Override
    public String toString() {
        return "BucketList{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", active=" + active +
            ", shared=" + shared +
            ", creationDate=" + creationDate +
            ", category=" + (category != null ? category.getId() : null) +
            ", accountId=" + accountId +
            '}';
    }

}
