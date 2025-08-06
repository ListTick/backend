package com.pro.list_tick.bucket_list.model;

import java.util.UUID;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "shared_bucket_list")
@Data
public class SharedBucketList {

    @EmbeddedId
    private SharedBucketListId id;

    @ManyToOne
    @MapsId("bucketListId")
    @JoinColumn(name = "bucket_list_id")
    private BucketList bucketList;

    public UUID getAccountId() {
        return id != null ? id.getAccountId() : null;
    }

    public void setBucketListAndAccount(BucketList bucketList, UUID accountId) {
        if (this.id == null) {
            this.id = new SharedBucketListId();
        }
        this.id.setBucketListId(bucketList.getId());
        this.id.setAccountId(accountId);
        this.bucketList = bucketList;
    }

    @Override
    public String toString() {
        return "SharedBucketList{" +
            "id=" + id +
            '}';
    }

}
