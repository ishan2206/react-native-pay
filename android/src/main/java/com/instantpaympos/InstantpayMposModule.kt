package com.instantpaympos

import `in`.credopay.payment.sdk.CredopayPaymentConstants
import `in`.credopay.payment.sdk.PaymentActivity
import `in`.credopay.payment.sdk.PaymentManager
import android.app.Activity
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.BaseActivityEventListener
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import org.json.JSONObject
import org.json.JSONTokener

class InstantpayMposModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

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
            //Log.d("*myApp", "TRANSACTION_COMPLETED")
            return resolve("Transaction Successfull", SUCCESS);
          }
          CredopayPaymentConstants.TRANSACTION_CANCELLED -> {
            //Log.d("*myApp", "TRANSACTION_CANCELLED")
            return resolve("Transaction Cancelled");
          }
          CredopayPaymentConstants.VOID_CANCELLED -> {
            //Log.d("*myApp", "VOID_CANCELLED")
            return resolve("Transaction Void Cancelled");
          }
          CredopayPaymentConstants.LOGIN_FAILED -> {
            //Log.d("*myApp", "LOGIN_FAILED")
            if (data != null) {
              val error = data.getStringExtra("error")
              if (error != null) {
                return resolve(error)
              }
            }
          }
          CredopayPaymentConstants.CHANGE_PASSWORD -> {
            //Log.d("*myApp", "CHANGE_PASSWORD")
            return resolve("Request Change Password by User");
          }
          CredopayPaymentConstants.CHANGE_PASSWORD_SUCCESS -> {
            //Log.d("*myApp", "CHANGE_PASSWORD_SUCCESS")
            return resolve("Password changed successfully", SUCCESS);
          }
          CredopayPaymentConstants.CHANGE_PASSWORD_FAILED -> {
            //Log.d("*myApp", "CHANGE_PASSWORD_FAILED")
            return resolve("Failed to change password", SUCCESS);
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

      Log.d("InstantpayMpos*", "options List: "+options );

      //val items = JSONTokener(options).nextValue() as JSONObject;

      val intent = Intent(activity, PaymentActivity::class.java);

      intent.putExtra("TRANSACTION_TYPE", CredopayPaymentConstants.PURCHASE);
      intent.putExtra("DEBUG_MODE", true);
      intent.putExtra("PRODUCTION", true);
      intent.putExtra("AMOUNT", 100);
      //intent.putExtra("LOGO", Utils.getVariableImage(ContextCompat.getDrawable(applicationContext, R.drawable.ipaylogo)));
      intent.putExtra("LOGIN_ID", "2000016248");

      //if(changePassword)
      //intent.putExtra("LOGIN_PASSWORD", "Qwert@123");
      //else
      intent.putExtra("LOGIN_PASSWORD", "Qwert@123");

      intent.putExtra("CUSTOM_FIELD1", "test");

      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

      activity.startActivityForResult(intent , 1);

      //resolve("Testing calls");
    }
    catch (e: Exception){
      resolve(e.message.toString()+" #SPAYEXP");
    }
  }

  private fun resolve(message: String, status: String = FAILED ,data: String = "" ){

    if(responsePromise == null){
      return;
    }

    val map: WritableMap = Arguments.createMap();
    map.putString("status",status);
    map.putString("message",message);
    map.putString("data",data);

    responsePromise!!.resolve(map);
    responsePromise = null;
  }

  private fun LogPrint(value: String?) {
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
