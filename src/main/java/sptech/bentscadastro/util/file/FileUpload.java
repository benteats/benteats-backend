package sptech.bentscadastro.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {

    public String saveArchive(String fileName, MultipartFile file) throws IOException {

        Path uploadPath = Paths.get("Read-files");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve("Bents-txt-files" + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

        String filePath = "Bents-txt-files" + "-" + fileName;

        return filePath;
    }

}
