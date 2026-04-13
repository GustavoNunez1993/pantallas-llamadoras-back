package com.datalock.datalock.utils.infra;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class UploadToStorageService {

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.bucket-name}")
    private String bucketName;

    @Value("${gcp.credentials.path}")
    private String credentialsPath;

    /**
     * Sube un archivo al bucket configurado y devuelve la URL pública.
     *
     * @param file Archivo recibido (PNG, JPG, etc.)
     * @param folder Carpeta o prefijo dentro del bucket
     * @return URL pública del archivo subido
     */
    public String uploadFile(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío o es nulo");
        }

        try (InputStream serviceAccount = getClass().getResourceAsStream(credentialsPath)) {

            if (serviceAccount == null) {
                throw new IOException("No se pudo encontrar el archivo de credenciales en la ruta: " + credentialsPath);
            }

            // Crear cliente de Google Cloud Storage
            Storage storage = StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();

            // Nombre único del archivo
            String extension = getFileExtension(file.getOriginalFilename());
            String fileName = folder + "/" + UUID.randomUUID() + (extension != null ? "." + extension : "");

            // Subir archivo
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            storage.create(blobInfo, file.getBytes());

            // Hacer público el archivo (opcional)
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            // URL pública del archivo
            String publicUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);

            log.info("Archivo subido correctamente: {}", publicUrl);
            return publicUrl;

        } catch (Exception e) {
            log.error("Error al subir archivo al bucket de GCP", e);
            throw new RuntimeException("Error al subir el archivo al almacenamiento", e);
        }
    }

    /**
     * Obtiene la extensión del archivo.
     */
   private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }
}
