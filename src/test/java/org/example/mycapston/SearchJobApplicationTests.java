package org.example.mycapston;

import org.example.mycapston.models.Job;
import org.example.mycapston.services.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SearchJobApplicationTests {

	@Autowired
	public JobService jobService;

	@Test
	void contextLoads() {

		List<Job> full_time = jobService.getFilteredJobList(Optional.empty(),
				Optional.of(2L), Optional.empty());
		System.out.println(full_time.size());


	}

}
