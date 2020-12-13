package lifecube.repository.dynamodb;

import lifecube.entity.dynamodb.SubmissionEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface SubmissionRepository extends CrudRepository<SubmissionEntity, Integer> {

}
