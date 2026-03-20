package tr.com.huseyinari.ecommerce.product.service;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.ecommerce.product.client.StorageClient;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.ecommerce.product.mapper.ProductImageMapper;
import tr.com.huseyinari.ecommerce.product.repository.JpaEntityResolver;
import tr.com.huseyinari.ecommerce.product.repository.ProductImageRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductImageCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductImageCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductImageSearchResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;
import tr.com.huseyinari.ecommerce.product.shared.response.StorageObjectSearchResponse;
import tr.com.huseyinari.springweb.rest.RequestUtils;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class ProductImageService {
    private static final Logger logger = LoggerFactory.getLogger(ProductImageService.class);

    private final ProductImageRepository repository;
    private final ProductImageMapper mapper;
    private final ProductService productService;
    private final StorageClient storageClient;
    private final JpaEntityResolver jpaEntityResolver;

    public ProductImageService(
        ProductImageRepository repository,
        ProductImageMapper mapper,
        @Lazy ProductService productService,
        StorageClient storageClient,
        JpaEntityResolver jpaEntityResolver
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.productService = productService;
        this.storageClient = storageClient;
        this.jpaEntityResolver = jpaEntityResolver;
    }

    List<ProductImageSearchResponse> findByProductId(String productId) {
        return this.repository.findByProduct_IdOrderByCreatedDateAsc(productId)
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    public ProductImageCreateResponse create(ProductImageCreateRequest request) {
        final String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow();

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
        } catch (Exception exception) {
            // TODO: Aslında servisten 404 yerine farklı bir hata response da dönmüş olabilir. Bu gibi durumlarda servise erişilememe durumu kesin olarak belirlenmeli.
            throw new RuntimeException("Depolama servisine erişilemedi. Lütfen daha sonra tekrar deneyiniz.");
        }

        Product product = this.jpaEntityResolver.getReference(Product.class, productResponse.id());

        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setStorageObjectId(storageObject.getId());

        this.repository.save(productImage);

        return new ProductImageCreateResponse(product.getId(), storageObject.getId());
    }
}
