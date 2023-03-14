package org.example.mycapston.dto;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class JobFilterDto {
    private String title;
    private String jobType;
    private Long skillId;

    public String getTitle() {
        return title == null || title.isEmpty() ? null : title;
    }

    public String getJobType() {
        return title == null || jobType.isEmpty() ? null : jobType;
    }

    public Long getSkillId() {
        return skillId;
    }
}
