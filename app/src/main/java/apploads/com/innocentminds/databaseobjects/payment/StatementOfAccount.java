package apploads.com.innocentminds.databaseobjects.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import apploads.com.innocentminds.databaseobjects.base.BaseResponse;

public class StatementOfAccount extends BaseResponse {
    @SerializedName("total_amount")
    @Expose
    private String total_amount;

    @SerializedName("payments")
    @Expose
    private List<Payment> payments;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
