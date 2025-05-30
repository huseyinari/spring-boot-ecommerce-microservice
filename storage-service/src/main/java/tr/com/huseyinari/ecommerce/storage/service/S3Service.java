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
import tr.com.huseyinari.ecommerce.storage.request.S3GetFileRequest;
import tr.com.huseyinari.ecommerce.storage.request.S3UploadRequest;
import tr.com.huseyinari.ecommerce.storage.response.S3UploadResponse;
import tr.com.huseyinari.utils.IOUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    private final AmazonS3 amazonS3;

    public S3UploadResponse uploadFile(S3UploadRequest request) {
        if (request.multipartFile() == null) {
            throw new RuntimeException("Dosya seçilmedi.");
        }

        final MultipartFile multipartFile = request.multipartFile();
        final String bucketName = request.bucketName();
        File file = null;

        try {
            final String originalFileName = multipartFile.getOriginalFilename();

            if (StringUtils.isBlank(originalFileName) || !originalFileName.contains(".")) {
                throw new RuntimeException("Geçersiz dosya adı !");
            }

            final String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            final long fileSize = multipartFile.getSize();  // byte

            final String newFileName = UUID.randomUUID() + "." + extension;

            file = File.createTempFile("temp-", newFileName);
            multipartFile.transferTo(file);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newFileName, file);
            amazonS3.putObject(putObjectRequest);

            logger.info("Dosya başarıyla yüklendi. Dosya: {}", file.getPath());

            return new S3UploadResponse(newFileName, extension, fileSize, bucketName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Dosya yükleme işlemi başarısız. Exception: {}", e.getMessage());

            if (file != null) {
                logger.error("Hatalı Dosya: {}", file.getPath());
            }

            throw new RuntimeException("Dosya yükleme işlemi başarısız. " + e.getMessage());
        } finally {
            if (file != null && file.exists()) {
                boolean isDeleted = file.delete();
                if (!isDeleted) {
                    logger.error("Geçici dosya silinemedi ! Dosya Adı: {}", file.getPath());
                }
            }
        }
    }

    public byte[] getFileContent(S3GetFileRequest request) {
        final String fileName = request.fileName();
        final String bucketName = request.bucketName();

        byte[] result = null;

        try {
            S3Object s3Object = amazonS3.getObject(bucketName, fileName);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            result = IOUtils.toByteArray(inputStream);
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

        return result;
    }
}
