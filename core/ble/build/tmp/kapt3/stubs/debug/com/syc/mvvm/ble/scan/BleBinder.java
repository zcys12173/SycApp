package com.syc.mvvm.ble.scan;

/**
 * Create by Stone Yu
 * Date:2023/11/7
 * Desc:BleBinder，提供Ble具体功能
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J*\u0010\u0012\u001a\u00020\u00132\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\b\b\u0002\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\b\u001a\n \u0007*\u0004\u0018\u00010\t0\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n \u0007*\u0004\u0018\u00010\u00110\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/syc/mvvm/ble/scan/BleBinder;", "Landroid/os/Binder;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapter", "Landroid/bluetooth/BluetoothAdapter;", "kotlin.jvm.PlatformType", "defaultScanSetting", "Landroid/bluetooth/le/ScanSettings;", "getDefaultScanSetting", "()Landroid/bluetooth/le/ScanSettings;", "defaultScanSetting$delegate", "Lkotlin/Lazy;", "manager", "Landroid/bluetooth/BluetoothManager;", "scanner", "Landroid/bluetooth/le/BluetoothLeScanner;", "scan", "", "filters", "", "Landroid/bluetooth/le/ScanFilter;", "setting", "callback", "Landroid/bluetooth/le/ScanCallback;", "ble_debug"})
public final class BleBinder extends android.os.Binder {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final android.bluetooth.BluetoothManager manager = null;
    private final android.bluetooth.BluetoothAdapter adapter = null;
    private final android.bluetooth.le.BluetoothLeScanner scanner = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy defaultScanSetting$delegate = null;
    
    public BleBinder(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final android.bluetooth.le.ScanSettings getDefaultScanSetting() {
        return null;
    }
    
    @androidx.annotation.RequiresPermission(value = "android.permission.BLUETOOTH_SCAN")
    public final void scan(@org.jetbrains.annotations.NotNull
    java.util.List<android.bluetooth.le.ScanFilter> filters, @org.jetbrains.annotations.NotNull
    android.bluetooth.le.ScanSettings setting, @org.jetbrains.annotations.NotNull
    android.bluetooth.le.ScanCallback callback) {
    }
}