package com.example.applicationtracker;

public class ApplicationPOJO {
    private String companyName;
    private String jobName;
    private String description;


    public ApplicationPOJO() {
    }

    public ApplicationPOJO(String companyName, String jobName, String description) {
        this.companyName = companyName;
        this.jobName = jobName;
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobName() {
        return jobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
