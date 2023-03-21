<!-- markdownlint-disable MD024 MD034 MD033 -->

# react-native-instantpay-mpos

mPOS for [React Native](https://github.com/facebook/react-native) and support only for android.

## TOC

- [Installation](#installation)
- [Android Dependencies](#android-dependencies)
- [Usage](#usage)
- [Theming](#theming)
- [Proguard](#proguard)


## Installation

Using npm:

```shell
npm install --save react-native-instantpay-mpos
```

or using yarn:

```shell
yarn add react-native-instantpay-mpos
```

## Android Dependencies

Importing SDK to your Android Application

Add this in your root **build.gradle** at the end of repositories in **allprojects** section:

```
allprojects { 
    repositories { 
        maven { 
            credentials { 
                username 'myMavenRepo' 
                password 'CredoPaySDK' 
            } 
            url "https://mymavenrepo.com/repo/FrQdp1FhEvW3jjbX8Md4/" 
        }
        maven { url "https://jitpack.io" } 
    } 
}

```

Then reference the library in the dependency section: 

```
dependencies { 
    implementation 'in.credopay.payment.sdk:vm30-payment-sdk:3.0.3' 
}

```

**The SDK internally depends on the following libraries**

```
com.squareup.okhttp3:okhttp:3.12.1
com.squareup.retrofit2:retrofit:2.4.0
com.squareup.retrofit2:converter-gson:2.4.0

```

If your app conflicts with above dependencies, then you could exclude the library from 
the SDK and then can be implemented externally.

```
dependencies {
    implementation ('in.credopay.payment.sdk:vm30-payment-sdk:3.0.3')  
    {
        exclude module:'retrofit' 
    }
} 

```

## Usage

Supported method list :
1. disconnectDevice
2. startTransaction

```js

import { disconnectDevice, startTransaction } from 'react-native-instantpay-mpos';


//case

let options = {
    transactionType : 'PURCHASE',
    debugMode : true,
    amount : 1,
    ...
}

startTransaction(JSON.stringify(options)).then(res => {
    console.log(res);
}); 


```

**Note about startTransaction Method**

Possible options values : 

| key               | Description                                        |  type      | required       |
| ---------------   | -------------------------------------------------- | ---------- | -------------- |
| transactionType   | Transaction Type                                   | string     | Mandatory      |
| debugMode         | Debug Mode                                         | boolean    | Optional       |
| production        | Enable for Production                              | boolean    | Mandatory      |
| amount            | Amount                                             | int        | Optional      |
| loginId           | Login Id for login                                 | string     | Mandatory      |
| loginPassword     | Login password for login                           | string     | Mandatory      |
| mobile            | mobile for sending SMS                             | string     | Mandatory      |
| customerRefNo     | uniques for every trans.                           | string     | Optional       |
| successTimeout    | Success dismiss timeout (2 sec default)            | int        | Optional       |
| optional1         | Optional value                                     | string     | Optional       |
| optional2         | Optional value                                     | string     | Optional       |
| optional3         | Optional value                                     | string     | Optional       |
| optional4         | Optional value                                     | string     | Optional       |
| optional5         | Optional value                                     | string     | Optional       |
| optional6         | Optional value                                     | string     | Optional       |
| optional7         | Optional value                                     | string     | Optional       |
| optional8         | Optional value                                     | string     | Optional       |
| optional9         | Optional value                                     | string     | Optional       |
| optional10        | Optional value                                     | string     | Optional       |
| isLogo            | set logo in drawable folder with name **mposlogo** | boolean    | Optional       |

--- 

Note : 

1. **Transaction Type**  possibel values : "PURCHASE", "MICROATM", "BALANCE_ENQUIRY", "UPI"
2. Acceptable file formate for logo : png,jpg,jpeg
3. use disconnectDevice method after every transactions.


## Theming

Colours can be overridden in colors.xml for your app.

```
<?xml version="1.0" encoding="utf-8"?> 
<resources>
    <color name="credopayColorPrimary">#3E6698</color>
    <color name="credopayColorPrimaryDark">#264367</color>  
    <color name="credopayColorSecondary">#33B900</color>  
    <color name="credopayColorAccent">#D81B60</color>
    <color name="credopayColorRed">#FE4242</color>
    <color name="credopayColorWhite">#ffffff</color>
    <color name="credopayColorBlack">#000000</color>
    <color name="credopayColorBottomNavIconUnSelected">#B1B1B1</color>  
    <color name="credopayColorLight">#f5f5f5</color>
</resources>

```


## Proguard

If you have enabled “minify”, you will need to add this config to your Proguard config

```
#-keep class in.credopay.**{
#   <fields>;
#   public <methods>;
#}
#-keepclassmembers class in.credopay.** { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.ApiRequest {  <fields>; }
-keepclassmembers class in.credopay.payment.sdk.ApiResponse {  <fields>; }
-keepclassmembers class in.credopay.payment.sdk.ApiRequest$IsoData { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.ApiResponse$IsoData { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.ApiResponse$IsoData { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.ApiErrorResponse { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.TransactionModel { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.TransactionResponse { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.UpiStatusResponse { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.ApiResponse$TransactionSets { <fields>; }
-keepclassmembers class in.credopay.payment.sdk.TransactionAggregateResponse { <fields>; }

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int d(...);
    public static int w(...);
    public static int v(...);
    public static int i(...);
    public static int e(...);
}
-assumenosideeffects class timber.log.Timber* {
    public static * d(...);
    public static * w(...);
    public static * v(...);
    public static * i(...);
    public static * e(...);
} 

```

