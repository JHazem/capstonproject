package org.example.mycapston.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class AddJobDto {
    @NotNull
    private String title;
    @NotNull
    private String companyName;
    @NotNull
    @NotEmpty
    private Set<Long> skillIds;
    @NotNull
    private String location;
    @NotNull
    private String jobType;

    // for update purpose
    private Long jobId;
}
