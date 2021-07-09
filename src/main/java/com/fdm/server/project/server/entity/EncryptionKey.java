package com.fdm.server.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fdm.server.project.server.user.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "EncryptionKey")
@Table(
        name = "encryption_key"
)
public class EncryptionKey {

    @Id
    @Column(
            name = "encryption_key_id"
    )
    private Long encryptionKeyId;

    @Column(
            name = "key",
            columnDefinition = "bigint",
            nullable = false
    )
    private Integer key;

    public EncryptionKey(Long encryptionKeyId, Integer key) {
        this.encryptionKeyId = encryptionKeyId;
        this.key = key;
    }

    public EncryptionKey() {

    }

    public Long getEncryptionKeyId() {
        return encryptionKeyId;
    }

    public void setEncryptionKeyId(Long encryptionKeyId) {
        this.encryptionKeyId = encryptionKeyId;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
