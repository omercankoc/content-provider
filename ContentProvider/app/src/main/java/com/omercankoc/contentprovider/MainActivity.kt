package com.omercankoc.contentprovider

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
class MainActivity : AppCompatActivity() {

    private lateinit var buttonOK : Button
    private lateinit var listView : ListView
    private lateinit var linearLayout: LinearLayout

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonOK = findViewById(R.id.buttonOK)
        listView = findViewById(R.id.listView)
        linearLayout = findViewById(R.id.linearLayout)

        buttonOK.setOnClickListener {
            // Eger kullanici erisime izin verdiyse rehberden tum isimleri al ve goster.
            if(ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                val contentResolver = contentResolver
                var cursor = contentResolver
                    .query(
                        ContactsContract.Contacts.CONTENT_URI,
                        arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
                        null,
                        null,
                        ContactsContract.Contacts.DISPLAY_NAME)
                if(cursor != null){ // Cursor bos degil ise adim adim tum icerigi al ve kaydet.
                    var contactList = ArrayList<String>()
                    while(cursor.moveToNext()){
                        contactList.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
                    }
                    cursor.close()
                    val adapter = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,contactList)
                    listView.adapter = adapter
                }
            } else { // Eger kullanici erisime izin vermediyse tekrar erisim izini iste.
                ActivityCompat.requestPermissions(this@MainActivity,arrayOf(Manifest.permission.READ_CONTACTS),1)
            }
        }
    }
}