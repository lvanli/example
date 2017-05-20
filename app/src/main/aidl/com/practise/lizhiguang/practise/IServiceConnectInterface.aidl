// IServiceConnectInterface.aidl
package com.practise.lizhiguang.practise;

import com.practise.lizhiguang.practise.IServiceListenerInterface;

// Declare any non-default types here with import statements

interface IServiceConnectInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getData();
    void setListener(IServiceListenerInterface listener);
}
