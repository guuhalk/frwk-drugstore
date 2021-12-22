package dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private String description;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;

}
