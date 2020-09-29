package com.fb.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fb.demo.entity.EmailProp;

@Repository
public interface EmailPropRepository extends JpaRepository<EmailProp, Integer> {

}
