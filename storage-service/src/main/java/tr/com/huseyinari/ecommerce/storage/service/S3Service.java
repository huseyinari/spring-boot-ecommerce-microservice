package tr.com.huseyinari.ecommerce.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tr.com.huseyinari.ecommerce.storage.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.storage.request.DeleteFileRequest;
import tr.com.huseyinari.ecommerce.storage.request.ReadFileRequest;
import tr.com.huseyinari.ecommerce.storage.request.UploadFileRequest;
import tr.com.huseyinari.ecommerce.storage.response.ReadFileResponse;
import tr.com.huseyinari.ecommerce.storage.response.S3ReadFileResponse;
import tr.com.huseyinari.ecommerce.storage.response.S3UploadFileResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadFileResponse;
import tr.com.huseyinari.utils.IOUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements StorageService {
    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    private final ECommerceConfigurationProperties configurationProperties;
    private final AmazonS3 amazonS3;

    public UploadFileResponse uploadFile(UploadFileRequest request) {
        if (request.getMultipartFile() == null || request.getMultipartFile().isEmpty()) {
            throw new RuntimeException("Dosya seçilmedi.");
        }
        if (StringUtils.isBlank(request.getStorageName())) {
            throw new RuntimeException("Depolama adı boş olamaz.");
        }

        final MultipartFile multipartFile = request.getMultipartFile();
        final String bucketName = request.getStorageName();

        final Long maxUploadFileSize = this.configurationProperties.getMaxUploadFileSize();

        if (maxUploadFileSize.compareTo(multipartFile.getSize()) < 0) {
            throw new RuntimeException("Dosya boyutu çok büyük !");
        }

        final String originalFileName = multipartFile.getOriginalFilename();

        if (StringUtils.isBlank(originalFileName) || !originalFileName.contains(".")) {
            throw new RuntimeException("Geçersiz dosya adı !");
        }

        final String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        final long fileSize = multipartFile.getSize();  // byte

        final String newFileName = UUID.randomUUID() + "." + extension;

        File file = null;

        try {
            file = File.createTempFile("temp-", newFileName);
            multipartFile.transferTo(file);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newFileName, file);
            this.amazonS3.putObject(putObjectRequest);

            logger.info("Dosya başarıyla yüklendi. Dosya: {}", file.getPath());

            return new S3UploadFileResponse(newFileName, extension, fileSize, bucketName);
        } catch (Exception exception) {
            logger.error("Dosya yükleme işlemi başarısız. Exception: {}", exception.getMessage());
            throw new RuntimeException("Dosya yükleme işlemi başarısız. " + exception.getMessage());
        } finally {
            if (file != null && file.exists()) {
                boolean isDeleted = file.delete();
                if (!isDeleted) {
                    logger.error("Geçici dosya silinemedi ! Dosya Adı: {}", file.getPath());
                }
            }
        }
    }

    public ReadFileResponse getFileContent(ReadFileRequest request) {
        final String fileName = request.getFileName();
        final String bucketName = request.getStorageName();

        try {
            S3Object s3Object = this.amazonS3.getObject(bucketName, fileName);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            final byte[] fileContent = IOUtils.toByteArray(inputStream);
            return new S3ReadFileResponse(fileContent);
        } catch (AmazonS3Exception exception) {
            exception.printStackTrace();
            logger.error("(AmazonS3Exception) Dosya indirme işlemi başarısız. Dosya Adı: {}", fileName);
            logger.error("Exception Message: {}", exception.getMessage());

            if (exception.getStatusCode() == 404) {
                if ("NoSuchKey".equals(exception.getErrorCode())) {
                    throw new RuntimeException("Saklama sunucusunda dosya mevcut değil !");
                } else if ("NoSuchBucket".equals(exception.getErrorCode())) {
                    throw new RuntimeException("Dosyaya ait saklama alanı mevcut değil !");
                } else {
                    throw new RuntimeException("Uzak sunucuda dosyaya erişilemiyor !");
                }
            }

            throw new RuntimeException("Dosya uzak sunucudan okunuruken hata oluştu !");
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("Dosya indirme işlemi başarısız. Dosya Adı: {}", fileName);
            logger.error("Exception Message: {}", exception.getMessage());

            throw new RuntimeException("Dosya okumada hata oluştu !");
        }
    }

    @Override
    public void deleteFile(DeleteFileRequest request) {
        final String fileName = request.getFileName();
        final String bucketName = request.getStorageName();

        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("Dosya adı geçersiz olduğu için dosya silme işlemi başarısız.");
        }
        if (StringUtils.isBlank(bucketName)) {
            throw new RuntimeException("Saklama alanı adı geçersiz olduğu için dosya silme işlemi başarısız.");
        }

        try {
            this.amazonS3.deleteObject(bucketName, fileName);
            logger.info("Dosya silme işlemi tamamlandı. Bucket Adı: {}, Dosya Adı: {}", bucketName, fileName);
        } catch (AmazonS3Exception exception) {
            if (exception.getStatusCode() == 404 && "NoSuchBucket".equals(exception.getErrorCode())) {
                throw new RuntimeException("Saklama alanı bulunamadı !");
            }

            logger.error("[STRG-1049] - Dosya silme işlemi başarısız.");
            throw new RuntimeException("[STRG-1049] - Dosya silme işlemi başarısız.");
        } catch (Exception exception) {
            logger.error("[STRG-1050] - Dosya silme servisine erişim başarısız.");
            throw new RuntimeException("[STRG-1050] - Dosya silme servisine erişim başarısız.");
        }
    }
}
