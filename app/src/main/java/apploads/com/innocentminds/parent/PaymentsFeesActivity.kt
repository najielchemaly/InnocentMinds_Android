package apploads.com.innocentminds.parent

import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

import com.wang.avi.AVLoadingIndicatorView

import java.util.ArrayList

import apploads.com.innocentminds.BaseActivity
import apploads.com.innocentminds.R
import apploads.com.innocentminds.databaseobjects.payment.Payment
import apploads.com.innocentminds.databaseobjects.payment.StatementOfAccount
import apploads.com.innocentminds.helpers.LoadingIndicator
import apploads.com.innocentminds.helpers.StaticData
import apploads.com.innocentminds.helpers.utils.AppUtils
import apploads.com.innocentminds.services.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentsFeesActivity : BaseActivity() {

    internal lateinit var lblPaymentTitle: TextView
    internal lateinit var lblTotal: TextView
    internal lateinit var txtTotal: TextView
    internal lateinit var lstPayments: ListView
    internal lateinit var btnFees: Button
    internal lateinit var btnPayments: Button
    internal lateinit var btnClose: Button
    internal lateinit var paymentsAdapter: PaymentsAdapter
    internal lateinit var indicatorView: LoadingIndicator

    internal var statementOfAccount: StatementOfAccount? = StatementOfAccount()
    internal var allPayments: List<Payment> = ArrayList()
    internal var payments: List<Payment> = ArrayList()
    internal var fees: List<Payment> = ArrayList()

    override fun getContentViewId(): Int {
        return R.layout.payments_fees_activity
    }

    @Throws(Exception::class)
    override fun doOnCreate() {
        initView()
        styleView()
        initListeners()
    }

    private fun initView() {
        lblPaymentTitle = _findViewById(R.id.lblPaymentTitle)
        lblTotal = _findViewById(R.id.lblTotal)
        txtTotal = _findViewById(R.id.txtTotal)
        lstPayments = _findViewById(R.id.lstPayments)
        btnFees = _findViewById(R.id.btnFees)
        btnPayments = _findViewById(R.id.btnPayments)
        btnClose = _findViewById(R.id.btnClose)
        indicatorView = _findViewById(R.id.indicatorView)

        lblPaymentTitle.text = resources.getText(R.string.payments_and_fees)
        txtTotal.text = null

        getStatementOfAccount()
    }

    private fun styleView() {
        lblPaymentTitle.typeface = AppUtils.getTitleRegularFont(this)
        lblTotal.typeface = AppUtils.getBoldTypeface(this)
        txtTotal.typeface = AppUtils.getBoldTypeface(this)
        btnFees.typeface = AppUtils.getRegularTypeface(this)
        btnPayments.typeface = AppUtils.getRegularTypeface(this)
    }

    private fun initListeners() {
        btnPayments.setOnClickListener {
            btnFees.setBackgroundResource(R.drawable.border_white_rec_right)
            btnPayments.setBackgroundResource(R.drawable.border_grey_rec_left)

            paymentsAdapter.selectedButton = "1"
            paymentsAdapter.root = payments
            paymentsAdapter.notifyDataSetChanged()
        }

        btnFees.setOnClickListener {
            btnFees.setBackgroundResource(R.drawable.border_grey_rec_right)
            btnPayments.setBackgroundResource(R.drawable.border_white_rec_left)

            paymentsAdapter.selectedButton = "2"
            paymentsAdapter.root = fees
            paymentsAdapter.notifyDataSetChanged()
        }


        btnClose.setOnClickListener { finish() }
    }

    internal fun getStatementOfAccount() {
        indicatorView.show()

        val userId = if (StaticData.user.id == null) "0" else StaticData.user.id!!.toString()
        val erpId = if (StaticData.user.erp_id == null) "0" else StaticData.user.erp_id.toString()

        ApiManager.getService(true).getStatementOfAccount(userId, erpId, AppUtils.getLang(this)).enqueue(object : Callback<StatementOfAccount> {
            override fun onResponse(call: Call<StatementOfAccount>, response: Response<StatementOfAccount>) {
                if (response.body() != null) {
                    if (response.body()!!.status == 1) {
                        statementOfAccount = response.body()

                        if (statementOfAccount!!.payments != null) {
                            allPayments = statementOfAccount!!.payments
                            payments = allPayments.filter { item -> item.type_id == 1 }
                            fees = allPayments.filter { item -> item.type_id == 2 }
                        }

                        if (statementOfAccount!!.total_amount != null) {
                            txtTotal.text = statementOfAccount!!.total_amount
                        }

                        paymentsAdapter = PaymentsAdapter(payments, this@PaymentsFeesActivity)
                        lstPayments.adapter = paymentsAdapter
                    } else if (response.body()!!.message != null) {
                        AppUtils.showAlertWithMessage(this@PaymentsFeesActivity, "Innocent Minds", response.body()!!.message)
                    } else {
                        AppUtils.showAlertWithMessage(this@PaymentsFeesActivity, "Innocent Minds", resources.getString(R.string.an_error_occured))
                    }
                }

                indicatorView.hide()
            }

            override fun onFailure(call: Call<StatementOfAccount>, t: Throwable) {
                indicatorView.hide()
            }
        })
    }
}
