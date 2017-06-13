package com.example.redisExample;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RedisExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisExampleApplication.class, args);
	}
	@Bean
	HealthIndicator doYouLoveStLouis()
	{
		return new HealthIndicator()
				{

					@Override
					public Health health() {

						return Health.status("I <3 St Louis").build();
					}
			
				};
	}
	
//	@Bean
//	HealthIndicator healthIndicator() {
//		return () -> Health.status("I <3 JFokus!").build();
//	}
	
	
}

@Component
class jugCLR implements CommandLineRunner
{

	@Autowired
	private DeveloperRepository r;
	
	@Override
	public void run(String... args) throws Exception {
		Stream.of(new Developer("1", "Jenny", "Pivotal"), 
		new Developer("2", "Weiqi", "OCI"), 
		new Developer("3", "Bruce", "Ideal source"), 
		new Developer("4", "Chris", "Edward Jones"), 
		new Developer("5", "Veronica", "Century Link"),
		new Developer("6", "Ethan", "Daughtery Business Solutions")).forEach(r::save);
		
		r.findAll().forEach(System.out::println);
		
	}
	
}

//@RestController
//@EnableRedisRepositories
//class jugController
//{
//	
//	@Autowired
//	private DeveloperRepository r;
//	@RequestMapping("/")
//	public Iterable<Developer> getDevelopers()
//	{
//		return r.findAll();
//	}
//}


@RepositoryRestResource
interface DeveloperRepository extends CrudRepository<Developer, String>
{
	
}

@RedisHash("Developers")
class Developer
{
	@Id
	private String id;
	private String name;
	private String company;
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCompany() {
		return company;
	}
	public Developer(String id, String name, String company) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
	}
	@Override
	public String toString() {
		return "Developer [id=" + id + ", name=" + name + ", company=" + company + "]";
	}
	
	
	
}
