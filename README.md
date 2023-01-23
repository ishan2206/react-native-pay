<!-- markdownlint-disable MD024 MD034 MD033 -->

# react-native-instantpay-mpos

mPOS for [React Native](https://github.com/facebook/react-native) and support only for android.

## TOC

- [Installation](#installation)
- [Android Dependencies](#android-dependencies)
- [Usage](#usage)


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
            url "https://mymavenrepo.com/repo/nkkgWioPTZZyJhyjF9hy/" 
        } 
    } 
}

```

Then reference the library in the dependency section: 

```
dependencies { 
    implementation 'in.credopay.payment.sdk:vm30-payment-sdk:2.0.3' 
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
    implementation ('in.credopay.payment.sdk:vm30-payment-sdk:2.0.3')  
    {
        exclude module:'retrofit' 
    }
} 

```

## Usage

Support methods list :
1. disconnectDevice
2. startTransaction


## Proguard

If you have enabled “minify”, you will need to add this config to your Proguard config

```
-keep class in.credopay.**{ 
    <fields>; 
    public <methods>; 
} 
-keepclassmembers class in.credopay.** { <fields>; } 
-keepclassmembers class in.credopay.payment.sdk.ApiRequest { <fields>; } 
-keepclassmembers class in.credopay.payment.sdk.ApiResponse { <fields>; } 
-keepclassmembers class in.credopay.payment.sdk.ApiRequest$IsoData { <fields>; } 
-keepclassmembers class in.credopay.payment.sdk.ApiResponse$IsoData { <fields>; } 
-keepclassmembers class in.credopay.payment.sdk.ApiResponse$IsoData { <fields>; } 
-keepclassmembers class in.credopay.payment.sdk.ApiErrorResponse { <fields>; } 
-keepclassmembers class in.credopay.payment.sdk.TransactionModel { <fields>; } 
-keepclassmembers class in.credopay.payment.sdk.TransactionResponse { <fields>; } 
```

