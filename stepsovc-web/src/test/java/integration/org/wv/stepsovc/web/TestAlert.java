package org.wv.stepsovc.web;

import java.util.Date;

public class TestAlert {

    public Date getAlertDate() {
        return alertDate;
    }

    private Date alertDate;

    public TestAlert(Date alertDate) {
        this.alertDate = alertDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestAlert testAlert = (TestAlert) o;

        if (alertDate != null ? !alertDate.equals(testAlert.alertDate) : testAlert.alertDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return alertDate != null ? alertDate.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TestAlert{" +
                "alertDate=" + alertDate +
                '}';
    }
}
