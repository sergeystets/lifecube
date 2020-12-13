package lifecube.repository.elastic;

import lifecube.entity.elastic.SubmissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SubmissionSearchRepository
    extends ElasticsearchRepository<SubmissionEntity, String> {

  Page<SubmissionEntity> findByTextContains(String text, Pageable pageable);

}
