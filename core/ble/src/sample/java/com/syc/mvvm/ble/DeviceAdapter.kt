package com.syc.mvvm.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Create by Stone Yu
 * Date:2023/11/9
 * Desc:
 */
class DeviceAdapter(private val list:List<BluetoothDevice>): RecyclerView.Adapter<DeviceAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_device_layout,parent,false)
        return VH(itemView)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: VH, position: Int) {
        with(list[position]){
            holder.nameTv.text = this.name
            holder.addressTv.text = this.address
        }
    }



    inner class VH(view:View):ViewHolder(view){
        val nameTv = itemView.findViewById<TextView>(R.id.tv_name)
        val addressTv = itemView.findViewById<TextView>(R.id.tv_address)
    }
}