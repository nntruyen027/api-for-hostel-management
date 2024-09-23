package qbit.entier.hostel.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class FileUtil {
    private static final String UPLOAD_DIR = "uploads/";
    
	public static String saveFile(MultipartFile file) throws IOException {
		Date current = new Date();
		String fileName = current.getTime() + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/" + UPLOAD_DIR)
                .path(fileName)
                .toUriString();
        
        return fileDownloadUri;
	}
	
	public static void deleteFile(String filePath) throws IOException {
		 String oldFileName = Paths.get(filePath.split("uploads/")[1]).getFileName().toString();
         Path oldFilePath = Paths.get(UPLOAD_DIR + oldFileName);
         if (Files.exists(oldFilePath)) {
             Files.delete(oldFilePath);
         }
         
	}
}
