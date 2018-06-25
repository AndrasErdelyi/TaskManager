package com.erdelyiandras.job.repository;

import org.springframework.data.repository.CrudRepository;

import com.erdelyiandras.job.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
