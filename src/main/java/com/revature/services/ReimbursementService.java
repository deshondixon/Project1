package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReimbursementService {



    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }


    //GET-ALL
    public List<Reimbursement> getAllReimbursements(){
        List<Reimbursement> reimbursements = reimbursementDAO.findAll();
        return reimbursements;
    }

    // INSERT
    public Reimbursement addReimbursement(Reimbursement reimbursement) {
        Reimbursement returnedReimbursement = reimbursementDAO.save(reimbursement);

        if (returnedReimbursement.getId() > 0) {
            return returnedReimbursement;
        } else {
            System.out.println("Reimbursement failed to add");
        }
        return returnedReimbursement;
    }

    //GET BY ID
    public Reimbursement findReimbursementById(int id){
        return reimbursementDAO.findById(id).orElseThrow(() -> new ReimbursementNotFoundException("No Reimbursement found with id: " + id));
    }

    //UPDATE
    public Reimbursement updateReimbursement(Reimbursement r) {
        return reimbursementDAO.save(r);
    }


    //DELETE
    public boolean deleteReimbursement(int id) {
        reimbursementDAO.deleteById(id);
        return !(reimbursementDAO.existsById(id));
    }

    public List<Reimbursement> searchReimbursements(String searchPattern){
        return reimbursementDAO.findByDescriptionContainingIgnoreCase(searchPattern);
    }



}