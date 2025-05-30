package tr.com.huseyinari.ecommerce.storage.response;

public record FileContentBase64Response(
    String base64,
    String fileName,
    String extension
) {}
