package com.revature.daos;

import com.revature.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer>{
    Reimbursement findByDescription(String description);

    List<Reimbursement> findByDescriptionContainingIgnoreCase(String pattern);

    @Query("FROM Reimbursement WHERE name LIKE %:pattern% ")
    List<Reimbursement> findByDescriptionSearch(@Param("pattern") String pattern);
}
