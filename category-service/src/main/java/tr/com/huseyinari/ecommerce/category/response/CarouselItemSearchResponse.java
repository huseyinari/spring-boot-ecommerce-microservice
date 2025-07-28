package tr.com.huseyinari.ecommerce.category.response;

public record CarouselItemSearchResponse (
    String title,
    String subtitle,
    String link,
    String linkTitle,
    Integer listOrder,
    String imageUrl
)
{}
