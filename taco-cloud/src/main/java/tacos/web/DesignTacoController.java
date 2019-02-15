package tacos.web;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.InitBinder;
import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")


public class DesignTacoController {
	 private final IngredientRepository ingredientRepo;	 
	 private TacoRepository designRepo;
	 
	 
	 @Autowired	 
	 public DesignTacoController(
	 IngredientRepository ingredientRepo,
	 TacoRepository designRepo) {
	 this.ingredientRepo = ingredientRepo;
	 this.designRepo = designRepo;
	 }

	 @ModelAttribute(name = "order")
	 public Order order() {
	 return new Order();
	 }
	 @ModelAttribute(name = "taco")
	 public Taco taco() {
	 return new Taco();
	 }
	 
	 
	 @InitBinder 
	     public void initBinder(WebDataBinder binder) {	 
	         binder.registerCustomEditor(Ingredient.class, new IngredientEditor(ingredientRepo));
	 
	 }
	 
//	 @ModelAttribute
//	    public void addIngredientToModel(Model model) {
//
//		List<Ingredient> ingredients = new ArrayList<>();
//		 ingredientRepo.findAll().forEach(i -> ingredients.add(i));
//
//		Type[] types = Ingredient.Type.values();
//
//		for (Type type : types) {
//			model.addAttribute(type.toString().toLowerCase(),
//					filterByType(ingredients, type));
//		}
//
//		
//	}
//	  @GetMapping
//	    public String showDesignForm(Model model) {
//	        model.addAttribute("design", new Taco());
//	        return "design";
//	    }
	  
	  
	  
	  
	  
	  
	  
	  @GetMapping
	  public String showDesignForm(Model model) {
	    List<Ingredient> ingredients = new ArrayList<>();
	    ingredientRepo.findAll().forEach(i -> ingredients.add(i));
	    
	    Type[] types = Ingredient.Type.values();
	    for (Type type : types) {
	      model.addAttribute(type.toString().toLowerCase(), 
	          filterByType(ingredients, type));      
	    }
	    model.addAttribute("design", new Taco());

	    return "design";
	  }
	  
	  
	@PostMapping
	public String processDesignn(@Valid Taco design, Errors errors,@ModelAttribute Order order) {
		System.out.println("test -- processDesignn -- entry ");
		
		if (errors.hasErrors()) {
			 return "design";
			 }
		 
		 Taco saved = designRepo.save(design);
		 System.out.println("test -- processDesignn -- after taco desgin save ");
		 order.addDesign(saved);

		 
		 
	 log.info("Processing design: " + design);
	 return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
	

		
		  return ingredients
	                .stream()
	                .filter(x -> x.getType().equals(type))
	                .collect(Collectors.toList());
		
		
	}


}