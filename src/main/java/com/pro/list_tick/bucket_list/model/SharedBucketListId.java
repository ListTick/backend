package com.pro.list_tick.bucket_list.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class SharedBucketListId implements Serializable {

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "bucket_list_id")
    private UUID bucketListId;

}
