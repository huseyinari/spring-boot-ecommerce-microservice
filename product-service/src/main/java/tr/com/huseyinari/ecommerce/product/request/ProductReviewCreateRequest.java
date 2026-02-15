package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.*;

public record ProductReviewCreateRequest(
    @NotBlank(message = "Geçersiz ürün.")
    String productId,

    @NotBlank(message = "Açıklama alanı zorunludur.")
    String description,

    @NotNull(message = "Puan alanı zorunludur.")
    @Min(value = 1, message = "Geçersiz puan.")
    @Max(value = 10, message = "Geçersiz puan.")
    Integer rating,

    @NotBlank(message = "İsim alanı zorunludur.")
    String reviewerName,

    @NotBlank(message = "Email alanı boş olamaz.")
    @Email(message = "Lütfen geçerli bir mail adresi giriniz.")
    String reviewerEmail
) {}
