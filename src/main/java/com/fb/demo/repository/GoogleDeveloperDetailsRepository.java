package com.fb.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fb.demo.entity.GoogleDeveloperDetails;

@Repository
public interface GoogleDeveloperDetailsRepository
                extends JpaRepository<GoogleDeveloperDetails, Integer> {

    public GoogleDeveloperDetails findByTenant(Integer id);

}
