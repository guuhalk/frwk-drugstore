package dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import model.enumeration.ProductStatus;

@Getter
@Setter
public class ProductDTO {

	private String id;
	
	private String sku;
	
	private String name;
	
	private String description;
	
	private String photoPath;
	
	private BigDecimal price;
	
	private Long inventoryQuantity;
	
	private ProductStatus status;
	
	private CategoryDTO categoryDTO;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;

}
