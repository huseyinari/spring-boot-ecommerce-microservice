package tr.com.huseyinari.ecommerce.product.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Converter
public class ProductVariantIndexConverter implements AttributeConverter<Map<String, Object>, String> {
    private static final Logger logger = LoggerFactory.getLogger(ProductVariantIndexConverter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return objectMapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException exception) {
            logger.error("Product Variant Index (Map -> String) convert işlemi sırasında hata oluştu: {}", exception.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
            return objectMapper.readValue(s, type);
        } catch (JsonProcessingException exception) {
            logger.error("Product Variant Index (String -> Map) convert işlemi sırasında hata oluştu: {}", exception.getMessage());
            return null;
        }
    }
}
