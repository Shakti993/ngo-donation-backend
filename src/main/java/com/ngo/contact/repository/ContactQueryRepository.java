package com.ngo.contact.repository;

import com.ngo.contact.entity.ContactQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactQueryRepository extends JpaRepository<ContactQuery, Long> {
}