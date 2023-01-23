<!-- markdownlint-disable MD024 MD034 MD033 -->

# react-native-instantpay-mpos

mPOS for [React Native](https://github.com/facebook/react-native) and support only for android.

## TOC

- [Installation](#installation)
- [Linking](#linking)
- [Usage](#usage)
- [API](#api)
- [Hooks & Events](#hooks--events)
- [Troubleshooting](#troubleshooting)
- [Release Notes](#release-notes)
- [react-native-dom / react-native-web](#react-native-dom)


## Installation

Using npm:

```shell
npm install --save react-native-instantpay-mpos
```

or using yarn:

```shell
yarn add react-native-instantpay-mpos

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

