package lk.ijse.aad67.backendaadcoursework.dao;

import Final.springBoot.backend.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDao extends JpaRepository<FieldEntity,String> {


}
