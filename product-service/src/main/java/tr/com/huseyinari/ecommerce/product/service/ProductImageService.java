package tr.com.huseyinari.ecommerce.product.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.ecommerce.product.client.StorageClient;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.ecommerce.product.repository.ProductImageRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductImageCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductImageCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;
import tr.com.huseyinari.ecommerce.product.shared.response.StorageObjectSearchResponse;
import tr.com.huseyinari.springweb.rest.RequestUtils;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository repository;
    private final ProductService productService;
    private final StorageClient storageClient;

    public ProductImageCreateResponse create(ProductImageCreateRequest request) {
        String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow();

        boolean exist = this.repository.findByProduct_IdAndStorageObjectId(request.productId(), request.storageObjectId()).isPresent();
        if (exist) {
            throw new RuntimeException("Belirtilen resim zaten üründe bulunuyor !");
        }

        ProductSearchResponse productResponse = this.productService.findById(request.productId());

        StorageObjectSearchResponse storageObject = null;
        try {
            SinhaRestApiResponse<StorageObjectSearchResponse> response = this.storageClient.findOne(request.storageObjectId());
            storageObject = response.getData();
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Seçilen dosya sistemde bulunamadı !");
        } catch (Exception e) {
            // TODO: Aslında servisten 404 yerine farklı bir hata response da dönmüş olabilir. Bu gibi durumlarda servise erişilememe durumu kesin olarak belirlenmeli.
            throw new RuntimeException("Depolama servisine erişilemedi. Lütfen daha sonra tekrar deneyiniz.");
        }

        // TODO: Product servisten searchobject döndüğü için Product entity'si oluşturup setliyorum. JPA'de bu durumun avantaj ve dezavantajları araştırılmalı.
        Product product = new Product();
        product.setId(productResponse.id());

        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setStorageObjectId(storageObject.getId());

        this.repository.save(productImage);

        return new ProductImageCreateResponse(product.getId(), storageObject.getId());
    }
}
