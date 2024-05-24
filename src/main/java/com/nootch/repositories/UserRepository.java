package com.nootch.repositories;

import com.nootch.entities.NootchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<NootchUser, Long> {

    default NootchUser getUserByEmail(String email) {
        if(findAll().stream().anyMatch(user -> user.getEmail().equals(email)))
            return findAll().stream().filter(user -> user.getEmail().equals(email)).findFirst().get();
        return null;
    }

    default NootchUser getUserByVerificationCode(String code) {
        if(findAll().stream().anyMatch(user -> user.getVerificationCode() != null && user.getVerificationCode().equals(code)))
            return findAll().stream().filter(user -> user.getVerificationCode() != null && user.getVerificationCode().equals(code)).findFirst().get();
        return null;
    }

    default NootchUser getUserByHttpSessionId(String httpSessionId) {
        if(findAll().stream().anyMatch(user -> user.getHttpSessionId() != null && user.getHttpSessionId().equals(httpSessionId)))
            return findAll().stream().filter(user -> user.getHttpSessionId() != null && user.getHttpSessionId().equals(httpSessionId)).findFirst().get();
        return null;
    }
}
