package lk.ijse.aad67.backendaadcoursework.utill;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class AppUtil {
    public static String profilePicToBase64(byte [] profilePic){
       return Base64.getEncoder().encodeToString(profilePic);
    }

    public static String convertImage(MultipartFile cropImage) throws IOException {
        byte [] image = cropImage.getBytes();
        String base64Image = AppUtil.profilePicToBase64(image);
        return base64Image;
    }
}