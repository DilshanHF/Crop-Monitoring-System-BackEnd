package lk.ijse.aad67.backendaadcoursework.dao;

import lk.ijse.aad67.backendaadcoursework.dto.impl.EquipmentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDao extends JpaRepository<EquipmentDto,String> {

}
