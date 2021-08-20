# Content Provider
A content provider manages access to a central repository of data. A provider is part of an Android application, which often provides its own UI for working with the data. However, content providers are primarily intended to be used by other applications, which access the provider using a provider client object. Together, providers and provider clients offer a consistent, standard interface to data that also handles inter-process communication and secure data access.

Typically you work with content providers in one of two scenarios; you may want to implement code to access an existing content provider in another application, or you may want to create a new content provider in your application to share data with other applications. This topic covers the basics of working with existing content providers.

```kotlin
buttonOK.setOnClickListener {
    // Find and show all names from the directory if the user has allowed access.
    if(ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
        val contentResolver = contentResolver
        var cursor = contentResolver
            .query(
                ContactsContract.Contacts.CONTENT_URI,
                arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME)
        if(cursor != null){ // If the cursor is not empty, get all the content step by step and save it.
            var contactList = ArrayList<String>()
            while(cursor.moveToNext()){
                contactList.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
            }
            cursor.close()
            val adapter = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,contactList)
            listView.adapter = adapter
        }
    } else { // Request access permission if the user has not allowed access.
        ActivityCompat.requestPermissions(this@MainActivity,arrayOf(Manifest.permission.READ_CONTACTS),1)
    }
}
```
