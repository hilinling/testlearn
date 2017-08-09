package hello;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by ling on 17/7/13.
 */

@RepositoryRestResource(collectionResourceRel = "people",path = "people")
public interface PersonRepository extends PagingAndSortingRepository<Person,Long> {

    List<Person> findByLastName(@Param("name") String name);
}
