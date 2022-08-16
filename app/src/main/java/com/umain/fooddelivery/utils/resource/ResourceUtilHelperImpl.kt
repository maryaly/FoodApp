package com.umain.fooddelivery.utils.resource

import android.content.Context
import com.umain.fooddelivery.utils.Constants
import timber.log.Timber
import javax.inject.Inject

class ResourceUtilHelperImpl @Inject constructor(private val mContext: Context) :
    ResourceUtilHelper {

    override fun getResourceString(resourceId: Int): String =
        try {
            mContext.getString(resourceId)
        } catch (exp: Exception) {
            Timber.e(exp)
            Constants.EMPTY_STRING
        }
}