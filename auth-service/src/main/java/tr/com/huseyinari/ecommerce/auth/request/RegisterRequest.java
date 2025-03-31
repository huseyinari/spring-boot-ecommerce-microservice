package tr.com.huseyinari.ecommerce.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Kullanıcı adı alanı boş olamaz.")
    @Size(min = 6, message = "Kullanıcı adı alanı en az 6 karakter içermelidir.")
    @Size(max = 25, message = "Kullanıcı adı alanı en fazla 25 karakter içerebilir.")
    String userName,

    @NotBlank(message = "Parola alanı boş olamaz.")
    @Size(min = 8, message = "Parola alanı en az 8 karakter içermelidir.")
    @Size(max = 25, message = "Parola alanı en fazla 25 karakter içerebilir.")
    String password,

    @NotBlank(message = "Ad alanı boş olamaz.")
    @Size(min = 3, message = "Ad alanı en az 3 karakter içermelidir.")
    @Size(max = 25, message = "Ad alanı en fazla 25 karakter içerebilir.")
    String firstName,

    @NotBlank(message = "Soyad alanı boş olamaz.")
    @Size(min = 3, message = "Soyad alanı en az 3 karakter içermelidir.")
    @Size(max = 25, message = "Soyad alanı en fazla 25 karakter içerebilir.")
    String lastName,

    @NotBlank(message = "Email alanı boş olamaz.")
    @Email(message = "Lütfen geçerli bir mail adresi giriniz.")
    String email
) {}


