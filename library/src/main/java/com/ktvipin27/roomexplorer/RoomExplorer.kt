package com.ktvipin27.roomexplorer

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.room.RoomDatabase
import com.ktvipin27.roomexplorer.activity.REMainActivity
import com.ktvipin27.roomexplorer.activity.REQueryActivity

/**
 * Created by Vipin KT on 08/05/20
 */
object RoomExplorer {

    internal const val KEY_DATABASE_CLASS = "com.ktvipin27.roomexplorer.DATABASE_CLASS"
    internal const val KEY_DATABASE_NAME = "com.ktvipin27.roomexplorer.DATABASE_NAME"

    /**
     * Launches [REMainActivity] from the context passed in the method.
     * @param context The context such as any activity or fragment or context reference
     * @param dbClass The database class registered in Room with @Database annotation and extended with RoomDatabase
     * @param dbName The name of your Room Database
     */
    fun explore(
        context: Context,
        dbClass: Class<out RoomDatabase>,
        dbName: String
    ) {
        Intent(context, REMainActivity::class.java)
            .apply {
                putExtras(
                    bundleOf(KEY_DATABASE_CLASS to dbClass, KEY_DATABASE_NAME to dbName)
                )
            }
            .also { context.startActivity(it) }

    }

    /**
     * internal purpose
     * Launches [REQueryActivity] from the context passed in the method.
     * @param context RoomExplorerMainActivity
     */
    internal fun query(
        context: REMainActivity
    ) = Intent(context, REQueryActivity::class.java)
        .also { context.startActivity(it) }
}