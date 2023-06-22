package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "expense_amount")
    private BigDecimal expenseAmount;

}
