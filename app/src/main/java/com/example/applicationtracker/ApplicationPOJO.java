package com.example.applicationtracker;

public class ApplicationPOJO {
    private String companyName;
    private String jobName;


    public ApplicationPOJO() {
    }

    public ApplicationPOJO(String companyName, String jobName) {
        this.companyName = companyName;
        this.jobName = jobName;
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

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
