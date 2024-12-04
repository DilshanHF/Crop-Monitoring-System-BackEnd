package lk.ijse.aad67.backendaadcoursework.dao;


import lk.ijse.aad67.backendaadcoursework.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDao extends JpaRepository<StaffEntity,String> {

}
