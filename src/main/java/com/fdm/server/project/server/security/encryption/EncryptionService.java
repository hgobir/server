package com.fdm.server.project.server.security.encryption;

import com.fdm.server.project.server.entity.EncryptionKey;
import com.fdm.server.project.server.repository.EncryptionKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EncryptionService {

    private final EncryptionKeyRepository encryptionKeyRepository;

    @Autowired
    public EncryptionService(EncryptionKeyRepository encryptionKeyRepository) {
        this.encryptionKeyRepository = encryptionKeyRepository;
    }

    @Transactional
    public String encryptValue(String unencryptedStr) {
        EncryptionKey encryptionKey = encryptionKeyRepository.findByEncryptionKeyId(1L).get();
        Integer key = encryptionKey.getKey();
        char[] unencryptedCharArr = unencryptedStr.toCharArray();
        return encrypt(unencryptedCharArr, key);
    }

    @Transactional
    public String decryptValue(String unencryptedStr) {
        EncryptionKey encryptionKey = encryptionKeyRepository.findByEncryptionKeyId(1L).get();
        Integer key = encryptionKey.getKey();
        char[] unencryptedCharArr = unencryptedStr.toCharArray();
        return decrypt(unencryptedCharArr, key);
    }

    private String encrypt(char[] chars, int key) {
        StringBuffer stringBuffer = new StringBuffer();

        for(char c : chars) {
            char encyptedChar = c += key;
            stringBuffer.append(encyptedChar);
        }
        return stringBuffer.toString();
    }

    private String decrypt(char[] chars, int key) {
        StringBuffer stringBuffer = new StringBuffer();

        for(char c : chars) {
            char encyptedChar = c -= key;
            stringBuffer.append(encyptedChar);
        }
        return stringBuffer.toString();
    }




}
