package com.instantpaympos

import `in`.credopay.payment.sdk.CredopayPaymentConstants
import `in`.credopay.payment.sdk.PaymentActivity
import `in`.credopay.payment.sdk.PaymentManager
import `in`.credopay.payment.sdk.Utils
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.*
import org.json.JSONObject
import org.json.JSONTokener

class InstantpayMposModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    val SUCCESS: String = "SUCCESS";
    val FAILED: String = "FAILED"
    lateinit var DATA: String;
    private var responsePromise: Promise? = null

    override fun getName(): String {
        return NAME
    }
    companion object {
        const val NAME = "InstantpayMpos"
    }

    private val activityEventListener = object: BaseActivityEventListener() {

        override fun onActivityResult(
            activity: Activity?,
            requestCode: Int,
            resultCode: Int,
            data: Intent?
        ) {
            super.onActivityResult(activity, requestCode, resultCode, data)

            if (requestCode == 1) {

                when (resultCode) {

                    CredopayPaymentConstants.TRANSACTION_COMPLETED -> {

                        val res = JSONObject();
                        if (data != null) {
                            res.put("rrn",data.getStringExtra("rrn"))
                            res.put("transactionId",data.getStringExtra("transaction_id"))
                            res.put("maskedPan",data.getStringExtra("masked_pan"))
                            res.put("tc",data.getStringExtra("tc"))
                            res.put("tvr",data.getStringExtra("tvr"))
                            res.put("tsi",data.getStringExtra("tsi"))
                            res.put("approvalCode",data.getStringExtra("approval_code"))
                            res.put("network",data.getStringExtra("network"))
                            res.put("cardApplicationName",data.getStringExtra("card_application_name"))
                            res.put("cardHolderName",data.getStringExtra("card_holder_name"))
                            res.put("appVersion",data.getStringExtra("app_version"))
                            res.put("cardType",data.getStringExtra("card_type"))
                            res.put("accountBalance",data.getStringExtra("account_balance"))
                            res.put("transactionType",data.getStringExtra("transaction_type"))
                        }

                        return resolve("Transaction Successfull", SUCCESS, res.toString());
                    }
                    CredopayPaymentConstants.TRANSACTION_CANCELLED -> {

                        val res = JSONObject();
                        if (data != null) {
                            res.put("error",data.getStringExtra("error"))
                        }

                        return resolve("Transaction Cancelled", FAILED, res.toString());
                    }
                    CredopayPaymentConstants.VOID_CANCELLED -> {

                        return resolve("Transaction Void Cancelled");
                    }
                    CredopayPaymentConstants.LOGIN_FAILED -> {

                        if (data != null) {
                            val error = data.getStringExtra("error")
                            if (error != null) {
                                return resolve(error)
                            }
                        }
                    }
                    CredopayPaymentConstants.CHANGE_PASSWORD -> {

                        return resolve("Request Change Password by User",SUCCESS, "", "ChangePassword");
                    }
                    CredopayPaymentConstants.CHANGE_PASSWORD_SUCCESS -> {

                        return resolve("Password changed successfully",SUCCESS, "", "PasswordChanged");
                    }
                    CredopayPaymentConstants.CHANGE_PASSWORD_FAILED -> {
                        
                        return resolve("Failed to change password", FAILED);
                    }
                    else -> return resolve("Out of the box request code");
                }
            }
        }
    }

    init {
        reactContext.addActivityEventListener(activityEventListener)
    }

    //Credopay Methods
    private fun logoutDevice(){
        PaymentManager.getInstance().logout();
    }

    private fun startPayment(options: String){
        try{

            val activity = currentActivity ?: return resolve("Activity doesn't exist")

            val items = JSONTokener(options).nextValue() as JSONObject;

            val intent = Intent(activity, PaymentActivity::class.java);

            val getTransactionType =  items.getString("transactionType").uppercase();

            if(getTransactionType == "PURCHASE"){
                intent.putExtra("TRANSACTION_TYPE", CredopayPaymentConstants.PURCHASE);
            }
            else if(getTransactionType == "MICROATM"){
                intent.putExtra("TRANSACTION_TYPE", CredopayPaymentConstants.MICROATM);
            }
            else if(getTransactionType == "UPI"){
                intent.putExtra("TRANSACTION_TYPE", CredopayPaymentConstants.UPI);
            }

            if(items.has("debugMode")){
                intent.putExtra("DEBUG_MODE", items.getString("debugMode").toBoolean());
            }
            else{
                intent.putExtra("DEBUG_MODE", false);
            }

            if(items.has("production")){
                intent.putExtra("PRODUCTION", items.getString("production").toBoolean());
            }

            val transAmount = items.getString("amount").toInt() * 100;
            intent.putExtra("AMOUNT", transAmount);

            intent.putExtra("LOGIN_ID", items.getString("loginId"));

            intent.putExtra("LOGIN_PASSWORD", items.getString("loginPassword"));

            intent.putExtra("MOBILE_NUMBER", items.getString("mobile"));

            if(items.has("customerRefNo")){
                intent.putExtra("CRN_U", items.getString("customerRefNo"));
            }

            if(items.has("successTimeout")){
                val getTimeSec = items.getString("successTimeout").toInt() * 1000;
                intent.putExtra("SUCCESS_DISMISS_TIMEOUT", getTimeSec);
            }

            if(items.has("optional1")){
                intent.putExtra("CUSTOM_FIELD1", items.getString("optional1"));
            }

            if(items.has("optional2")){
                intent.putExtra("CUSTOM_FIELD2", items.getString("optional2"));
            }

            if(items.has("optional3")){
                intent.putExtra("CUSTOM_FIELD3", items.getString("optional3"));
            }

            if(items.has("optional4")){
                intent.putExtra("CUSTOM_FIELD4", items.getString("optional4"));
            }

            if(items.has("optional5")){
                intent.putExtra("CUSTOM_FIELD5", items.getString("optional5"));
            }

            if(items.has("optional6")){
                intent.putExtra("CUSTOM_FIELD6", items.getString("optional6"));
            }

            if(items.has("optional7")){
                intent.putExtra("CUSTOM_FIELD7", items.getString("optional7"));
            }

            if(items.has("optional8")){
                intent.putExtra("CUSTOM_FIELD8", items.getString("optional8"));
            }

            if(items.has("optional9")){
                intent.putExtra("CUSTOM_FIELD9", items.getString("optional9"));
            }

            if(items.has("optional10")){
                intent.putExtra("CUSTOM_FIELD10", items.getString("optional10"));
            }

            if(items.has("isLogo")){

                val id = reactApplicationContext.resources.getIdentifier("mposlogo", "drawable", reactApplicationContext.packageName);

                if(id > 0){
                    intent.putExtra("LOGO", Utils.getVariableImage(ContextCompat.getDrawable(reactApplicationContext, id)));
                }
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            activity.startActivityForResult(intent , 1);

        }
        catch (e: Exception){
            resolve(e.message.toString()+" #SPAYEXP");
        }
    }

    private fun resolve(message: String, status: String = FAILED ,data: String = "", actCode: String = "" ){

        if(responsePromise == null){
            return;
        }

        val map: WritableMap = Arguments.createMap();
        map.putString("status",status);
        map.putString("message",message);
        map.putString("data",data);
        map.putString("actCode",actCode);

        responsePromise!!.resolve(map);
        responsePromise = null;
    }

    private fun logPrint(value: String?) {
        if (value == null) {
            return
        }
        Log.i("InstantpayMpos*", value)
    }

    //React Methods

    @ReactMethod
    fun disconnectDevice(){
        logoutDevice();
    }

    @ReactMethod
    fun startTransaction(options: String, prm: Promise){
        try {

            responsePromise = prm;

            if(options.length == 0){
                return resolve("Options cannot be empty");
            }

            val items = JSONTokener(options).nextValue() as JSONObject;

            val getTransactionType =  items.getString("transactionType").uppercase();

            val listOfTransaction = arrayOf("PURCHASE", "UPI", "MICROATM");

            //Check Transaction Type
            if(!listOfTransaction.contains(getTransactionType)){
                return resolve("Invalid Transaction Type Passed");
            }

            //Check Production Key
            if(!items.has("production")){
                return resolve("Missing Production Key");
            }

            //Check Amount Key
            if(!items.has("amount")){
                return resolve("Missing Amount Key");
            }

            //Check Login Id Key
            if(!items.has("loginId")){
                return resolve("Missing Login Id Key");
            }

            //Check Login Password Key
            if(!items.has("loginPassword")){
                return resolve("Missing Login Password Key");
            }

            //Check Mobile Key
            if(!items.has("mobile")){
                return resolve("Missing Mobile Key");
            }

            startPayment(options);

        }
        catch (e: Exception) {
            resolve(e.message.toString()+" #STRNEXP");
        }
    }
}
