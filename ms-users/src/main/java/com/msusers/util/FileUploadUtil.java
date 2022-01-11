package com.msusers.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.msschemas.dto.MultipartFileDTO;

public class FileUploadUtil {

	public static final String USER_PHOTO_BASE_PATH = "user-photos/";

	public static void saveFile(String uploadDir, String fileName, MultipartFileDTO multipartFileDTO) throws IOException {
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = new ByteArrayInputStream(multipartFileDTO.getBytes())) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}
}
