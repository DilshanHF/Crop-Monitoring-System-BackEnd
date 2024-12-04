package lk.ijse.aad67.backendaadcoursework.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lk.ijse.aad67.backendaadcoursework.customStatusCode.SelectedErrorStatus;
import lk.ijse.aad67.backendaadcoursework.dao.CropDao;
import lk.ijse.aad67.backendaadcoursework.dao.FieldDao;
import lk.ijse.aad67.backendaadcoursework.dao.LogDao;
import lk.ijse.aad67.backendaadcoursework.dto.impl.CropDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.entity.impl.CropEntity;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.CropService;
import lk.ijse.aad67.backendaadcoursework.utill.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CropServiceIMPL implements CropService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CropDao cropDao;

    @Autowired
    private Mapping mapping;

    @Autowired
    private LogDao logDao;

    @Autowired
    private FieldDao fieldDao;

    @Override
    public void saveCrop(CropDto cropDto) {
        if ( cropDao.save(mapping.toCropEntity(cropDto)) == null) {
            throw new DataPersistException();
        }

    }

    @Override
    public List<CropDto> getCropList() {
        return mapping.asCropDtoList(cropDao.findAll());
    }

    @Override
    public Status getCropById(String cropId) {
        if (cropDao.existsById(cropId)) {
            CropEntity cropEntity = cropDao.getReferenceById(cropId);
            return mapping.toCropDto(cropEntity);
        }else {
            return new SelectedErrorStatus(2,"Crop not found");
        }
    }

    @Override
    public void updateCrop(String cropId, CropDto cropDto) {
        Optional<CropEntity> byId = cropDao.findById(cropId);
        if (byId.isPresent()) {
            byId.get().setCropCategory(cropDto.getCropCategory());
            byId.get().setCropCommonName(cropDto.getCropCommonName());
            byId.get().setCropImage(cropDto.getCropImage());
            byId.get().setCropScientificName(cropDto.getCropScientificName());
            byId.get().setCropSeason(cropDto.getCropSeason());
            byId.get().setField(fieldDao.getReferenceById(cropDto.getFieldCode()));
            byId.get().setLog(logDao.getReferenceById(cropDto.getLogCode()));
        }
    }

    @Override
    public void deleteCrop(String cropId) {
        Optional<CropEntity> existedUser = cropDao.findById(cropId);
        if(!existedUser.isPresent()){
            throw new ItemNotFoundException("User with id " + cropId + " not found");
        }else {

            cropDao.deleteById(cropId);


        }
    }

    @Override
    public String generateCropID() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT c.cropCode FROM CropEntity c ORDER BY c.cropCode DESC", String.class);
        query.setMaxResults(1);


        String lastCropId = query.getResultStream().findFirst().orElse(null);

        if (lastCropId != null) {

            int generatedCropId = Integer.parseInt(lastCropId.replace("C00-", "")) + 1;
            return String.format("C00-%03d", generatedCropId);
        } else {

            return "C00-001";
        }
    }
}
