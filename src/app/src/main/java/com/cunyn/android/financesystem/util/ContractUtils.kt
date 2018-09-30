package com.cunyn.android.financesystem.util

import android.content.Context
import android.provider.ContactsContract
import com.cunyn.android.financesystem.bean.ContractBean

internal class ContractUtils(var context: Context) {
    // 号码
    var NUM: String = ContactsContract.CommonDataKinds.Phone.NUMBER
    // 联系人姓名
    var NAME: String = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME

//联系人提供者的uri
    private var phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
//获取所有联系人
    fun getPhone(): List<ContractBean>  {
        var phoneDtos = ArrayList<ContractBean>()
        var cr = context.contentResolver
        var cursor = cr.query (phoneUri, arrayOf( NUM , NAME ), null, null, null);
        while (cursor.moveToNext()) {
            var phoneDto = ContractBean(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
            phoneDtos.add(phoneDto)
        }
        cursor.close()
        return phoneDtos
    }

}
