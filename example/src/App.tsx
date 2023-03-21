import * as React from 'react';

import { StyleSheet, View, Text, Button } from 'react-native';
import { startTransaction } from 'react-native-instantpay-mpos';

export default function App() {
  
  const testModule = () => {

    /* InstantpayMpos.multiply(5,3).then(res => {
        console.log(res);
    }); */

    let options = {
        transactionType : 'PURCHASE',
        debugMode : true,
        production : true,
        amount : 101.112356,
        loginId : "2000016248",
        loginPassword : "Qwert@123",
        mobile : "7845682531",
        successTimeout : "120",
        isLogo: true
    }

    startTransaction(JSON.stringify(options)).then(res => {

      console.log(res);

    }); 
}

  return (
    <View style={styles.container}>
      <Button title='Hello' onPress={() => testModule()} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
