package tacos.web.api;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;

import org.springframework.hateoas.Resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path="/design",
 produces="application/json")
@CrossOrigin(origins="*")
public class DesignTacoController {
 private TacoRepository tacoRepo;
 @Autowired
 EntityLinks entityLinks;
 public DesignTacoController(TacoRepository tacoRepo) {
 this.tacoRepo = tacoRepo;
 }
 @GetMapping("/recent")
 public  Resources<TacoResource> recentTacos() {
 PageRequest page = PageRequest.of(
 0, 12, Sort.by("createdAt").descending());
 
 List<Taco> tacos = tacoRepo.findAll(page).getContent();
 List<TacoResource> tacoResources =
 new TacoResourceAssembler().toResources(tacos);
 Resources<TacoResource> recentResources =
		 new Resources<TacoResource>(tacoResources);
 
 recentResources.add(
		 linkTo(methodOn(DesignTacoController.class).recentTacos())
		 .withRel("recents"));
 
 return recentResources;

 }
 
 @GetMapping("/{id}")
 public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
  Optional<Taco> optTaco = tacoRepo.findById(id);
  if (optTaco.isPresent()) {
  return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
  }
  return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
 }
 
 @PostMapping(consumes="application/json")
 @ResponseStatus(HttpStatus.CREATED)
 public Taco postTaco(@RequestBody Taco taco) {
  return tacoRepo.save(taco);
 }
 
 
}