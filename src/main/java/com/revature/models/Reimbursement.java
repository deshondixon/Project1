package com.revature.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="reimbursements")
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

    public Reimbursement() {
    }

    public Reimbursement(int id, String description, LocalDate submissionDate, BigDecimal expenseAmount) {
        this.id = id;
        this.description = description;
        this.submissionDate = submissionDate;
        this.expenseAmount = expenseAmount;
    }

    public Reimbursement(String description, LocalDate submissionDate, BigDecimal expenseAmount) {
        this.description = description;
        this.submissionDate = submissionDate;
        this.expenseAmount = expenseAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", submissionDate=" + submissionDate +
                ", expenseAmount=" + expenseAmount +
                '}';
    }
}
