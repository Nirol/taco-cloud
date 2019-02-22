package tacos.domain;
import java.util.Date;
import java.util.List;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Data;

@Data
@Entity
@RestResource(rel="tacos", path="tacos")
public class Taco {
	@Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	@NotNull(message="Respect your Taco, give it a name!")
	 @Size(min=3, message="Name must be at least 3 characters long")
 private String name;
	
	
	 private Date createdAt;
	 
	 
@Size(min=1, message="You must choose at least 1 ingredient")
@ManyToMany(targetEntity=Ingredient.class)
 private List<Ingredient> ingredients;

@PrePersist
void createdAt() {
this.createdAt = new Date();
}



}