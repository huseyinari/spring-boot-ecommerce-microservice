package tr.com.huseyinari.ecommerce.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tr.com.huseyinari.utils.IOUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    private final AmazonS3 amazonS3;

    @Value("${huseyinari.ecommerce.aws.s3.bucketName}")
    private String bucketName;

    public void uploadFile(MultipartFile multipartFile) {
        File file = null;

        try {
            final String originalFileName = multipartFile.getOriginalFilename();

            if (StringUtils.isBlank(originalFileName) || !originalFileName.contains(".")) {
                throw new RuntimeException("Geçersiz dosya adı !");
            }

            final String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            final long fileSize = multipartFile.getSize();  // byte

            final String newFileName = UUID.randomUUID() + "." + extension;

            file = File.createTempFile("upload-", newFileName);
            multipartFile.transferTo(file);

            PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
            amazonS3.putObject(request);

            logger.info("Dosya başarıyla yüklendi. Dosya: {}", file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Dosya yükleme işlemi başarısız. Dosya: {}", file.getPath());
            logger.error("Exception: {}", e.getMessage());

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

    public byte[] getFileContent(String fileName) {
        byte[] result = null;

        try {
            S3Object s3Object = amazonS3.getObject(bucketName, fileName);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            result = IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Dosya indirme işlemi başarısız. Dosya Adı: {}", fileName);
            logger.error("Exception: {}", e.getMessage());
        }

        return result;
    }
}
