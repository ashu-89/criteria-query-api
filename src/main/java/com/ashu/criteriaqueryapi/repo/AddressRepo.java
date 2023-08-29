package com.ashu.criteriaqueryapi.repo;

import com.ashu.criteriaqueryapi.model.Address;
import com.ashu.criteriaqueryapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
}
