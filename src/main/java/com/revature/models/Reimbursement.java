package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="reimbursements")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reimbursement {

    @Id
    @Column(name = "reimbursement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String description;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) default 'Pending'")
    private String status;

    @Column(name = "expense_amount")
    private BigDecimal expenseAmount;

    public void setEmployee(Employee employee) {
    }
}

