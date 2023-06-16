package com.revature.daos;

import com.revature.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionDAO extends JpaRepository<Position, Integer> {

    Position getByName(String name);
}
