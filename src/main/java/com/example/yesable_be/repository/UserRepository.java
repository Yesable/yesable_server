package com.example.yesable_be.repository;

import com.example.yesable_be.model.CoreUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<CoreUser, Long> {





}
