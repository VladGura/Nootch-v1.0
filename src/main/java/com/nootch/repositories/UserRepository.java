package com.nootch.repositories;

import com.nootch.entities.NootchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<NootchUser, Long> {

}
