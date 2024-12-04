package lk.ijse.aad67.backendaadcoursework.dao;


import lk.ijse.aad67.backendaadcoursework.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDao extends JpaRepository<FieldEntity,String> {


}
