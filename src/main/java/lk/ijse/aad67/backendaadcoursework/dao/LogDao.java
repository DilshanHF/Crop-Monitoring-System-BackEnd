package lk.ijse.aad67.backendaadcoursework.dao;

import Final.springBoot.backend.entity.impl.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao extends JpaRepository<LogEntity, String> {
}
