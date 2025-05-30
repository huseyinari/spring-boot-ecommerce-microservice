package tr.com.huseyinari.ecommerce.storage.response;

public record FileContentResponse(
    byte[] content,
    String fileName,
    String extension
) {}
