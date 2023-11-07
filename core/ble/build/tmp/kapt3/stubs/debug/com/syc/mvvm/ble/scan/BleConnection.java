package com.syc.mvvm.ble.scan;

/**
 * Create by Stone Yu
 * Date:2023/11/7
 * Desc:
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007J\"\u0010\u000f\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0094\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/syc/mvvm/ble/scan/BleConnection;", "Landroid/bluetooth/BluetoothGattCallback;", "device", "Landroid/bluetooth/BluetoothDevice;", "(Landroid/bluetooth/BluetoothDevice;)V", "getDevice", "()Landroid/bluetooth/BluetoothDevice;", "gatt", "Landroid/bluetooth/BluetoothGatt;", "connect", "", "context", "Landroid/content/Context;", "autoConnect", "", "onConnectionStateChange", "status", "", "newState", "ble_debug"})
public class BleConnection extends android.bluetooth.BluetoothGattCallback {
    @org.jetbrains.annotations.NotNull
    private final android.bluetooth.BluetoothDevice device = null;
    @org.jetbrains.annotations.Nullable
    private android.bluetooth.BluetoothGatt gatt;
    
    public BleConnection(@org.jetbrains.annotations.NotNull
    android.bluetooth.BluetoothDevice device) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    protected android.bluetooth.BluetoothDevice getDevice() {
        return null;
    }
    
    @androidx.annotation.RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    public final void connect(@org.jetbrains.annotations.NotNull
    android.content.Context context, boolean autoConnect) {
    }
    
    @java.lang.Override
    public void onConnectionStateChange(@org.jetbrains.annotations.Nullable
    android.bluetooth.BluetoothGatt gatt, int status, int newState) {
    }
}