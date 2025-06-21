package tr.com.huseyinari.ecommerce.category.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuCategoryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Integer totalProductCount;
    private Long parentId;
    private List<MenuCategoryResponse> subCategories = new ArrayList<>();
}
