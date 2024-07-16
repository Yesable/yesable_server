package com.example.yesable_be.repository.mariadb;

import com.example.yesable_be.model.entity.mariadb.CoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CoreUser, Long> {

}
