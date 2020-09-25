package com.fb.demo.repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fb.demo.entity.FbDeveloperDetails;

@Repository
public interface FbDeveloperDetailsRepository extends JpaRepository<FbDeveloperDetails, Integer> {

    public FbDeveloperDetails findByParentTenant(Integer id);

    public List<FbDeveloperDetails> getAllFbDeveloperDetails(PageRequest of);

}
