package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.GeneralException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.DEFAULT_ERROR_MESSAGE;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final UserService userService;
    private EncryptionService encryptionService;

    private Logger logger = LoggerFactory.getLogger(CredentialsService.class);

    public CredentialsService(CredentialsMapper credentialsMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public void insertCredentials(Credentials credentials) throws GeneralException {

        try {
            User userLoggedIn = userService.getUserLoggedIn();
            credentials.setUserid(userLoggedIn.getUserId());

            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);

            credentials.setKey(encodedKey);
            credentials.setPassword(encryptedPassword);

            credentialsMapper.insert(credentials);

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new GeneralException(DEFAULT_ERROR_MESSAGE);
        }
    }

    public void deleteCredentials(Integer noteId) throws GeneralException {
        try {
            credentialsMapper.delete(noteId);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new GeneralException(DEFAULT_ERROR_MESSAGE);
        }
    }

    public void updateCredentials(Credentials credential) throws GeneralException {
        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

            credential.setKey(encodedKey);
            credential.setPassword(encryptedPassword);

            credentialsMapper.update(credential);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new GeneralException(DEFAULT_ERROR_MESSAGE);
        }
    }

    public List<Credentials> loadCredentials() throws GeneralException {

        User userLoggedIn = userService.getUserLoggedIn();

        List<Credentials> credentialsFromUser = credentialsMapper.getCredentialsFromUser(userLoggedIn.getUserId());

        try{
            credentialsFromUser.forEach(credential -> {
                String decodedKey = new String(Base64.getDecoder().decode(credential.getKey()));
                credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
            });
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new GeneralException(DEFAULT_ERROR_MESSAGE);
        }



        return credentialsFromUser;
    }
}
