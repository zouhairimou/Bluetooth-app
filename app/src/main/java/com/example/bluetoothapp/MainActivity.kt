package com.example.bluetoothapp

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
   private val REQUEST_ENABLE_BT:Int = 1
   private val REQUEST_CODE_DISCOVERABLE_BT:Int = 2

    lateinit var bAdapter:BluetoothAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bAdapter=BluetoothAdapter.getDefaultAdapter()
        if (bAdapter==null){
            bluetoothStatusTv.text="bluetooth is not available"
        }else{
            bluetoothStatusTv.text="bluetooth is available"
        }

        if (bAdapter.isEnabled){
            bluetooIv.setImageResource(R.drawable.ic_bluetooth_on)
        }else{
            bluetooIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
    //ON
        turnOnBtn.setOnClickListener{
            if (bAdapter.isEnabled){
                Toast.makeText(this,"already on",Toast.LENGTH_SHORT).show()
            }
            else{
                var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)


                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                startActivityForResult(intent,REQUEST_ENABLE_BT)
            }
        }
        //OFF
        turnOffBtn.setOnClickListener{
            if (bAdapter.isEnabled){
                Toast.makeText(this,"already off",Toast.LENGTH_SHORT).show()
            }
            else {
                bAdapter.disable()
                bluetooIv.setImageResource(R.drawable.ic_bluetooth_off)
                Toast.makeText(this,"bluetoot Off",Toast.LENGTH_SHORT).show()

            }
        }
       discoverableBtn.setOnClickListener{
            if (!bAdapter.isDiscovering){
                Toast.makeText(this,"making your device discoverable",Toast.LENGTH_SHORT).show()
                val intent = Intent(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                startActivityForResult(intent,REQUEST_CODE_DISCOVERABLE_BT)

            }
        }
        PaireDBtn.setOnClickListener{
            if (bAdapter.isEnabled){
                pairedList.text="Paired devices"
                val devices =bAdapter.bondedDevices
                for (device in devices){
                    val dName=device.name
                    val dAddress=device
                    pairedList.append("\n device: $dName , $device")
                }
            }else{
                Toast.makeText(this,"turn the bluetooth first",Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_ENABLE_BT->
                if (resultCode== Activity.RESULT_OK){
                    bluetooIv.setImageResource(R.drawable.ic_bluetooth_on)
                    Toast.makeText(this,"bluetooth on",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"could not activate the Bluetooth",Toast.LENGTH_SHORT).show()

                }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}