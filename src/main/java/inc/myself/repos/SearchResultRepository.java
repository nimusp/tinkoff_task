package inc.myself.repos;

import inc.myself.models.dbModels.DbResultModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchResultRepository extends CrudRepository<DbResultModel, Long> {
}
