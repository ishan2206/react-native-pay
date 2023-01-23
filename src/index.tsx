import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-instantpay-mpos' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const InstantpayMpos = NativeModules.InstantpayMpos
  ? NativeModules.InstantpayMpos
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );
    
export function disconnectDevice(): Promise<Object> {
  return InstantpayMpos.disconnectDevice();
}

export function startTransaction(options:Object): Promise<Object> {
  return InstantpayMpos.startTransaction(options);
}
