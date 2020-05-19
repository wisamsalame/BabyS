package com.babysitter.babys;

public class feedbackReport {

    String IDBS;
    String IDCU;
    String NameCS;

    String review;
    float stars ;
    boolean ifReadIt;
    boolean acceptOrUnaccept;

    public feedbackReport(String IDBS,String IDCU, String nameCS, String review, float stars, boolean ifReadIt, boolean acceptOrUnaccept) {
        this.IDBS = IDBS;
        this.IDCU = IDCU;
        NameCS = nameCS;
        this.review = review;
        this.stars = stars;
        this.ifReadIt = ifReadIt;
        this.acceptOrUnaccept = acceptOrUnaccept;
    }

    public feedbackReport() {
    }

    public String getIDCU() {
        return IDCU;
    }

    public void setIDCU(String IDCU) {
        this.IDCU = IDCU;
    }

    public boolean isIfReadIt() {
        return ifReadIt;
    }

    public void setIfReadIt(boolean ifReadIt) {
        this.ifReadIt = ifReadIt;
    }

    public boolean isAcceptOrUnaccept() {
        return acceptOrUnaccept;
    }

    public void setAcceptOrUnaccept(boolean acceptOrUnaccept) {
        this.acceptOrUnaccept = acceptOrUnaccept;
    }

    public String getIDBS() {
        return IDBS;
    }

    public void setIDBS(String IDBS) {
        this.IDBS = IDBS;
    }

    public String getNameCS() {
        return NameCS;
    }

    public void setNameCS(String nameCS) {
        NameCS = nameCS;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
